package com.capstone.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.exceptions.PortfolioException;
import com.capstone.integration.ClientHoldingDao;
import com.capstone.models.Holding;

public class HoldingService {

	@Autowired
    private ClientHoldingDao dao;

    
    
    private List<Holding> portfolios;

    public HoldingService() {
        this.portfolios = new ArrayList<>();
    }
    

    public void addPortfolioItem(Holding portfolioItem) throws PortfolioException {
    	if (portfolioItem.getInstrumentId() == null || portfolioItem.getInstrumentId().isEmpty() || portfolioItem.getInstrument()==null) {
            throw new PortfolioException("Instrument ID is invalid.");
        }        portfolios.add(portfolioItem);
    }

    public void removePortfolioItem(Holding portfolioItem, int quantityToRemove) throws PortfolioException {
        validatePortfolio(portfolioItem);
        Holding existingPortfolio = getPortfolioById(portfolioItem.getInstrumentId(), portfolioItem.getClientId());
        if (existingPortfolio.getQuantity() > quantityToRemove) {
            existingPortfolio.setQuantity(existingPortfolio.getQuantity() - quantityToRemove);
        } else {
            portfolios.remove(existingPortfolio);
        }
    }

    public void updatePortfolioItem(Holding updatedPortfolioItem) throws PortfolioException {
        validatePortfolio(updatedPortfolioItem);
        Holding existingPortfolio = getPortfolioById(updatedPortfolioItem.getInstrumentId(), updatedPortfolioItem.getClientId());
        existingPortfolio.setQuantity(updatedPortfolioItem.getQuantity());
        existingPortfolio.setAveragePrice(updatedPortfolioItem.getAveragePrice());
        existingPortfolio.setInvestedCapital(updatedPortfolioItem.getInvestedCapital());
        existingPortfolio.setLtp(updatedPortfolioItem.getLtp());
        existingPortfolio.setPercentChange(updatedPortfolioItem.getPercentChange());
        existingPortfolio.setProfitLoss(updatedPortfolioItem.getProfitLoss());
        existingPortfolio.setDayChangePercent(updatedPortfolioItem.getDayChangePercent());
    }
    public List<Holding> getClientPortfolio(String clientId) {
        List<Holding> clientPortfolios = portfolios.stream()
                .filter(portfolio -> portfolio.getClientId().equals(clientId))
                .collect(Collectors.toList());
        if (clientPortfolios.isEmpty()) {
            throw new NoSuchElementException("Client ID " + clientId + " not found.");
        }
        return clientPortfolios;
    }

    private Holding getPortfolioById(String instrumentId, String clientId) throws PortfolioException {
        return portfolios.stream()
                .filter(portfolio -> portfolio.getInstrumentId().equals(instrumentId) && portfolio.getClientId().equals(clientId))
                .findFirst()
                .orElseThrow(() -> new PortfolioException("Portfolio with Instrument ID " + instrumentId + " and Client ID " + clientId + " not found."));
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
