package com.capstone.services;

import java.util.ArrayList;
import java.util.List;

import com.capstone.exceptions.InvestmentPreferenceAlreadyExists;
import com.capstone.exceptions.InvestmentPreferenceWithClientIdNotFound;
import com.capstone.exceptions.RoboAdvisorMandatoryException;
import com.capstone.exceptions.UserNotLoggedInToPerformAction;
import com.capstone.models.IncomeCategory;
import com.capstone.models.InvestmentPreference;
import com.capstone.models.InvestmentPurpose;
import com.capstone.models.InvestmentYear;
import com.capstone.models.RiskTolerance;

public class InvestmentPreferenceService {

	private List<InvestmentPreference> investmentPreferences = new ArrayList<InvestmentPreference>();
	private ClientService clientService;

	public InvestmentPreferenceService(ClientService clientService) {
		this.clientService = clientService;
	}

	public int getLength() {
		return this.investmentPreferences.size();
	}

	public InvestmentPreference getInvestmentPreference(String clientId)
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction {

		if (!clientService.isUserLoggedIn(clientId)) {
			throw new UserNotLoggedInToPerformAction("The given user with client Id is not logged in..");
		}

		for (InvestmentPreference investmentPreference : this.investmentPreferences) {
			if (investmentPreference.getClientId().equals(clientId)) {
				return investmentPreference;
			}
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

		this.investmentPreferences.add(investmentPreference);
		
		return investmentPreference;

	}

	public InvestmentPreference updateInvestmentPreference(InvestmentPreference updatedInvestmentPreference)
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction {

		InvestmentPreference investmentPreference = this
				.getInvestmentPreference(updatedInvestmentPreference.getClientId());

		investmentPreference.setInvestmentPurpose(updatedInvestmentPreference.getInvestmentPurpose());

		investmentPreference
				.setInvestmentPurposeDescription(updatedInvestmentPreference.getInvestmentPurposeDescription());

		investmentPreference.setIncomeCategory(updatedInvestmentPreference.getIncomeCategory());

		investmentPreference.setRiskTolerance(updatedInvestmentPreference.getRiskTolerance());

		investmentPreference.setInvestmentYear(updatedInvestmentPreference.getInvestmentYear());

		return investmentPreference;

	}

	public InvestmentPreference removeInvestmentPreference(String clientId)
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction {

		InvestmentPreference investmentPreference = this.getInvestmentPreference(clientId);

		this.investmentPreferences.remove(investmentPreference);
		
		return investmentPreference;

	}
}
