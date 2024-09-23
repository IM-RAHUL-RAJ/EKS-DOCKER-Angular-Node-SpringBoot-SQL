package com.capstone.integration;

import java.sql.SQLException;

import com.capstone.models.Client;

public interface ClientDao {
    boolean verifyEmailAddress(String emailAddress);
    void addClient(Client client) throws SQLException;
    boolean verifyLogin(String username, String password) throws SQLException;
}
