package com.hackerrank.stocktrade.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hackerrank.stocktrade.model.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long>{
	
	List<Trade> findByUserId(@Param("userId") long userId );
	List<Trade> findBySymbolAndTimestampBetween(String symbol, Date startDate, Date endDate);
	Trade findDistinctFirstByIdAndTimestamp(long id, Date timestamp);
}
