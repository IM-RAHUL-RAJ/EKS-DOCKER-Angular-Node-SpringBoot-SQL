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

import com.capstone.models.Portfolio;

public class ClientPortfolioDaoImpl implements ClientPortfolioDao{


    private static final String GET_CLIENT_PORTFOLIO_QUERY = "SELECT * FROM portfolios WHERE clientId = ?";

    private Connection connection;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private DataSource dataSource;

    public ClientPortfolioDaoImpl(DataSource ds) {
        dataSource = ds;
    }

    @Override
    public List<Portfolio> getClientPortfolio(String clientId) throws SQLException {
        String query = "SELECT instrument, instrumentId, clientId, quantity, averagePrice, investedCapital, ltp, percentChange, profitLoss, dayChangePercent " +
                       "FROM portfolios WHERE clientId = ?";

        if(clientId.isBlank())
        {
        	throw new SQLException();
        }
        List<Portfolio> portfolios = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, clientId);  // Set the parameter before executing the query
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Portfolio portfolio = new Portfolio();
                        portfolio.setInstrument(rs.getString("instrument"));
                        portfolio.setInstrumentId(rs.getString("instrumentId"));
                        portfolio.setClientId(rs.getString("clientId"));
                        portfolio.setQuantity(rs.getInt("quantity"));
                        portfolio.setAveragePrice(rs.getDouble("averagePrice"));
                        portfolio.setInvestedCapital(rs.getDouble("investedCapital"));
                        portfolio.setLtp(rs.getDouble("ltp"));
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
}


