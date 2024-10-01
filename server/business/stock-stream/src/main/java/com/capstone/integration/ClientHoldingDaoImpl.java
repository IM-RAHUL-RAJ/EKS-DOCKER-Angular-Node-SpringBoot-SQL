package com.capstone.integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capstone.exceptions.DatabaseException;
import com.capstone.models.Holding;

public class ClientHoldingDaoImpl implements ClientHoldingDao {

	private static final String GET_CLIENT_PORTFOLIO_QUERY = "SELECT * FROM portfolios WHERE clientId = ?";

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private DataSource dataSource;

	public ClientHoldingDaoImpl(DataSource ds) {
		dataSource = ds;
	}

	@Override
	public List<Holding> getClientHoldings(String clientId) {
		String query = "SELECT instrument, instrumentId, clientId, quantity, averagePrice, investedCapital, ltp, percentChange, profitLoss, dayChangePercent "
				+ "FROM holdings WHERE clientId = ?";

		if (clientId == null || clientId.isBlank()) {
			throw new IllegalArgumentException();
		}
		List<Holding> portfolios = new ArrayList<>();
		try {
			Connection conn = dataSource.getConnection();
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, clientId); // Set the parameter before executing the query
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						Holding portfolio = new Holding();
						portfolio.setInstrument(rs.getString("instrument"));
						portfolio.setInstrumentId(rs.getString("instrumentId"));
						portfolio.setClientId(rs.getString("clientId"));
						portfolio.setQuantity(rs.getInt("quantity"));
						portfolio.setAveragePrice(rs.getBigDecimal("averagePrice").setScale(2));
						portfolio.setInvestedCapital(rs.getDouble("investedCapital"));
						portfolio.setLtp(rs.getBigDecimal("ltp").setScale(2));
						portfolio.setPercentChange(rs.getDouble("percentChange"));
						portfolio.setProfitLoss(rs.getDouble("profitLoss"));
						portfolio.setDayChangePercent(rs.getDouble("dayChangePercent"));
						portfolios.add(portfolio);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return portfolios;
	}

	
	@Override
	public Holding getClientHolding(String clientId,String instrumentId) {
		String query = "SELECT instrument, instrumentId, clientId, quantity, averagePrice, investedCapital, ltp, percentChange, profitLoss, dayChangePercent "
				+ "FROM holdings WHERE clientId = ? AND instrumentid=?";

		if (clientId == null || instrumentId == null || clientId.isBlank() || instrumentId.isBlank()) {
			throw new IllegalArgumentException();
		}
		
		try {
			Connection conn = dataSource.getConnection();
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, clientId);
				stmt.setString(2, instrumentId);
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						Holding holding = new Holding();
						holding.setInstrument(rs.getString("instrument"));
						holding.setInstrumentId(rs.getString("instrumentId"));
						holding.setClientId(rs.getString("clientId"));
						holding.setQuantity(rs.getInt("quantity"));
						holding.setAveragePrice(rs.getBigDecimal("averagePrice").setScale(2));
						holding.setInvestedCapital(rs.getDouble("investedCapital"));
						holding.setLtp(rs.getBigDecimal("ltp").setScale(2));
						holding.setPercentChange(rs.getDouble("percentChange"));
						holding.setProfitLoss(rs.getDouble("profitLoss"));
						holding.setDayChangePercent(rs.getDouble("dayChangePercent"));
						
						return holding;
						
					}else {
						throw new DatabaseException();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException();
		}
	}
	
	@Override
	public Holding addClientHolding(Holding holding) {

		if (holding == null) {
			throw new IllegalArgumentException();
		}

		try {
			Connection connection = dataSource.getConnection();

			String query = """
					INSERT INTO holdings
					(instrument, instrumentId, clientId, 
					quantity, averagePrice, investedCapital, 
					ltp, percentChange, profitLoss, 
					dayChangePercent)
					VALUES (?,?,?,?,?,?,?,?,?,?)
					""";

			try(PreparedStatement statement = connection.prepareStatement(query)){
				
				statement.setString(1, holding.getInstrument());
				statement.setString(2, holding.getInstrumentId());
				statement.setString(3, holding.getClientId());
				statement.setInt(4, holding.getQuantity());
				statement.setBigDecimal(5, holding.getAveragePrice());
				statement.setDouble(6, holding.getInvestedCapital());
				statement.setBigDecimal(7, holding.getLtp());
				statement.setDouble(8, holding.getPercentChange());
				statement.setDouble(9, holding.getProfitLoss());
				statement.setDouble(10, holding.getDayChangePercent());
				
				if(statement.executeUpdate()==0) {
					throw new DatabaseException();
				}
				
				return holding;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException();
		}
	}
	
	@Override
	public Holding updateClientHolding(Holding holding) {

		if (holding == null) {
			throw new IllegalArgumentException();
		}

		try {
			Connection connection = dataSource.getConnection();

			String query = """
					UPDATE holdings SET
					instrument=?,
					instrumentId=?, 
					clientId=?, 
					quantity=?, 
					averagePrice=?, 
					investedCapital=?, 
					ltp=?,
					percentChange=?, 
					profitLoss=?, 
					dayChangePercent=?
					WHERE
					clientid=? AND instrumentid=?
					""";

			try(PreparedStatement statement = connection.prepareStatement(query)){
				
				statement.setString(1, holding.getInstrument());
				statement.setString(2, holding.getInstrumentId());
				statement.setString(3, holding.getClientId());
				statement.setInt(4, holding.getQuantity());
				statement.setBigDecimal(5, holding.getAveragePrice());
				statement.setDouble(6, holding.getInvestedCapital());
				statement.setBigDecimal(7, holding.getLtp());
				statement.setDouble(8, holding.getPercentChange());
				statement.setDouble(9, holding.getProfitLoss());
				statement.setDouble(10, holding.getDayChangePercent());
				statement.setString(11, holding.getClientId());
				statement.setString(12, holding.getInstrumentId());
				
				if(statement.executeUpdate()==0) {
					throw new DatabaseException();
				}
				
				return holding;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException();
		}
	}
	
	@Override
	public Holding removeClientHolding(String clientId,String instrumentId) {

		if (clientId == null || instrumentId == null || clientId.isBlank() || instrumentId.isBlank()) {
			throw new IllegalArgumentException();
		}

		try {
			Connection connection = dataSource.getConnection();

			String query = """
					DELETE FROM holdings
					WHERE
					clientid=? AND instrumentid=?
					""";

			Holding holding = getClientHolding(clientId, instrumentId);
			
			try(PreparedStatement statement = connection.prepareStatement(query)){
				statement.setString(1, clientId);
				statement.setString(2, instrumentId);
				
				
				if(statement.executeUpdate()==0) {
					throw new DatabaseException();
				}
				
				return holding;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException();
		}
	}
}
