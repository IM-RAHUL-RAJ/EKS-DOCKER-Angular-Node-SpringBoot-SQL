package com.capstone.services.v2;

import com.capstone.models.Client;

public interface ClientService {
	boolean verifyEmail(String email);
    Client login(String email, String password);
    void register(Client client);
}
