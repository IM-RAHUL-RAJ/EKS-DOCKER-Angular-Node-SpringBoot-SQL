package com.capstone.services.v2;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.capstone.exceptions.PortfolioException;
import com.capstone.integration.ClientHoldingDao;
import com.capstone.integration.ClientHoldingDaoImpl;
import com.capstone.integration.ClientMyBatisImplementaion;
import com.capstone.integration.ClientMybatisHoldingsDaoImpl;
import com.capstone.models.Holding;

@Service
@Primary
public class HoldingServiceImpl implements HoldingService {
	
	@Autowired
	private ClientMybatisHoldingsDaoImpl dao;

	@Override
	public void addPortfolioItem(Holding portfolioItem) throws PortfolioException {
		if (portfolioItem.getInstrumentId() == null || portfolioItem.getInstrumentId().isEmpty()
				|| portfolioItem.getInstrument() == null) {
			throw new PortfolioException("Instrument ID is invalid.");
		}
		dao.addClientHolding(portfolioItem);
	
	}
	
	@Override
	public void removePortfolioItem(Holding portfolioItem, int quantityToRemove) throws PortfolioException {
		validatePortfolio(portfolioItem);
		Holding existingPortfolio = getPortfolioById(portfolioItem.getInstrumentId(), portfolioItem.getClientId());
		if (existingPortfolio.getQuantity() > quantityToRemove) {
			existingPortfolio.setQuantity(existingPortfolio.getQuantity() - quantityToRemove);
		} else {
			dao.removeClientHolding(existingPortfolio.getClientId(), existingPortfolio.getInstrumentId());
		}
	}
	
	@Override
	public void updatePortfolioItem(Holding updatedPortfolioItem) throws PortfolioException {
		validatePortfolio(updatedPortfolioItem);
		Holding existingPortfolio = getPortfolioById(updatedPortfolioItem.getInstrumentId(),
				updatedPortfolioItem.getClientId());
		existingPortfolio.setQuantity(updatedPortfolioItem.getQuantity());
		existingPortfolio.setAveragePrice(updatedPortfolioItem.getAveragePrice());
		existingPortfolio.setInvestedCapital(updatedPortfolioItem.getInvestedCapital());
		existingPortfolio.setLtp(updatedPortfolioItem.getLtp());
		existingPortfolio.setPercentChange(updatedPortfolioItem.getPercentChange());
		existingPortfolio.setProfitLoss(updatedPortfolioItem.getProfitLoss());
		existingPortfolio.setDayChangePercent(updatedPortfolioItem.getDayChangePercent());
		dao.updateClientHolding(existingPortfolio);
	}
	
	@Override
	public List<Holding> getClientPortfolio(String clientId) throws SQLException {
		List<Holding> clientPortfolios = dao.getClientHoldings(clientId);
		if (clientPortfolios.isEmpty()) {
			throw new NoSuchElementException("Client ID " + clientId + " not found.");
		}
		return clientPortfolios;
	}

	private Holding getPortfolioById(String instrumentId, String clientId) throws PortfolioException {
		return dao.getClientHolding(clientId, instrumentId);
	}

	private void validatePortfolio(Holding portfolio) throws PortfolioException {
		if (portfolio.getInstrumentId() == null || portfolio.getInstrumentId().isEmpty()) {
			throw new PortfolioException("Instrument ID is invalid.");
		}
		if (portfolio.getClientId() == null || portfolio.getClientId().isEmpty()) {
			throw new PortfolioException("Client ID is invalid.");
		}
		if (portfolio.getQuantity() < 0) {
			throw new PortfolioException("Quantity cannot be negative.");
		}
	}

}
