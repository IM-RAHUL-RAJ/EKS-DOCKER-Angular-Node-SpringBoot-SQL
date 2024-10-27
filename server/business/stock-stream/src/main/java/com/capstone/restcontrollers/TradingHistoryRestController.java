package com.capstone.restcontrollers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.capstone.exceptions.NoTradingHistoryFoundForClientException;
import com.capstone.models.Trade;
import com.capstone.services.TradeHistoryService;

@RestController
@RequestMapping("stock_stream/trades")
public class TradingHistoryRestController {

	@Autowired
	private TradeHistoryService historyService;

	@Autowired
	private Logger logger;

	@GetMapping(value = "/trade_history/{clientId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Trade>> getClientTradingHistory(@PathVariable String clientId) {

		ResponseEntity<List<Trade>> responseEntity;

		try {
			List<Trade> clientTradingHistory = historyService.getClientTradingHistory(clientId);

			responseEntity = ResponseEntity.ok().body(clientTradingHistory);

			return responseEntity;

		} catch (NoTradingHistoryFoundForClientException e) {
			logger.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"No Trade History Found for client id : " + clientId);

		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
