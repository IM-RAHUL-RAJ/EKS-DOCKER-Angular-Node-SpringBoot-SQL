package com.capstone.integration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.capstone.exceptions.DatabaseException;
import com.capstone.integration.mapper.TradeMapper;
import com.capstone.models.Instrument;
import com.capstone.models.Order;
import com.capstone.models.Price;
import com.capstone.models.Trade;

@Repository("tradeDao")
@Primary
public class TradeMyBatisImpl implements TradeDao{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	TradeMapper tradeMapper;

	@Override
	public List<Instrument> getAllInstruments() {
		try {
            return tradeMapper.getAllInstruments();
        } catch (Exception e) {
            String message = "Unable to extract instruments";
            logger.error(message, e);
            throw new DatabaseException(message, e);
        }
    }

	@Override
	public Instrument getInstrumentById(String instrumentId) {
		 try {
	            return tradeMapper.getInstrumentById(instrumentId);
	        } catch (Exception e) {
	            String message = "Unable to extract instrument information";
	            logger.error(message, e);
	            throw new DatabaseException(message, e);
	        }
	}

	@Override
	public Price getPrice(String instrumentId) {
		try {
            return tradeMapper.getPrice(instrumentId);
        } catch (Exception e) {
            String message = "Unable to extract price information";
            logger.error(message, e);
            throw new DatabaseException(message, e);
        }
	}

	@Override
	public List<Trade> getTradeHistory() {
		try {
            return tradeMapper.getTradeHistory();
        } catch (Exception e) {
            String message = "Unable to retrieve trade history";
            logger.error(message, e);
            throw new DatabaseException(message, e);
        }
	}

	@Override
	public Trade getTradeById(String tradeId) {
		try {
            return tradeMapper.getTradeById(tradeId);
        } catch (Exception e) {
            String message = "Unable to extract trade information";
            logger.error(message, e);
            throw new DatabaseException(message, e);
        }
	}

	@Override
	public void insertTrade(Trade trade) {
		try {
            tradeMapper.insertTrade(trade);
        } catch (Exception e) {
            String message = "Cannot insert into trades";
            logger.error(message, e);
            throw new DatabaseException(message, e);
        }
	}

	@Override
	public List<Order> getAllPendingOrders() {
		try {
            return tradeMapper.getAllPendingOrders();
        } catch (Exception e) {
            String message = "Unable to extract pending orders";
            logger.error(message, e);
            throw new DatabaseException(message, e);
        }
	}

	@Override
	public List<Order> getAllOrders() {
		 try {
	            return tradeMapper.getAllOrders();
	        } catch (Exception e) {
	            String message = "Unable to extract orders";
	            logger.error(message, e);
	            throw new DatabaseException(message, e);
	        }
	}

	@Override
	public Order getOrderById(String orderId) {
		try {
            return tradeMapper.getOrderById(orderId);
        } catch (Exception e) {
            String message = "Unable to extract order information";
            logger.error(message, e);
            throw new DatabaseException(message, e);
        }
	}

	@Override
	public void insertOrder(Order order) {
		try {
            tradeMapper.insertOrder(order);
        } catch (Exception e) {
            String message = "Cannot insert into orders";
            logger.error(message, e);
            throw new DatabaseException(message, e);
        }		
	}

	@Override
	public void cancelOrder(String orderId) {
		try {
            tradeMapper.cancelOrder(orderId);
        } catch (Exception e) {
            String message = "Cannot cancel order";
            logger.error(message, e);
            throw new DatabaseException(message, e);
        }		
	}

	@Override
	public void modifyOrder(Order order) {
		try {
            tradeMapper.modifyOrder(order);
        } catch (Exception e) {
            String message = "Unable to modify order with id=" + order.getOrderId();
            logger.error(message, e);
            throw new DatabaseException(message, e);
        }
	}

	@Override
	public Trade executeOrder(Order order) {
		Trade trade = null;
        try {
            String tradeId = 'T' + order.getOrderId();
            trade = new Trade(tradeId, order.getClientId(), order.getOrderId(), order.getInstrumentId(),
                    order.getQuantity(), order.getTargetPrice(), order.getDirection(), order.getCreationTime());
            this.insertTrade(trade);
        } catch (Exception ex) {
            throw new DatabaseException("Unable to execute order with id=" + order.getOrderId(), ex);
        }
        return trade;
    }

	@Override
	public List<Trade> getClientTradeHistory(String clientId) {
		
		if(clientId == null) {
			throw new IllegalArgumentException("client id cannot be null");
		}
		
		List<Trade> clientTradeHistory = tradeMapper.getClientTradeHistory(clientId);
		return clientTradeHistory;
	}
}
	
