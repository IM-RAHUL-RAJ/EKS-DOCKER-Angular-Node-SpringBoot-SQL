package com.capstone.models;

import java.math.BigDecimal;

public class Portfolio {
    private String instrument;
    private String instrumentId;
    private int quantity;
    private BigDecimal averagePrice;
    private double investedCapital;
    private BigDecimal ltp;
    private double percentChange;
    private double profitLoss;
    private double dayChangePercent;
    private String clientId;
    
    public Portfolio() {}  

    public Portfolio(String instrument, String instrumentId,String clientId, int quantity, BigDecimal averagePrice, double investedCapital, BigDecimal ltp, double percentChange, double profitLoss, double dayChangePercent) {
        this.instrument = instrument;
        this.instrumentId = instrumentId;
        this.quantity = quantity;
        this.averagePrice = averagePrice;
        this.investedCapital = investedCapital;
        this.ltp = ltp;
        this.percentChange = percentChange;
        this.profitLoss = profitLoss;
        this.dayChangePercent = dayChangePercent;
        this.clientId=clientId;
    }

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(BigDecimal bigDecimal) {
		this.averagePrice = bigDecimal;
	}

	public double getInvestedCapital() {
		return investedCapital;
	}

	public void setInvestedCapital(double investedCapital) {
		this.investedCapital = investedCapital;
	}

	public BigDecimal getLtp() {
		return ltp;
	}

	public void setLtp(BigDecimal bigDecimal) {
		this.ltp = bigDecimal;
	}

	public double getPercentChange() {
		return percentChange;
	}

	public void setPercentChange(double percentChange) {
		this.percentChange = percentChange;
	}

	public double getProfitLoss() {
		return profitLoss;
	}

	public void setProfitLoss(double profitLoss) {
		this.profitLoss = profitLoss;
	}

	public double getDayChangePercent() {
		return dayChangePercent;
	}

	public void setDayChangePercent(double dayChangePercent) {
		this.dayChangePercent = dayChangePercent;
	}

    // Getters and setters
}