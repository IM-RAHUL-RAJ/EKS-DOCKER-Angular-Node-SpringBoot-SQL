package com.capstone.services.v2;

import java.util.List;
import com.capstone.exceptions.PortfolioException;
import com.capstone.models.Holding;
import java.sql.SQLException;

public interface HoldingService {

	public void addPortfolioItem(Holding portfolioItem) throws PortfolioException;
	public void removePortfolioItem(Holding portfolioItem, int quantityToRemove) throws PortfolioException;
	public void updatePortfolioItem(Holding updatedPortfolioItem) throws PortfolioException;
	public List<Holding> getClientPortfolio(String clientId) throws SQLException;

}