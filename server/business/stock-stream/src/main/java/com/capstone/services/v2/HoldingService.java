package com.capstone.services.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.capstone.exceptions.InvestmentPreferenceAlreadyExists;
import com.capstone.exceptions.InvestmentPreferenceWithClientIdNotFound;
import com.capstone.exceptions.PortfolioException;
import com.capstone.exceptions.UserNotLoggedInToPerformAction;
import com.capstone.integration.ClientHoldingDaoImpl;
import com.capstone.integration.ClientMyBatisImplementaion;
import com.capstone.models.Holding;
import com.capstone.models.InvestmentPreference;
import com.capstone.services.ClientService;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public interface HoldingService {

	public void addPortfolioItem(Holding portfolioItem) throws PortfolioException;
	public void removePortfolioItem(Holding portfolioItem, int quantityToRemove) throws PortfolioException;
	public void updatePortfolioItem(Holding updatedPortfolioItem) throws PortfolioException;
	public List<Holding> getClientPortfolio(String clientId) throws SQLException;

}