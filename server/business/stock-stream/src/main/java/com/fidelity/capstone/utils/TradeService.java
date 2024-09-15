package com.fidelity.capstone.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fidelity.capstone.exceptions.TradeException;
import com.fidelity.capstone.stock_stream.Order;
import com.fidelity.capstone.stock_stream.Trade;

public class TradeService {
    private List<Trade> trades = new ArrayList<>();

    public void executeTrade(Trade trade) throws TradeException {
        validateTrade(trade);
        if (trades.stream().anyMatch(t -> t.getTradeId().equals(trade.getTradeId()))) {
            throw new TradeException.TradeAlreadyExistsException("Trade with ID " + trade.getTradeId() + " already exists");
        }
        trades.add(trade);
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
            order.setOrderDate(trade.getTradeDate());
        } catch (IllegalArgumentException e) {
            throw new TradeException.TradeValidationException("Invalid trade data: " + e.getMessage());
        }
        return order;
    }

    public void validateTrade(Trade trade) throws TradeException {
        Objects.requireNonNull(trade, "Trade cannot be null");
    }

    public double calculateTradeValue(Trade trade) throws TradeException {
        validateTrade(trade);
        return trade.getQuantity() * trade.getExecutionPrice();
    }

    public void cancelTrade(String tradeId) throws TradeException {
        Trade trade = getTradeById(tradeId);
        trades.remove(trade);
    }

    public void updateTrade(Trade updatedTrade) throws TradeException {
        validateTrade(updatedTrade);
        Trade existingTrade = getTradeById(updatedTrade.getTradeId());
        trades.remove(existingTrade);
        trades.add(updatedTrade);
    }

    public Trade getTradeById(String tradeId) throws TradeException {
        return trades.stream()
                .filter(trade -> tradeId.equals(trade.getTradeId()))
                .findFirst()
                .orElseThrow(() -> new TradeException.TradeNotFoundException("Trade with ID " + tradeId + " not found"));
    }

    public List<Trade> listAllTrades() {
        return new ArrayList<>(trades);
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
