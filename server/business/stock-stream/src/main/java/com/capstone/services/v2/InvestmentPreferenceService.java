package com.capstone.services.v2;

import com.capstone.exceptions.InvestmentPreferenceAlreadyExists;
import com.capstone.exceptions.InvestmentPreferenceWithClientIdNotFound;
import com.capstone.exceptions.UserNotLoggedInToPerformAction;
import com.capstone.models.InvestmentPreference;
import com.capstone.services.ClientService;

public interface InvestmentPreferenceService {

	InvestmentPreference getInvestmentPreference(String clientId)
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction;

	InvestmentPreference addInvestmentPreference(InvestmentPreference investmentPreference)
			throws InvestmentPreferenceAlreadyExists, UserNotLoggedInToPerformAction;

	InvestmentPreference updateInvestmentPreference(InvestmentPreference updatedInvestmentPreference)
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction;

	InvestmentPreference removeInvestmentPreference(String clientId)
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction;

	void setClientService(ClientService clientService);

}