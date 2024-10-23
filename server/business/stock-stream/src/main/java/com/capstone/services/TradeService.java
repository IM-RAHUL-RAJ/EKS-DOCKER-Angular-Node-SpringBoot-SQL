package com.capstone.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.capstone.exceptions.PortfolioException;
import com.capstone.exceptions.TradeException;
import com.capstone.integration.TradeDao;
import com.capstone.models.Order;
import com.capstone.models.Holding;
import com.capstone.models.Trade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface TradeService {
	public void executeTrade(Trade trade) throws TradeException, PortfolioException ;
	public Order createOrder(Trade trade) throws TradeException;
	public void validateTrade(Trade trade) throws TradeException;
	public BigDecimal calculateTradeValue(Trade trade) throws TradeException;
	public void cancelOrder(String tradeId) throws TradeException;
	public void updateOrder(Order updatedOrder) throws TradeException, PortfolioException;
	public Trade getTradeById(String tradeId) throws TradeException ;
	public List<Trade> listAllTrades();
	public List<Trade> getClientTradeHistory(String clientId);
	public List<Trade> listTradesByClient(String clientId);
	public List<Trade> listTradesByInstrument(String instrumentId);
}
