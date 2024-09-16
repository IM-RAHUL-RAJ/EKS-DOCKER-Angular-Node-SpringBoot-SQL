package com.fidelity.capstone.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.capstone.exceptions.InvestmentPreferenceAlreadyExists;
import com.fidelity.capstone.exceptions.InvestmentPreferenceWithClientIdNotFound;
import com.fidelity.capstone.exceptions.RoboAdvisorMandatoryException;
import com.fidelity.capstone.stock_stream.IncomeCategory;
import com.fidelity.capstone.stock_stream.InvestmentPreference;
import com.fidelity.capstone.stock_stream.InvestmentPurpose;
import com.fidelity.capstone.stock_stream.InvestmentYear;
import com.fidelity.capstone.stock_stream.RiskTolerance;

class InvestmentPreferenceServiceTest {

	InvestmentPreferenceService investmentPreferenceService = new InvestmentPreferenceService();

	@BeforeEach
	void setup() throws InvestmentPreferenceAlreadyExists, RoboAdvisorMandatoryException {
		this.investmentPreferenceService.addInvestmentPreference(new InvestmentPreference("abdcabdc",
				InvestmentPurpose.COLLEGE_FUND, InvestmentPurpose.COLLEGE_FUND.getDescription(), RiskTolerance.AVERAGE,
				IncomeCategory.FROM_60000_TO_80000, InvestmentYear.ZERO_TO_FIVE, true));

		this.investmentPreferenceService.addInvestmentPreference(new InvestmentPreference("abcabcab",
				InvestmentPurpose.BUSINESS_INVESTMENT, InvestmentPurpose.BUSINESS_INVESTMENT.getDescription(),
				RiskTolerance.CONSERVATIVE, IncomeCategory.ABOVE_80000, InvestmentYear.SEVEN_TO_TEN, true));
	}

	@Test
	void addInvestmentPreferenceToSucceed() throws InvestmentPreferenceAlreadyExists, RoboAdvisorMandatoryException {
		InvestmentPreference investmentPreference = this.investmentPreferenceService
				.addInvestmentPreference(new InvestmentPreference("abcabcdb", InvestmentPurpose.BUSINESS_INVESTMENT,
						InvestmentPurpose.BUSINESS_INVESTMENT.getDescription(), RiskTolerance.CONSERVATIVE,
						IncomeCategory.ABOVE_80000, InvestmentYear.SEVEN_TO_TEN, true));

		assertEquals(investmentPreference,
				new InvestmentPreference("abcabcdb", InvestmentPurpose.BUSINESS_INVESTMENT,
						InvestmentPurpose.BUSINESS_INVESTMENT.getDescription(), RiskTolerance.CONSERVATIVE,
						IncomeCategory.ABOVE_80000, InvestmentYear.SEVEN_TO_TEN, true));

		assertEquals(3, this.investmentPreferenceService.getLength());
	}

	@Test
	void addInvestmentPreferenceToThrowInvestmentPreferencesAlreadyExistsException()
			throws InvestmentPreferenceAlreadyExists, RoboAdvisorMandatoryException {

		assertThrows(InvestmentPreferenceAlreadyExists.class, () -> {
			this.investmentPreferenceService.addInvestmentPreference(new InvestmentPreference("abcabcab",
					InvestmentPurpose.BUSINESS_INVESTMENT, InvestmentPurpose.BUSINESS_INVESTMENT.getDescription(),
					RiskTolerance.CONSERVATIVE, IncomeCategory.ABOVE_80000, InvestmentYear.SEVEN_TO_TEN, true));
		});
	}

	@Test
	void addInvestmentPreferenceToThrowRoboAdvisorMandatoryException()
			throws InvestmentPreferenceAlreadyExists, RoboAdvisorMandatoryException {

		assertThrows(RoboAdvisorMandatoryException.class, () -> {
			this.investmentPreferenceService.addInvestmentPreference(new InvestmentPreference("abcabcda",
					InvestmentPurpose.BUSINESS_INVESTMENT, InvestmentPurpose.BUSINESS_INVESTMENT.getDescription(),
					RiskTolerance.CONSERVATIVE, IncomeCategory.ABOVE_80000, InvestmentYear.SEVEN_TO_TEN, false));
		});
	}

	@Test
	void updateInvestmentPreferenceToSucceed() throws InvestmentPreferenceWithClientIdNotFound {
		InvestmentPreference investmentPreference = this.investmentPreferenceService
				.getInvestmentPreference("abcabcab");

		investmentPreference.setIncomeCategory(IncomeCategory.FROM_40000_TO_60000);
		investmentPreference.setInvestmentYear(InvestmentYear.TEN_TO_FIFTEEN);

		InvestmentPreference updatedInvestmentPreference = this.investmentPreferenceService
				.updateInvestmentPreference(investmentPreference);

		assertEquals(updatedInvestmentPreference.getIncomeCategory(), investmentPreference.getIncomeCategory());
		assertEquals(updatedInvestmentPreference.getInvestmentYear(), investmentPreference.getInvestmentYear());
		assertEquals(updatedInvestmentPreference, investmentPreference);

	}

	@Test
	void updateInvestmentPreferenceToThrowInvestmentPreferenceWithClientIdNotFound()
			throws InvestmentPreferenceWithClientIdNotFound, RoboAdvisorMandatoryException {
		InvestmentPreference investmentPreference = new InvestmentPreference("abdcabac", InvestmentPurpose.COLLEGE_FUND,
				InvestmentPurpose.COLLEGE_FUND.getDescription(), RiskTolerance.AVERAGE,
				IncomeCategory.FROM_60000_TO_80000, InvestmentYear.ZERO_TO_FIVE, true);

		investmentPreference.setIncomeCategory(IncomeCategory.FROM_40000_TO_60000);
		investmentPreference.setInvestmentYear(InvestmentYear.TEN_TO_FIFTEEN);

		assertThrows(InvestmentPreferenceWithClientIdNotFound.class, () -> {
			this.investmentPreferenceService.updateInvestmentPreference(investmentPreference);
		});

	}

	void getInvestmentPreferenceToSucceed() throws InvestmentPreferenceWithClientIdNotFound {
		InvestmentPreference investmentPreference = this.investmentPreferenceService
				.getInvestmentPreference("abcabcab");

		assertEquals(investmentPreference.getClientId(), "abcabcab");
	}

	void getInvestmentPreferenceToThrowInvestmentPreferenceWithClientIdNotFound() {
		assertThrows(InvestmentPreferenceWithClientIdNotFound.class, () -> {
			this.investmentPreferenceService.getInvestmentPreference("abcabcjk");
		});
	}

	@Test
	void removeInvestmentPreferenceToSucceed() throws InvestmentPreferenceWithClientIdNotFound {
		InvestmentPreference investmentPreference = this.investmentPreferenceService
				.removeInvestmentPreference("abcabcab");

		assertEquals(investmentPreference.getClientId(), "abcabcab");

	}

	@Test
	void removeInvestmentPreferenceToThrowInvestmentPreferenceWithClientIdNotFound()
			throws InvestmentPreferenceWithClientIdNotFound, RoboAdvisorMandatoryException {

		assertThrows(InvestmentPreferenceWithClientIdNotFound.class, () -> {
			this.investmentPreferenceService.removeInvestmentPreference(null);
		});

	}

}
