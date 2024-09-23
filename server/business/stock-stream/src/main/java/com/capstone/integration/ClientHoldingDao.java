package com.capstone.integration;
import java.sql.SQLException;
import java.util.List;

import com.capstone.models.Portfolio;

public interface ClientPortfolioDao {
    List<Portfolio> getClientPortfolio(String clientId) throws SQLException;

}
