package com.capstone.integration;
import java.sql.SQLException;
import java.util.List;

import com.capstone.models.Holding;

public interface ClientHoldingDao {
    public List<Holding> getClientHoldings(String clientId) throws SQLException;
    

    public Holding addClientHolding(Holding holding);


	Holding updateClientHolding(Holding holding);


	Holding getClientHolding(String clientId, String instrumentId);


	Holding removeClientHolding(String clienId, String instrumentId);
}
