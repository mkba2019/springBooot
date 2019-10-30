package com.hackerrank.stocktrade.controller;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.hackerrank.stocktrade.Application;
import com.hackerrank.stocktrade.model.Message;
import com.hackerrank.stocktrade.model.MinMax;
import com.hackerrank.stocktrade.model.Trade;
import com.hackerrank.stocktrade.service.StockTradeService;

@RestController
@RequestMapping
public class StockTradeApiRestController {

	@Autowired
	private StockTradeService stockService;

	@DeleteMapping("/erase")
	public ResponseEntity<?> eraseTrade() {

		stockService.eraseTrade();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value ="/trades")
	public ResponseEntity<?> getAllTrades() {
		List<Trade> tradeList = new ArrayList<>();
		tradeList = stockService.findAllTrades();
		if (tradeList != null && tradeList.size() > 0) {
			return new ResponseEntity<List<Trade>>(tradeList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@PostMapping(value = "/trades", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveTrade(@RequestBody Trade trade) {
		if (trade != null && trade.getId() != null) {
			Trade existTrade = stockService.findTradeByIdAndTimeStamp(trade.getId(), trade.getTimestamp());
			if (existTrade != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			} else {
				stockService.saveTrade(trade);
				return new ResponseEntity<>(HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@GetMapping(value= "/trades/users/{userID}")
	public ResponseEntity<?> getAllTradesByUserId(@PathVariable("userID") long userID) {
		List<Trade> trades = new ArrayList<>();

		trades = stockService.findTradeByUserID(userID);
		if (trades != null && trades.size() > 0) {
			return new ResponseEntity<>(trades, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(trades, HttpStatus.NOT_FOUND);
		}
	}
	

	@GetMapping(value="/stocks/{stockSymbol}/price")
	public ResponseEntity<?> getHiiestAndLowest(@PathVariable("stockSymbol") String symbol,
			@RequestParam("start") String startDate, @RequestParam("end") String endDate) {
		MinMax  minMax = null;
		try {
			minMax = stockService.getHiiestAndLowest(symbol, startDate, endDate);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	
		if (minMax != null) {
			return new ResponseEntity<>(minMax, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

}
