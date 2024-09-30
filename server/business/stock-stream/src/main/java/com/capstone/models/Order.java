package com.capstone.models;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import com.capstone.enums.OrderStatus;

public class Order {
    private String orderId;
    private String clientId;
    private String instrumentId;
    private int quantity;
    private BigDecimal targetPrice;
    private String direction;
    private OrderStatus orderStatus;
    private Timestamp creationTime;

    public Order(String orderId, String clientId, String instrumentId, int quantity, BigDecimal targetPrice,
			String direction, OrderStatus orderStatus, Timestamp creationTime) {
		super();
		this.orderId = orderId;
		this.clientId = clientId;
		this.instrumentId = instrumentId;
		this.quantity = quantity;
		this.targetPrice = targetPrice;
		this.direction = direction;
		this.orderStatus = orderStatus;
		this.creationTime = creationTime;
	}

	public Order() {
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

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        if (targetPrice.compareTo(BigDecimal.ZERO) <= 0) {
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

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        Objects.requireNonNull(creationTime, "Order date cannot be null");
        this.creationTime = creationTime;
    }

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
}
