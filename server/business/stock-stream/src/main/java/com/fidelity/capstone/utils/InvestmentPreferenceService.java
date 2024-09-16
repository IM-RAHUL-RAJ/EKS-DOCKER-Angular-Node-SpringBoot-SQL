package com.fidelity.capstone.utils;

import java.util.ArrayList;
import java.util.List;

import com.fidelity.capstone.exceptions.InvestmentPreferenceAlreadyExists;
import com.fidelity.capstone.exceptions.InvestmentPreferenceWithClientIdNotFound;
import com.fidelity.capstone.exceptions.RoboAdvisorMandatoryException;
import com.fidelity.capstone.stock_stream.IncomeCategory;
import com.fidelity.capstone.stock_stream.InvestmentPreference;
import com.fidelity.capstone.stock_stream.InvestmentPurpose;
import com.fidelity.capstone.stock_stream.InvestmentYear;
import com.fidelity.capstone.stock_stream.RiskTolerance;

public class InvestmentPreferenceService {

	private List<InvestmentPreference> investmentPreferences = new ArrayList<InvestmentPreference>();

	public InvestmentPreferenceService(){
		
	}

	public int getLength() {
		return this.investmentPreferences.size();
	}
	
	public InvestmentPreference getInvestmentPreference(String clientId)
			throws InvestmentPreferenceWithClientIdNotFound {

		for (InvestmentPreference investmentPreference : this.investmentPreferences) {
			if (investmentPreference.getClientId().equals(clientId)) {
				return investmentPreference;
			}
		}

		throw new InvestmentPreferenceWithClientIdNotFound(
				"The investment preference with the given client id is not found..");

	}

	public boolean isValidInvestmentPreference(InvestmentPreference investmentPreference) {
		for (InvestmentPreference ip : this.investmentPreferences) {
			if (ip.equals(investmentPreference)) {
				return true;
			}
		}
		return false;
	}

	public InvestmentPreference addInvestmentPreference(InvestmentPreference investmentPreference)
			throws InvestmentPreferenceAlreadyExists {

		if (isValidInvestmentPreference(investmentPreference)) {
			throw new InvestmentPreferenceAlreadyExists(
					"The given investment preference with the client Id already exists :"
							+ investmentPreference.getClientId());
		}

		this.investmentPreferences.add(investmentPreference);

		System.out.println("Add Investment preference succeeded :" + investmentPreference.getClientId());

		return investmentPreference;

	}

	public InvestmentPreference updateInvestmentPreference(InvestmentPreference updatedInvestmentPreference)
			throws InvestmentPreferenceWithClientIdNotFound {

		InvestmentPreference investmentPreference = this
				.getInvestmentPreference(updatedInvestmentPreference.getClientId());

		investmentPreference.setInvestmentPurpose(updatedInvestmentPreference.getInvestmentPurpose());

		investmentPreference
				.setInvestmentPurposeDescription(updatedInvestmentPreference.getInvestmentPurposeDescription());

		investmentPreference.setIncomeCategory(updatedInvestmentPreference.getIncomeCategory());

		investmentPreference.setRiskTolerance(updatedInvestmentPreference.getRiskTolerance());

		investmentPreference.setInvestmentYear(updatedInvestmentPreference.getInvestmentYear());

		System.out.println("Update Investment preference succeeded :" + investmentPreference.getClientId());

		return investmentPreference;

	}

	public InvestmentPreference removeInvestmentPreference(String clientId)
			throws InvestmentPreferenceWithClientIdNotFound {

		InvestmentPreference investmentPreference = this.getInvestmentPreference(clientId);
		
		this.investmentPreferences.remove(investmentPreference);

		System.out.println("Remove Investment preference succeeded :" + investmentPreference.getClientId());
		
		return investmentPreference;

	}
}
