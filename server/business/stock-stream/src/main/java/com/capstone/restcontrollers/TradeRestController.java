package com.capstone.restcontrollers;

import java.sql.Timestamp;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.exceptions.PortfolioException;
import com.capstone.exceptions.TradeException;
import com.capstone.models.Trade;
import com.capstone.models.TradeRequest;
import com.capstone.services.TradeService;


@RestController
@RequestMapping("stock_stream/trades")
public class TradeRestController {
	@Autowired
	private TradeService tradeService;

	@Autowired
	private Logger logger;
    
    
    @PostMapping(value = "/{direction}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> addTrade(@RequestBody TradeRequest tradeRequest) {
        Trade trade = new Trade();
        trade.setTradeId(tradeRequest.getTradeId());
        trade.setDirection(tradeRequest.getDirection());
        trade.setExecutionPrice(tradeRequest.getExecutionPrice());
        trade.setQuantity(tradeRequest.getQuantity());
        trade.setClientId(tradeRequest.getClientId());
        trade.setInstrumentId(tradeRequest.getInstrumentId());
        trade.setCreationTime(new Timestamp(System.currentTimeMillis()));

        try {
            if ("B".equalsIgnoreCase(trade.getDirection())) {
                tradeService.executeTrade(trade); 
            } else if ("S".equalsIgnoreCase(trade.getDirection())) {
                tradeService.executeTrade(trade); 
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid trade direction");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Trade added successfully");
        } catch (TradeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding trade: " + e.getMessage());
        } catch (PortfolioException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating portfolio: " + e.getMessage());
        }
    }
}
