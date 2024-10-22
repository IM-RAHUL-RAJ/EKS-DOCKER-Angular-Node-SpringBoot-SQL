package com.capstone.restcontrollers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.capstone.dto.EmailDTO;
import com.capstone.dto.LivePricingResponse;
import com.capstone.exceptions.EmailAlreadyExistsException;
import com.capstone.exceptions.IdentificationAlreadyExistsException;
import com.capstone.exceptions.InvalidEmailException;
import com.capstone.integration.ClientHoldingDao;
import com.capstone.integration.FmtsDao;
import com.capstone.models.Client;
import com.capstone.models.Holding;
import com.capstone.services.v2.ClientService;
import com.capstone.services.v2.HoldingService;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

	
	 @Autowired
	    private Logger logger;

	    @Autowired
	    private ClientHoldingDao dao;

	    @Autowired
	    private HoldingService service;

	    @Autowired
	    private FmtsDao fmtsDao;
	
	@Autowired
	private ClientService clientService;

	@PostMapping("/verify-email")
	public ResponseEntity<?> verifyEmail(@RequestBody EmailDTO emailDTO) {
		try {
			boolean isUnique = clientService.verifyEmail(emailDTO.getEmail());
			if (isUnique)
				return new ResponseEntity<>(true, HttpStatus.OK);
			else
				throw new EmailAlreadyExistsException("Email is not unique");

		} catch (InvalidEmailException e) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} catch (EmailAlreadyExistsException e) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		} catch (Exception e) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Client client) {
		try {
			Client loggedInClient = clientService.login(client.getEmail(), client.getPassword());
			return new ResponseEntity<>(loggedInClient, HttpStatus.OK);
		} catch (InvalidEmailException e) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Client client) {
		try {
			Client loggedInClientAfterRegistration = clientService.register(client);
			return new ResponseEntity<>(loggedInClientAfterRegistration, HttpStatus.CREATED);
		} catch (EmailAlreadyExistsException | IdentificationAlreadyExistsException | DuplicateKeyException e) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
    @GetMapping(value = "/holdings/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Holding>> getWidgets(@PathVariable String id) {
        try {
            List<Holding> holdings = service.getClientPortfolio(id);
            if (holdings != null && !holdings.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(holdings);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            logger.error("problem getting widgets", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve books", e);
        }
    }
    
    @GetMapping(value = "/live-prices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LivePricingResponse>> getLivePrices() {
        try {
            List<LivePricingResponse> livePricing = fmtsDao.getLivePricing();
            return ResponseEntity.status(HttpStatus.OK).body(livePricing);
        } catch (Exception e) {
            logger.error("problem getting live prices", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve live prices", e);
        }
    }
}
