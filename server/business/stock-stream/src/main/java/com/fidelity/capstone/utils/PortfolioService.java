package com.fidelity.capstone.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.fidelity.capstone.stock_stream.Portfolio;

public class PortfolioService {

    private List<Portfolio> portfolios;

    public PortfolioService() {
        this.portfolios = new ArrayList<>();
        portfolios.add(new Portfolio("AAPL", "1", "6734GHJ",10, 150.0, 1500.0, 155.0, 3.33, 50.0, 1.5));
        portfolios.add(new Portfolio("GOOGL", "2","8734GHJ" ,5, 1000.0, 5000.0, 1050.0, 5.0, 250.0, 2.0));
    }

    public List<Portfolio> getClientPortfolio(String clientId) {
    	List<Portfolio> clientPortfolios = portfolios.stream()
                .filter(portfolio -> portfolio.getClientId().equals(clientId))
                .collect(Collectors.toList());
    	if (clientPortfolios.isEmpty()) {
    		throw new NoSuchElementException("Client ID " + clientId + " not found.");
    	}
    	return clientPortfolios;

    }
}
