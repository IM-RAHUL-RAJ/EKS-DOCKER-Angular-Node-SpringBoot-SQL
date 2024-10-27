package com.capstone.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.exceptions.NoTradingHistoryFoundForClientException;
import com.capstone.integration.TradeDao;
import com.capstone.models.Trade;


@Service
public class TradeHistoryServiceImpl implements TradeHistoryService {

	@Autowired
	private TradeDao tradeDao;
	
	@Override
	public List<Trade> getClientTradingHistory(String clientId) {
		
		if(clientId == null) {
			throw new IllegalArgumentException("client id cannot be null");
		}
		
		
		List<Trade> clientTradeHistory = tradeDao.getClientTradeHistory(clientId);
		
		if(clientTradeHistory.isEmpty()) {
			throw new NoTradingHistoryFoundForClientException("No Trading history found for client id : "+clientId);
		}
		
		return clientTradeHistory;
	}

}
