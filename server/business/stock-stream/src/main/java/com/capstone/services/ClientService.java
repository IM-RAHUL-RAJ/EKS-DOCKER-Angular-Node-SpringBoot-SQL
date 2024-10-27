package com.capstone.services;

import com.capstone.models.Client;

public interface ClientService {
	boolean verifyEmail(String email);
    Client login(String email, String password);
    Client register(Client client);
}
