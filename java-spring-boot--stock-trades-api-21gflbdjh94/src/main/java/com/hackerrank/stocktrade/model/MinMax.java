package com.hackerrank.stocktrade.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class MinMax {

	private String symbol;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getLowest() {
		return lowest;
	}

	public void setLowest(double lowest) {
		this.lowest = lowest;
	}

	public double getHighest() {
		return highest;
	}

	public void setHighest(double highest) {
		this.highest = highest;
	}

	private double lowest;
	private double highest;

}
