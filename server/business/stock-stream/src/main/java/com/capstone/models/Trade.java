package com.fidelity.capstone.stock_stream;

import java.util.Objects;

public class Trade {
    private String instrumentId;
    private int quantity;
    private double executionPrice;
    private String direction;
    private String clientId;
    private Order order;
    private String tradeId;
    private double cashValue;
    private String tradeDate;

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

    public double getExecutionPrice() {
        return executionPrice;
    }

    public void setExecutionPrice(double executionPrice) {
        if (executionPrice <= 0) {
            throw new IllegalArgumentException("Execution price must be greater than zero");
        }
        this.executionPrice = executionPrice;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        Objects.requireNonNull(direction, "Direction cannot be null");
        if (!"buy".equalsIgnoreCase(direction) && !"sell".equalsIgnoreCase(direction)) {
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

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        Objects.requireNonNull(tradeDate, "Trade date cannot be null");
        if (tradeDate.isEmpty()) {
            throw new IllegalArgumentException("Trade date cannot be empty");
        }
        this.tradeDate = tradeDate;
    }
}
