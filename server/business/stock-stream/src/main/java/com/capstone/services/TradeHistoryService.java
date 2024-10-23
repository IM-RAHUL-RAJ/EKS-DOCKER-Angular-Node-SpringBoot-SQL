package com.capstone.services;

import java.util.List;

import com.capstone.models.Trade;

public interface TradeHistoryService {
	public List<Trade> getClientTradingHistory(String clientId);
}
