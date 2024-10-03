package com.capstone.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

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

@Service
@Primary
public class InvestmentPreferenceServiceImpl implements InvestmentPreferenceService {

	private List<InvestmentPreference> investmentPreferences = new ArrayList<InvestmentPreference>();
	private ClientService clientService;
	@Autowired
	private InvestmentPreferenceDao investmentPreferenceDao;

	public InvestmentPreferenceServiceImpl() {
	}

	public int getLength() {
		return this.investmentPreferences.size();
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	@Override
	public InvestmentPreference getInvestmentPreference(String clientId)
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction {

		if (!clientService.isUserLoggedIn(clientId)) {
			throw new UserNotLoggedInToPerformAction("The given user with client Id is not logged in..");
		}

		InvestmentPreference investmentPreference = investmentPreferenceDao.getInvestmentPreference(clientId);

		if (investmentPreference != null) {
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

	@Override
	public InvestmentPreference addInvestmentPreference(InvestmentPreference investmentPreference)
			throws InvestmentPreferenceAlreadyExists, UserNotLoggedInToPerformAction {
		if (!clientService.isUserLoggedIn(investmentPreference.getClientId())) {
			throw new UserNotLoggedInToPerformAction("The given user with client Id is not logged in..");
		}
		if (isValidInvestmentPreference(investmentPreference)) {
			throw new InvestmentPreferenceAlreadyExists(
					"The given investment preference with the client Id already exists :"
							+ investmentPreference.getClientId());
		}

		try {
			InvestmentPreference insertedInvestmentPreference = investmentPreferenceDao
					.addInvestmentPreference(investmentPreference);

			return insertedInvestmentPreference;

		}catch(DuplicateKeyException e) {
			throw new InvestmentPreferenceAlreadyExists();
		}catch(Exception e) {
			throw new RuntimeException("Error");
		}
		
		
	}

	@Override
	public InvestmentPreference updateInvestmentPreference(InvestmentPreference updatedInvestmentPreference)
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction {

		if (!clientService.isUserLoggedIn(updatedInvestmentPreference.getClientId())) {
			throw new UserNotLoggedInToPerformAction("The given user with client Id is not logged in..");
		}

		InvestmentPreference investmentPreference = this.investmentPreferenceDao
				.updateInvestmentPreference(updatedInvestmentPreference);

		return investmentPreference;

	}

	@Override
	public InvestmentPreference removeInvestmentPreference(String clientId)
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction {

		if (!clientService.isUserLoggedIn(clientId)) {
			throw new UserNotLoggedInToPerformAction("The given user with client Id is not logged in..");
		}

		InvestmentPreference investmentPreference = this.investmentPreferenceDao.removeInvestmentPreference(clientId);

		return investmentPreference;

	}
}
