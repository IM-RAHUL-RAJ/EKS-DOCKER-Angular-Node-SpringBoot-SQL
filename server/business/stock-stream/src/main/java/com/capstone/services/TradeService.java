package com.capstone.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.capstone.exceptions.PortfolioException;
import com.capstone.exceptions.TradeException;
import com.capstone.models.Order;
import com.capstone.models.Holding;
import com.capstone.models.Trade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TradeService {
    private List<Trade> trades = new ArrayList<>();
    private HoldingService holdingService = new HoldingService();

    public void executeTrade(Trade trade) throws TradeException, PortfolioException {
        try {
            validateTrade(trade);
            if (trades.stream().anyMatch(t -> t.getTradeId().equals(trade.getTradeId()))) {
                throw new TradeException.TradeAlreadyExistsException("Trade with ID " + trade.getTradeId() + " already exists");
            }
            trades.add(trade);
            
            Holding item = new Holding();
            item.setAveragePrice(trade.getExecutionPrice());
            item.setClientId("Test");
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

    public void validateTrade(Trade trade) throws TradeException {
        try {
            Objects.requireNonNull(trade, "Trade cannot be null");
        } catch (NullPointerException e) {
            throw new TradeException("Trade validation failed: " + e.getMessage());
        }
    }

    public BigDecimal calculateTradeValue(Trade trade) throws TradeException {
        validateTrade(trade);
        try {
        	return trade.getExecutionPrice().multiply(new BigDecimal(trade.getQuantity()));
            
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new TradeException("Error calculating trade value: " + e.getMessage());
        }
    }

    public void cancelTrade(String tradeId) throws TradeException {
        try {
            Trade trade = getTradeById(tradeId);
            trades.remove(trade);
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new TradeException("Error canceling trade: " + e.getMessage());
        }
    }

    public void updateTrade(Trade updatedTrade) throws TradeException, PortfolioException {
        validateTrade(updatedTrade);
        try {
            Trade existingTrade = getTradeById(updatedTrade.getTradeId());
            trades.remove(existingTrade);
            trades.add(updatedTrade);
            
            Holding item = new Holding();
            item.setAveragePrice(updatedTrade.getExecutionPrice());
            item.setDayChangePercent(0);
            item.setClientId("Test");
            item.setInstrument("Test");
            item.setInstrumentId(updatedTrade.getInstrumentId());
            item.setInvestedCapital(0);
            item.setLtp(updatedTrade.getExecutionPrice());
            item.setPercentChange(0);
            item.setProfitLoss(0);
            item.setQuantity(updatedTrade.getQuantity());
            
            holdingService.updatePortfolioItem(item);
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new TradeException("Error updating trade: " + e.getMessage());
        }
    }

    public Trade getTradeById(String tradeId) throws TradeException {
        try {
            return trades.stream()
                    .filter(trade -> tradeId.equals(trade.getTradeId()))
                    .findFirst()
                    .orElseThrow(() -> new TradeException.TradeNotFoundException("Trade with ID " + tradeId + " not found"));
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new TradeException("Error retrieving trade: " + e.getMessage());
        }
    }

    public List<Trade> listAllTrades() {
        return new ArrayList<>(trades);
    }
    
    public List<Trade> getClientTradeHistory(String clientId) {
        List<Trade> tradeHistory = new ArrayList<>(listTradesByClient(clientId));
        tradeHistory.sort((trade1, trade2) -> trade2.getCreationTime().compareTo(trade1.getCreationTime()));
        if (tradeHistory.size() > 100) {
            return tradeHistory.subList(0, 100);
        }
        return tradeHistory;
    }

    public List<Trade> listTradesByClient(String clientId) {
        return trades.stream()
                .filter(trade -> clientId.equals(trade.getClientId()))
                .toList();
    }

    public List<Trade> listTradesByInstrument(String instrumentId) {
        return trades.stream()
                .filter(trade -> instrumentId.equals(trade.getInstrumentId()))
                .toList();
    }
}
