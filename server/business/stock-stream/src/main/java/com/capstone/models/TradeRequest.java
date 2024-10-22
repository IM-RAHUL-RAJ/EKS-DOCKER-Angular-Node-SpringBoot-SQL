package com.capstone.models;

import java.math.BigDecimal;
import java.util.Objects;

public class TradeRequest {
	private String tradeId;
    private String clientId;
    private String instrumentId;
    private int quantity;
    private BigDecimal executionPrice;
    private String direction;
	public TradeRequest(String tradeId, String clientId, String instrumentId, int quantity, BigDecimal executionPrice,
			String direction) {
		super();
		this.tradeId = tradeId;
		this.clientId = clientId;
		this.instrumentId = instrumentId;
		this.quantity = quantity;
		this.executionPrice = executionPrice;
		this.direction = direction;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
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
	public BigDecimal getExecutionPrice() {
		return executionPrice;
	}
	public void setExecutionPrice(BigDecimal executionPrice) {
		this.executionPrice = executionPrice;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	@Override
	public int hashCode() {
		return Objects.hash(clientId, direction, executionPrice, instrumentId, quantity, tradeId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeRequest other = (TradeRequest) obj;
		return Objects.equals(clientId, other.clientId) && Objects.equals(direction, other.direction)
				&& Objects.equals(executionPrice, other.executionPrice)
				&& Objects.equals(instrumentId, other.instrumentId) && quantity == other.quantity
				&& Objects.equals(tradeId, other.tradeId);
	}
	@Override
	public String toString() {
		return "TradeRequest [tradeId=" + tradeId + ", clientId=" + clientId + ", instrumentId=" + instrumentId
				+ ", quantity=" + quantity + ", executionPrice=" + executionPrice + ", direction=" + direction + "]";
	}
    

}
