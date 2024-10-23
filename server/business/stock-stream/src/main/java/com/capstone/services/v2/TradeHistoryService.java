package com.capstone.services.v2;

import java.util.List;

import com.capstone.models.Trade;

public interface TradeHistoryService {
	public List<Trade> getClientTradingHistory(String clientId,Long token);
}
