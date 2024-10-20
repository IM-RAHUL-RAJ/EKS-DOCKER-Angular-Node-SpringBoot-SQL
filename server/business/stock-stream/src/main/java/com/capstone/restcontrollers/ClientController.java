package com.capstone.restcontrollers;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.capstone.dto.EmailDTO;
import com.capstone.exceptions.EmailAlreadyExistsException;
import com.capstone.exceptions.IdentificationAlreadyExistsException;
import com.capstone.exceptions.InvalidEmailException;
import com.capstone.models.Client;
import com.capstone.services.v2.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

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
			clientService.register(client);
			return new ResponseEntity<>("Client registered successfully!", HttpStatus.CREATED);
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
}
