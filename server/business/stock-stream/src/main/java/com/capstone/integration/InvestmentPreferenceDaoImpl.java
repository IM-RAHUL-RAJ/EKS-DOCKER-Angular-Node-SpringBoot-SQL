package com.capstone.integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.capstone.exceptions.ClientWithIdNotFoundException;
import com.capstone.exceptions.DatabaseException;
import com.capstone.exceptions.InvestmentPreferenceAlreadyExists;
import com.capstone.exceptions.InvestmentPreferenceWithClientIdNotFound;
import com.capstone.integration.mapper.InvestmentPreferenceMapper;
import com.capstone.models.IncomeCategory;
import com.capstone.models.InvestmentPreference;
import com.capstone.models.InvestmentPurpose;
import com.capstone.models.InvestmentYear;
import com.capstone.models.RiskTolerance;

@Repository("investmentPreferenceDao")
@Primary
public class InvestmentPreferenceDaoImpl implements InvestmentPreferenceDao {


	@Autowired
	InvestmentPreferenceMapper investmentPreferenceMapper;

	@Override
	public InvestmentPreference getInvestmentPreference(String clientId) {

		if (clientId == null || clientId.isBlank()) {
			throw new IllegalArgumentException("client id cannot be null or blank");
		}

		InvestmentPreference investmentPreference = investmentPreferenceMapper.getInvestmentPreference(clientId);

		if (investmentPreference == null) {
			throw new InvestmentPreferenceWithClientIdNotFound();
		}

		return investmentPreference;

	}

	@Override
	public InvestmentPreference addInvestmentPreference(InvestmentPreference investmentPreference) {

		if (investmentPreference == null) {
			throw new IllegalArgumentException("Investment preference cannot be null..");
		}

		if (investmentPreferenceMapper.addInvestmentPreference(investmentPreference) == 1) {
			InvestmentPreference newInvestmentPreference = getInvestmentPreference(investmentPreference.getClientId());
			return newInvestmentPreference;
		} else {
			throw new DatabaseException();
		}

	}

	@Override
	public InvestmentPreference updateInvestmentPreference(InvestmentPreference investmentPreference) {

		if (investmentPreference == null) {
			throw new IllegalArgumentException("investment preference cannot be null");
		}

		if (investmentPreferenceMapper.updateInvestmentPreference(investmentPreference) == 1) {
			InvestmentPreference updatedInvestmentPreference = getInvestmentPreference(
					investmentPreference.getClientId());
			return updatedInvestmentPreference;
		} else {
			throw new InvestmentPreferenceWithClientIdNotFound();
		}

	}

	@Override
	public InvestmentPreference removeInvestmentPreference(String clientId) {

		if (clientId == null || clientId.isBlank()) {
			throw new IllegalArgumentException("client id cannot be null or empty");
		}

		InvestmentPreference investmentPreference = getInvestmentPreference(clientId);

		if (investmentPreferenceMapper.removeInvestmentPreference(clientId) == 1) {
			return investmentPreference;
		} else {
			throw new DatabaseException();
		}

	}


}
