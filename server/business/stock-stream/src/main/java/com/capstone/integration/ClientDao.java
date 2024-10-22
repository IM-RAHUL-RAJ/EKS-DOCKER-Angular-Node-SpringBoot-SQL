package com.capstone.integration;

import java.sql.SQLException;

import com.capstone.models.Client;

public interface ClientDao {
    boolean isEmailUnique(String email);
    boolean isIdentificationUnique(String identificationType, String identificationNumber);
    Client findByEmail(String email);
    void save(Client client);
}
