package com.hackerrank.stocktrade.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackerrank.stocktrade.model.MinMax;
import com.hackerrank.stocktrade.model.Trade;
import com.hackerrank.stocktrade.repository.TradeRepository;
@Service
public class StockTradeServiceImpl implements StockTradeService {

	@Autowired
    private TradeRepository tradeRepo;
	
	@Override
	public void eraseTrade() {
		tradeRepo.deleteAll();
		
	}

	@Override
	public Trade saveTrade(Trade trade) {
		return tradeRepo.save(trade);
	}

	@Override
	public Trade findTradeByIdAndTimeStamp(long id, Date timestamp) {

	    return tradeRepo.findDistinctFirstByIdAndTimestamp(id,timestamp);
	}

	@Override
	public List<Trade> findTradeByUserID(long userId) {
		return tradeRepo.findByUserId(userId);
	}

	@Override
	public MinMax getHiiestAndLowest(String symbol, String startDate, String endDate) throws Exception{
		
		Date startDt = null;
		Date endDt = null;
		try {
			startDt = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
			endDt=new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
		} catch (ParseException e) {
			throw new Exception(e);
		}
		List<Trade> trades = tradeRepo.findBySymbolAndTimestampBetween(symbol,startDt, endDt);
		 // then
		if(trades.isEmpty()) {
			return null;
		}
		
		 Trade min = trades
	      .stream()
	      .min(Comparator.comparing(Trade::getPrice))
	      .orElseThrow(NoSuchElementException::new);
	    
		  Trade max = trades
			      .stream()
			      .max(Comparator.comparing(Trade::getPrice))
			      .orElseThrow(NoSuchElementException::new);
		
			MinMax minMax = new MinMax();
			minMax.setSymbol(symbol);
			minMax.setHighest(max.getPrice());
			minMax.setLowest(min.getPrice());  
			return minMax;
	}

	@Override
	public List<Trade> findAllTrades() {
		return tradeRepo.findAll();
	}

}
