package com.capstone.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.capstone.exceptions.PortfolioException;
import com.capstone.exceptions.TradeException;
import com.capstone.integration.TradeDao;
import com.capstone.models.Holding;
import com.capstone.models.Order;
import com.capstone.models.Trade;

@Service
@Primary
public class TradeServiceImpl implements TradeService {
	private List<Trade> trades = new ArrayList<>();

	@Autowired
	private HoldingService holdingService;

	@Autowired
	private TradeDao dao;

	@Override
	public void executeTrade(Trade trade) throws TradeException, PortfolioException {
		try {
			validateTrade(trade);
			if (trades.stream().anyMatch(t -> t.getTradeId().equals(trade.getTradeId()))) {
				throw new TradeException.TradeAlreadyExistsException(
						"Trade with ID " + trade.getTradeId() + " already exists");
			}
			dao.insertTrade(trade);
			Holding item = new Holding();
			item.setAveragePrice(trade.getExecutionPrice());
			item.setClientId(trade.getClientId());
			item.setInstrument("Test");
			item.setDayChangePercent(0);
			item.setInstrumentId(trade.getInstrumentId());
			item.setInvestedCapital(0);
			item.setLtp(trade.getExecutionPrice());
			item.setPercentChange(0);
			item.setProfitLoss(0);
			item.setQuantity(trade.getQuantity());
			holdingService.addPortfolioItem(item);

		} catch (NullPointerException | IllegalArgumentException e) {
			throw new TradeException("Error executing trade: " + e.getMessage());
		}
	}

	@Override
	public Order createOrder(Trade trade) throws TradeException {
		validateTrade(trade);
		Order order = new Order();
		try {
			order.setInstrumentId(trade.getInstrumentId());
			order.setQuantity(trade.getQuantity());
			order.setTargetPrice(trade.getExecutionPrice());
			order.setDirection(trade.getDirection());
			order.setClientId(trade.getClientId());
			order.setOrderId(trade.getTradeId());
			order.setCreationTime(trade.getCreationTime());
		} catch (IllegalArgumentException e) {
			throw new TradeException.TradeValidationException("Invalid trade data: " + e.getMessage());
		} catch (NullPointerException e) {
			throw new TradeException("Null value encountered: " + e.getMessage());
		}
		return order;
	}

	@Override
	public void validateTrade(Trade trade) throws TradeException {
		try {
			Objects.requireNonNull(trade, "Trade cannot be null");
		} catch (NullPointerException e) {
			throw new TradeException("Trade validation failed: " + e.getMessage());
		}
	}

	@Override
	public BigDecimal calculateTradeValue(Trade trade) throws TradeException {
		validateTrade(trade);
		try {
			return trade.getExecutionPrice().multiply(new BigDecimal(trade.getQuantity()));

		} catch (NullPointerException | IllegalArgumentException e) {
			throw new TradeException("Error calculating trade value: " + e.getMessage());
		}
	}

	@Override
	public void cancelOrder(String tradeId) throws TradeException {
		try {
			dao.cancelOrder(tradeId);
		} catch (NullPointerException | IllegalArgumentException e) {
			throw new TradeException("Error canceling trade: " + e.getMessage());
		}
	}

	@Override
	public void updateOrder(Order updatedOrder) throws TradeException, PortfolioException {
		try {
			dao.modifyOrder(updatedOrder);
		} catch (NullPointerException | IllegalArgumentException e) {
			throw new TradeException("Error updating trade: " + e.getMessage());
		}
	}

	@Override
	public Trade getTradeById(String tradeId) throws TradeException {
		try {
			return dao.getTradeById(tradeId);
		} catch (NullPointerException | IllegalArgumentException e) {
			throw new TradeException("Error retrieving trade: " + e.getMessage());
		}
	}

	@Override
	public List<Trade> listAllTrades() {
		return dao.getTradeHistory();
	}

	@Override
	public List<Trade> getClientTradeHistory(String clientId) {
		List<Trade> tradeHistory = dao.getTradeHistory();
		tradeHistory.sort((trade1, trade2) -> trade2.getCreationTime().compareTo(trade1.getCreationTime()));
		if (tradeHistory.size() > 100) {
			return tradeHistory.subList(0, 100);
		}
		return tradeHistory;
	}

	@Override
	public List<Trade> listTradesByClient(String clientId) {
		return dao.getTradeHistory().stream().filter(trade -> clientId.equals(trade.getClientId())).toList();
	}

	@Override
	public List<Trade> listTradesByInstrument(String instrumentId) {
		return dao.getTradeHistory().stream().filter(trade -> instrumentId.equals(trade.getInstrumentId())).toList();
	}
}
