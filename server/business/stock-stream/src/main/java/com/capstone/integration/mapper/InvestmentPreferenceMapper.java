package com.capstone.integration.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.capstone.models.InvestmentPreference;

@Mapper
public interface InvestmentPreferenceMapper {

	InvestmentPreference getInvestmentPreference(String clientId);

	int addInvestmentPreference(InvestmentPreference investmentPreference);

	int updateInvestmentPreference(InvestmentPreference investmentPreference);

	int removeInvestmentPreference(String clientId);

}
