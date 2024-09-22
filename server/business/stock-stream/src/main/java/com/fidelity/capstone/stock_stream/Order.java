package com.fidelity.capstone.stock_stream;
import java.util.Objects;

public class Order {
    private String instrumentId;
    private int quantity;
    private double targetPrice;
    private String direction;
    private String clientId;
    private String orderId;
    private String orderDate;

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

    public double getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(double targetPrice) {
        if (targetPrice <= 0) {
            throw new IllegalArgumentException("Target price must be greater than zero");
        }
        this.targetPrice = targetPrice;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        Objects.requireNonNull(orderId, "Order ID cannot be null");
        if (orderId.isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty");
        }
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        Objects.requireNonNull(orderDate, "Order date cannot be null");
        if (orderDate.isEmpty()) {
            throw new IllegalArgumentException("Order date cannot be empty");
        }
        this.orderDate = orderDate;
    }
}
