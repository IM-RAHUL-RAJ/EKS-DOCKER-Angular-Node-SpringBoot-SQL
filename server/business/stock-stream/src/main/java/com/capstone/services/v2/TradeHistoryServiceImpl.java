package com.capstone.services.v2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.exceptions.NoTradingHistoryFoundForClientException;
import com.capstone.exceptions.UserNotLoggedInToPerformAction;
import com.capstone.integration.TradeDao;
import com.capstone.models.Trade;

@Service
public class TradeHistoryServiceImpl implements TradeHistoryService {

	@Autowired
	private TradeDao tradeDao;

	@Autowired
	private ClientService clientService;

	@Override
	public List<Trade> getClientTradingHistory(String clientId, Long token) {
		if (clientId == null || token == null) {
			throw new IllegalArgumentException("client id & token cannot be null");
		}

		if (clientService.getToken() == null || !clientService.getToken().equals(token)) {
			throw new UserNotLoggedInToPerformAction();
		}

		List<Trade> clientTradeHistory = tradeDao.getClientTradeHistory(clientId);

		if (clientTradeHistory.isEmpty()) {
			throw new NoTradingHistoryFoundForClientException("No Trading history found for client id : " + clientId);
		}

		return clientTradeHistory;
	}

}
