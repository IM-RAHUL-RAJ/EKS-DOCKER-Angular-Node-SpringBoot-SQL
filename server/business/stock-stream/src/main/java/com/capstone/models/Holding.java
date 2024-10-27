package com.capstone.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Holding {
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
    
    public Holding() {}  

    public Holding(String instrument, String instrumentId,String clientId, int quantity, BigDecimal averagePrice, double investedCapital, BigDecimal ltp, double percentChange, double profitLoss, double dayChangePercent) {
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

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
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

	public void setLtp(BigDecimal ltp) {
		this.ltp = ltp;
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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(averagePrice, clientId, dayChangePercent, instrument, instrumentId, investedCapital, ltp,
				percentChange, profitLoss, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Holding other = (Holding) obj;
		return Objects.equals(averagePrice, other.averagePrice) && Objects.equals(clientId, other.clientId)
				&& Double.doubleToLongBits(dayChangePercent) == Double.doubleToLongBits(other.dayChangePercent)
				&& Objects.equals(instrument, other.instrument) && Objects.equals(instrumentId, other.instrumentId)
				&& Double.doubleToLongBits(investedCapital) == Double.doubleToLongBits(other.investedCapital)
				&& Objects.equals(ltp, other.ltp)
				&& Double.doubleToLongBits(percentChange) == Double.doubleToLongBits(other.percentChange)
				&& Double.doubleToLongBits(profitLoss) == Double.doubleToLongBits(other.profitLoss)
				&& quantity == other.quantity;
	}

	@Override
	public String toString() {
		return "Holding [instrument=" + instrument + ", instrumentId=" + instrumentId + ", quantity=" + quantity
				+ ", averagePrice=" + averagePrice + ", investedCapital=" + investedCapital + ", ltp=" + ltp
				+ ", percentChange=" + percentChange + ", profitLoss=" + profitLoss + ", dayChangePercent="
				+ dayChangePercent + ", clientId=" + clientId + "]";
	}

	

    // Getters and setters
	
	
}