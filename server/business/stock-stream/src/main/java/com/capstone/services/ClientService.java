package com.capstone.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.capstone.models.Client;

public class ClientService {

	private List<Client> clients = new ArrayList<Client>();

	private HashMap<String, String> loggedInUsers = new HashMap<String, String>();

	private Client registeredClient;
	private FmtsService mockFmtsService;

	public ClientService(FmtsService mockFmtsService) {
		this.mockFmtsService = mockFmtsService;
	}

	// to verify the existence of an email address
	public boolean verifyEmailAddress(String email) {
		for (Client cli : this.clients) {
			if (cli != null && cli.getEmail().equals(email)) {
				return true; // Email already registered
			}
		}
		
		return false;
		
	}

	// to process client registration
	public boolean registerClient(Client client) {

		for (Client cli : this.clients) {
			if (cli != null && cli.getEmail().equals(client.getEmail())) {
				return false; // Email already registered
			}
		}
		if (mockFmtsService.verifyClient(client.getIdentificationNumber(), client.getCountry())) {
			registeredClient = client;

			this.clients.add(client);
			return true;
		}
		return false; // FMTS service confirmation failed
	}

	// to process client login
	public boolean loginClient(String email, String password) {

		for (Client cli : this.clients) {
			if (cli != null && cli.getEmail().equals(email)
					&& cli.getPassword().equals(password)) {
				
				if(this.isUserLoggedIn(cli.getClientId())){
					return false; // User Already in loggedUsersList
				}
				

				this.loggedInUsers.put(cli.getClientId(),UUID.randomUUID().toString());
				
				return true;
			}
		}

		return false;
	}
	
	public boolean isUserLoggedIn(String clientId) {
		return this.loggedInUsers.get(clientId)!=null;
		
	}
}
