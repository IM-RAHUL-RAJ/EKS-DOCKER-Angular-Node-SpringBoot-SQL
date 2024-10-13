package com.capstone.integration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.capstone.models.Instrument;
import com.capstone.models.Order;
import com.capstone.models.Price;
import com.capstone.models.Trade;

@Mapper
public interface TradeMapper {

    List<Instrument> getAllInstruments();

    Instrument getInstrumentById(String instrumentId);

    Price getPrice(String instrumentId);

    List<Trade> getTradeHistory();
    
    List<Trade> getClientTradeHistory(String clientId);

    Trade getTradeById(String tradeId);

    void insertTrade(Trade trade);

    List<Order> getAllPendingOrders();

    List<Order> getAllOrders();

    Order getOrderById(String orderId);

    void insertOrder(Order order);

    void cancelOrder(String orderId);

    void modifyOrder(Order order);

    Trade executeOrder(Order order);
}
