package com.capstone.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.capstone.exceptions.ClientHasNoHoldingsToSellException;
import com.capstone.models.Holding;
import com.capstone.models.Price;
import com.capstone.services.v2.RoboAdvisorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/stock_stream/robo_advisor")
@CrossOrigin(origins = "http://localhost:4200")
public class RoboAdvisorController {

	@Autowired
	private RoboAdvisorService roboAdvisorService;

	@GetMapping("/buy/{clientId}")
	public ResponseEntity<List<Price>> getBuySuggestions(@PathVariable String clientId) {

		ResponseEntity<List<Price>> responseEntity;
		
		try {
			List<Price> suggestedInstruments = roboAdvisorService.getBuySuggestions(clientId);
			if(suggestedInstruments.size()==0) {
				responseEntity = ResponseEntity.noContent().build();
			}else {
				responseEntity = ResponseEntity.ok().body(suggestedInstruments);
			}
			
			return responseEntity;
		}catch (RuntimeException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/sell/{clientId}")
	public ResponseEntity<List<Holding>> getSellSuggestions(@PathVariable String clientId) {

		ResponseEntity<List<Holding>> responseEntity;
		
		try {
			List<Holding> suggestedInstruments = roboAdvisorService.getSellSuggestions(clientId);
			if(suggestedInstruments.size()==0) {
				responseEntity = ResponseEntity.noContent().build();
			}else {
				responseEntity = ResponseEntity.ok().body(suggestedInstruments);
			}
			
			return responseEntity;
		}catch (ClientHasNoHoldingsToSellException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		catch (RuntimeException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
