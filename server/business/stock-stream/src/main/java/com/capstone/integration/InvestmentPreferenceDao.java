package com.capstone.integration;

import com.capstone.models.InvestmentPreference;

public interface InvestmentPreferenceDao {

	public InvestmentPreference getInvestmentPreference(String clientId);
	
	public InvestmentPreference addInvestmentPreference(InvestmentPreference investmentPreference);
	
	public InvestmentPreference updateInvestmentPreference(InvestmentPreference investmentPreference);
	
	public InvestmentPreference removeInvestmentPreference(String clientId);
}
