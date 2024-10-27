package com.capstone.integration;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.exceptions.DatabaseException;
import com.capstone.integration.mapper.ClientHoldingsMapper;
import com.capstone.models.Holding;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientMybatisHoldingsDaoImpl implements ClientHoldingDao {

    @Autowired
    private ClientHoldingsMapper clientHoldingsMapper;

    @Override
    public List<Holding> getClientHoldings(String clientId) throws SQLException {
        if (clientId == null || clientId.isBlank()) {
            throw new IllegalArgumentException("Client ID cannot be null or blank");
        }
        return clientHoldingsMapper.getAllHoldings(clientId);
    }
    
    @Override
    public Holding getClientHolding(String clientId, String instrumentId) {
        if (clientId == null || instrumentId == null || clientId.isBlank() || instrumentId.isBlank()) {
            throw new IllegalArgumentException("Client ID and Instrument ID cannot be null or blank");
        }
        return clientHoldingsMapper.getHolding(clientId, instrumentId);
    }


    @Override
    public Holding addClientHolding(Holding holding) {
        if (holding == null) {
            throw new IllegalArgumentException("Holding cannot be null");
        }
        if (clientHoldingsMapper.insertHolding(holding) == 0) {
            throw new DatabaseException("Failed to add holding");
        }
        return holding;
    }

    @Override
    public Holding updateClientHolding(Holding holding) {
        if (holding == null) {
            throw new IllegalArgumentException("Holding cannot be null");
        }
        if (clientHoldingsMapper.updateHolding(holding) == 0) {
            throw new DatabaseException("Failed to update holding");
        }
        return holding;
    }

   
    @Override
    public Holding removeClientHolding(String clientId, String instrumentId) {
        if (clientId == null || instrumentId == null || clientId.isBlank() || instrumentId.isBlank()) {
            throw new IllegalArgumentException("Client ID and Instrument ID cannot be null or blank");
        }
        Holding holding = getClientHolding(clientId, instrumentId);
        if (clientHoldingsMapper.deleteHolding(clientId, instrumentId) == 0) {
            throw new DatabaseException("Failed to remove holding");
        }
        return holding;
    }
}
