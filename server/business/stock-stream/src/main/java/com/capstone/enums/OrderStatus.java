package com.capstone.enums;

public enum OrderStatus {
	PENDING(0),
	EXECUTED(1),
	CANCELED(2);

	private int code;
	
	private OrderStatus(int code) {
		this.code = code;
	}
	
	public static OrderStatus of(int code) {
		for (OrderStatus orderStatus : values()) {
			if (orderStatus.code == code) {
				return orderStatus;
			}
		}
		throw new IllegalArgumentException("Unknown ORDER STATUS code " + code);
	}

	public int getCode() {
		return code;
	}
}
