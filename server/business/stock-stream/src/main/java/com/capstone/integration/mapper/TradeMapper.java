package com.capstone.integration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.capstone.models.Instrument;
import com.capstone.models.Order;
import com.capstone.models.Price;
import com.capstone.models.Trade;

public interface TradeMapper {
//	@Select("SELECT * FROM ss_instruments")
    List<Instrument> getAllInstruments();

//    @Select("SELECT * FROM ss_instruments WHERE INSTRUMENT_ID = ?")
    Instrument getInstrumentById(String instrumentId);

//    @Select("SELECT * FROM ss_price WHERE INSTRUMENT_ID = ?")
    Price getPrice(String instrumentId);

//    @Select("SELECT * FROM ss_trades ORDER BY EXECUTED_AT DESC FETCH FIRST 100 ROWS ONLY")
    List<Trade> getTradeHistory();

//    @Select("SELECT * FROM ss_trades WHERE TRADE_ID = ?")
    Trade getTradeById(String tradeId);

//    @Insert("INSERT INTO SS_TRADES (TRADE_ID, clientId, ORDER_ID, INSTRUMENT_ID, QUANTITY, EXECUTION_PRICE, DIRECTION, EXECUTED_AT) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
    void insertTrade(Trade trade);

//    @Select("SELECT * FROM ss_orders WHERE STATUS = 0")
    List<Order> getAllPendingOrders();

//    @Select("SELECT * FROM ss_orders")
    List<Order> getAllOrders();

//    @Select("SELECT * FROM ss_orders WHERE ORDER_ID = ?")
    Order getOrderById(String orderId);

//    @Insert("INSERT INTO SS_ORDERS (ORDER_ID, clientId, INSTRUMENT_ID, QUANTITY, TARGET_PRICE, DIRECTION, STATUS, CREATED_AT) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
    void insertOrder(Order order);

//    @Delete("UPDATE ss_orders SET status = 2 WHERE ORDER_ID = ?")
    void cancelOrder(String orderId);

//    @Update("UPDATE ss_orders SET ORDER_ID = ?, clientId = ?, INSTRUMENT_ID = ?, QUANTITY = ?, TARGET_PRICE = ?, DIRECTION = ?, STATUS = ?, CREATED_AT = ? WHERE ORDER_ID = ?")
    void modifyOrder(Order order);

//    @Insert("INSERT INTO SS_TRADES (TRADE_ID, clientId, ORDER_ID, INSTRUMENT_ID, QUANTITY, EXECUTION_PRICE, DIRECTION, EXECUTED_AT) VALUES (?, ?, ?, ?, ?, ?, ?, TO_TIMESTAMP(?, ?))")
    Trade executeOrder(Order order);
}
