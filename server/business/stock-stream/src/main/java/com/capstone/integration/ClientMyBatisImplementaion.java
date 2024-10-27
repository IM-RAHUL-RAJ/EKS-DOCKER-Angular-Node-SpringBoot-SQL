package com.capstone.integration;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.capstone.integration.mapper.ClientMapper;
import com.capstone.models.Client;

@Repository("clientDao")
public class ClientMyBatisImplementaion implements ClientDao {

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public boolean isEmailUnique(String email) {
        return clientMapper.isEmailUnique(email) == 0;
    }

    @Override
    public boolean isIdentificationUnique(String identificationType, String identificationNumber) {
        return clientMapper.isIdentificationUnique(identificationType, identificationNumber) == 0;
    }

    @Override
    public Client findByEmail(String email) {
        return clientMapper.findByEmail(email);
    }

    @Override
    public void save(Client client) {
        clientMapper.save(client);
    }
}