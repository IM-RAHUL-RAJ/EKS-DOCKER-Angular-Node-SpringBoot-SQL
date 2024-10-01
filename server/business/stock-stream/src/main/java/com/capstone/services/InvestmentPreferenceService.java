package com.capstone.services;

import java.util.ArrayList;
import java.util.List;

import com.capstone.exceptions.InvestmentPreferenceAlreadyExists;
import com.capstone.exceptions.InvestmentPreferenceWithClientIdNotFound;
import com.capstone.exceptions.RoboAdvisorMandatoryException;
import com.capstone.exceptions.UserNotLoggedInToPerformAction;
import com.capstone.integration.InvestmentPreferenceDao;
import com.capstone.models.IncomeCategory;
import com.capstone.models.InvestmentPreference;
import com.capstone.models.InvestmentPurpose;
import com.capstone.models.InvestmentYear;
import com.capstone.models.RiskTolerance;

public class InvestmentPreferenceService {

	private List<InvestmentPreference> investmentPreferences = new ArrayList<InvestmentPreference>();
	private ClientService clientService;
	private InvestmentPreferenceDao investmentPreferenceDao;

	public InvestmentPreferenceService(ClientService clientService,InvestmentPreferenceDao investmentPreferenceDao) {
		this.clientService = clientService;
		this.investmentPreferenceDao = investmentPreferenceDao;
	}

	public int getLength() {
		return this.investmentPreferences.size();
	}

	public InvestmentPreference getInvestmentPreference(String clientId)
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction {

		if (!clientService.isUserLoggedIn(clientId)) {
			throw new UserNotLoggedInToPerformAction("The given user with client Id is not logged in..");
		}

		InvestmentPreference investmentPreference = investmentPreferenceDao.getInvestmentPreference(clientId);
		
		if(investmentPreference !=null) {
			return investmentPreference;
		}

		throw new InvestmentPreferenceWithClientIdNotFound(
				"The investment preference with the given client id is not found..");

	}

	private boolean isValidInvestmentPreference(InvestmentPreference investmentPreference) {
		for (InvestmentPreference ip : this.investmentPreferences) {
			if (ip.equals(investmentPreference)) {
				return true;
			}
		}
		return false;
	}

	public InvestmentPreference addInvestmentPreference(InvestmentPreference investmentPreference)
			throws InvestmentPreferenceAlreadyExists, UserNotLoggedInToPerformAction {
		if(!clientService.isUserLoggedIn(investmentPreference.getClientId())) {
			throw new UserNotLoggedInToPerformAction("The given user with client Id is not logged in..");
		}
		if (isValidInvestmentPreference(investmentPreference)) {
			throw new InvestmentPreferenceAlreadyExists(
					"The given investment preference with the client Id already exists :"
							+ investmentPreference.getClientId());
		}

		InvestmentPreference insertedInvestmentPreference =  investmentPreferenceDao.addInvestmentPreference(investmentPreference);
		
		return insertedInvestmentPreference;

	}

	public InvestmentPreference updateInvestmentPreference(InvestmentPreference updatedInvestmentPreference)
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction {

		if (!clientService.isUserLoggedIn(updatedInvestmentPreference.getClientId())) {
			throw new UserNotLoggedInToPerformAction("The given user with client Id is not logged in..");
		}
		
		InvestmentPreference investmentPreference = this.investmentPreferenceDao.updateInvestmentPreference(updatedInvestmentPreference);

		return investmentPreference;

	}

	public InvestmentPreference removeInvestmentPreference(String clientId)
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction {

		if (!clientService.isUserLoggedIn(clientId)) {
			throw new UserNotLoggedInToPerformAction("The given user with client Id is not logged in..");
		}
		
		InvestmentPreference investmentPreference = this.investmentPreferenceDao.removeInvestmentPreference(clientId);
		
		return investmentPreference;

	}
}
