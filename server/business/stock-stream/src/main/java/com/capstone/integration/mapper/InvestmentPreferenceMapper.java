package com.capstone.integration.mapper;

import com.capstone.models.InvestmentPreference;


public interface InvestmentPreferenceMapper {

	InvestmentPreference getInvestmentPreference(String clientId);

	int addInvestmentPreference(InvestmentPreference investmentPreference);

	int updateInvestmentPreference(InvestmentPreference investmentPreference);

	int removeInvestmentPreference(String clientId);

}
