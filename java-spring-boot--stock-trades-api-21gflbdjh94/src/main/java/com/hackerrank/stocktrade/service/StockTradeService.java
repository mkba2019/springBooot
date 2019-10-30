package com.hackerrank.stocktrade.service;

import java.util.Date;
import java.util.List;

import com.hackerrank.stocktrade.model.MinMax;
import com.hackerrank.stocktrade.model.Trade;

public interface StockTradeService {

	public void eraseTrade();
    
    public Trade saveTrade(Trade trade);
    
    Trade findTradeByIdAndTimeStamp(long id, Date timestamp);
    
    List<Trade> findTradeByUserID(long userId);
    
    MinMax getHiiestAndLowest(String symbol, String startDate, String endDate)  throws Exception;

    List<Trade> findAllTrades();
}
