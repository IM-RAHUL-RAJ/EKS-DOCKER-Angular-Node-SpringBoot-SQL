package com.capstone.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Trade {
    private String tradeId;
    private String clientId;
    private String orderId;
    private String instrumentId;
    private int quantity;
    private BigDecimal executionPrice;
    private String direction;
    private Order order;
    private double cashValue;
    private Timestamp creationTime;

    public Trade(String tradeId, String clientId, String orderId, String instrumentId, int quantity,
			BigDecimal executionPrice, String direction, Timestamp creationTime) {
		super();
		this.tradeId = tradeId;
		this.clientId = clientId;
		this.orderId = orderId;
		this.instrumentId = instrumentId;
		this.quantity = quantity;
		this.executionPrice = executionPrice;
		this.direction = direction;
		this.creationTime = creationTime;
	}

	public Trade() {
		// TODO Auto-generated constructor stub
	}

	public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        Objects.requireNonNull(instrumentId, "Instrument ID cannot be null");
        if (instrumentId.isEmpty()) {
            throw new IllegalArgumentException("Instrument ID cannot be empty");
        }
        this.instrumentId = instrumentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.quantity = quantity;
    }

    public BigDecimal getExecutionPrice() {
        return executionPrice;
    }

    public void setExecutionPrice(BigDecimal executionPrice) {
        if (executionPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Execution price must be greater than zero");
        }
        this.executionPrice = executionPrice;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        Objects.requireNonNull(direction, "Direction cannot be null");
        if (!"B".equalsIgnoreCase(direction) && !"S".equalsIgnoreCase(direction)) {
            throw new IllegalArgumentException("Direction must be 'buy' or 'sell'");
        }
        this.direction = direction;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        Objects.requireNonNull(clientId, "Client ID cannot be null");
        if (clientId.isEmpty()) {
            throw new IllegalArgumentException("Client ID cannot be empty");
        }
        this.clientId = clientId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = Objects.requireNonNull(order, "Order cannot be null");
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        Objects.requireNonNull(tradeId, "Trade ID cannot be null");
        if (tradeId.isEmpty()) {
            throw new IllegalArgumentException("Trade ID cannot be empty");
        }
        this.tradeId = tradeId;
    }

    public double getCashValue() {
        return cashValue;
    }

    public void setCashValue(double cashValue) {
        if (cashValue < 0) {
            throw new IllegalArgumentException("Cash value cannot be negative");
        }
        this.cashValue = cashValue;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        Objects.requireNonNull(creationTime, "Trade date cannot be null");
        this.creationTime = creationTime;
    }

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
