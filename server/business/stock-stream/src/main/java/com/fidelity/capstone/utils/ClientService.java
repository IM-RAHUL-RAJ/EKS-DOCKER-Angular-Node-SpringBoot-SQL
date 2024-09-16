package com.fidelity.capstone.utils;

import com.fidelity.capstone.stock_stream.Client;

public class ClientService {
    private Client registeredClient;
    private FmtsService mockFmtsService;

    public ClientService(FmtsService mockFmtsService) {
        this.mockFmtsService = mockFmtsService;
    }

    // to verify the existence of an email address
    public boolean verifyEmailAddress(String email) {
        return registeredClient != null && registeredClient.getEmail().equals(email);
    }

    // to process client registration
    public boolean registerClient(Client client) {
        if (registeredClient != null && registeredClient.getEmail().equals(client.getEmail())) {
            return false; // Email already registered
        }
        if (mockFmtsService.verifyClient(client.getIdentificationValue(), client.getCountry())) {
            registeredClient = client;
            return true;
        }
        return false; // FMTS service confirmation failed
    }

    // to process client login
    public boolean loginClient(String email, String password) {
        return registeredClient != null && registeredClient.getEmail().equals(email) && registeredClient.getPassword().equals(password);
    }
}

