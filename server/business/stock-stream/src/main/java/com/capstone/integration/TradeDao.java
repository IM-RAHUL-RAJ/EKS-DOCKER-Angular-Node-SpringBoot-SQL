package com.capstone.integration;

import java.util.List;

import com.capstone.models.Instrument;
import com.capstone.models.Order;
import com.capstone.models.Price;
import com.capstone.models.Trade;

public interface TradeDao {

	List<Instrument> getAllInstruments();

	Instrument getInstrumentById(String instrumentId);

	Price getPrice(String instrumentId);

	List<Trade> getTradeHistory();

	Trade getTradeById(String tradeId);

	void insertTrade(Trade trade);

	List<Order> getAllPendingOrders();

	List<Order> getAllOrders(); // will contain all orders, cancelled or executed

	Order getOrderById(String orderId);

	void insertOrder(Order order); // will call execute order

	void cancelOrder(String orderId); // delete

	void modifyOrder(Order order); // update

	Trade executeOrder(Order order); // will call insertTrade
}
