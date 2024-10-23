package com.capstone.restcontrollers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;

import com.capstone.exceptions.InvestmentPreferenceAlreadyExists;
import com.capstone.exceptions.InvestmentPreferenceWithClientIdNotFound;
import com.capstone.exceptions.UserNotLoggedInToPerformAction;
import com.capstone.models.InvestmentPreference;
import com.capstone.models.Trade;
import com.capstone.services.InvestmentPreferenceService;

import ch.qos.logback.core.net.server.Client;

@RestController
@RequestMapping("/stock_stream")
@CrossOrigin(origins = "http://localhost:4200")
public class InvestmentPreferenceController {
	
	@Autowired
	private InvestmentPreferenceService service;
	
	@Autowired
	private Logger logger;
	
	@GetMapping(value = "/investment_preference/{clientId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<InvestmentPreference> getClientInvestmentPreference(@PathVariable String clientId) {
		InvestmentPreference investmentPreference;
		ResponseEntity<InvestmentPreference> response;
		try {
			investmentPreference = service.getInvestmentPreference(clientId);
			response = ResponseEntity.status(HttpStatus.OK).body(investmentPreference);
		}catch(UserNotLoggedInToPerformAction e) {
			logger.error("Error UserNotLoggedInToPerformAction", e);
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}catch(InvestmentPreferenceWithClientIdNotFound e) {
			logger.error("Error InvestmentPreferenceWithClientIdNotFound", e);
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (RuntimeException e) {
			// all other exceptions will be handled here
			logger.error("Error", e);
			throw new ServerErrorException("Error", e);
		}
		return response;
	}
	
	@PostMapping(value = "/investment_preference")
	public ResponseEntity<InvestmentPreference> addClientInvestmentPreference(@RequestBody InvestmentPreference investmentPreference) {
		InvestmentPreference addedInvestmentPreference;
		ResponseEntity<InvestmentPreference> response;
		try {
			addedInvestmentPreference = service.addInvestmentPreference(investmentPreference);
			response = ResponseEntity.status(HttpStatus.OK).body(investmentPreference);
		}catch(UserNotLoggedInToPerformAction e) {
			logger.error("Error UserNotLoggedInToPerformAction", e);
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}catch(InvestmentPreferenceAlreadyExists e) {
			logger.error("Error InvestmentPreferenceWithClientIdNotFound", e);
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (RuntimeException e) {
			// all other exceptions will be handled here
			logger.error("Error", e);
			throw new ServerErrorException("Error", e);
		}
		return response;
	}
	
	@PutMapping(value = "/investment_preference")
	public ResponseEntity<InvestmentPreference> updateClientInvestmentPreference(@RequestBody InvestmentPreference investmentPreference) {
		InvestmentPreference updatedInvestmentPreference;
		ResponseEntity<InvestmentPreference> response;
		try {
			updatedInvestmentPreference = service.updateInvestmentPreference(investmentPreference);
			response = ResponseEntity.status(HttpStatus.OK).body(investmentPreference);
		}catch(UserNotLoggedInToPerformAction e) {
			logger.error("Error UserNotLoggedInToPerformAction", e);
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}catch(InvestmentPreferenceWithClientIdNotFound e) {
			logger.error("Error InvestmentPreferenceWithClientIdNotFound", e);
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (RuntimeException e) {
			// all other exceptions will be handled here
			logger.error("Error", e);
			throw new ServerErrorException("Error", e);
		}
		return response;
	}
	
	@DeleteMapping(value = "/investment_preference/{clientId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<InvestmentPreference> deleteClientInvestmentPreference(@PathVariable String clientId) {
		InvestmentPreference investmentPreference;
		ResponseEntity<InvestmentPreference> response;
		try {
			investmentPreference = service.removeInvestmentPreference(clientId);
			response = ResponseEntity.status(HttpStatus.OK).body(investmentPreference);
		}catch(UserNotLoggedInToPerformAction e) {
			logger.error("Error UserNotLoggedInToPerformAction", e);
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}catch(InvestmentPreferenceWithClientIdNotFound e) {
			logger.error("Error InvestmentPreferenceWithClientIdNotFound", e);
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (RuntimeException e) {
			// all other exceptions will be handled here
			logger.error("Error", e);
			throw new ServerErrorException("Error", e);
		}
		return response;
	}

}
