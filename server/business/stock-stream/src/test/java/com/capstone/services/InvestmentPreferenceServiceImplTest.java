package com.capstone.services;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.exceptions.InvestmentPreferenceAlreadyExists;
import com.capstone.exceptions.InvestmentPreferenceWithClientIdNotFound;
import com.capstone.exceptions.RoboAdvisorMandatoryException;
import com.capstone.exceptions.UserNotLoggedInToPerformAction;
import com.capstone.models.Client;
import com.capstone.models.IncomeCategory;
import com.capstone.models.InvestmentPreference;
import com.capstone.models.InvestmentPurpose;
import com.capstone.models.InvestmentYear;
import com.capstone.models.ProfileStatus;
import com.capstone.models.RiskTolerance;
import com.capstone.services.ClientService;
import com.capstone.services.FmtsService;
import com.capstone.services.InvestmentPreferenceService;

@SpringBootTest
@Transactional
class InvestmentPreferenceServiceImplTest {

	@Autowired
	InvestmentPreferenceService investmentPreferenceService;

	private final String PREFERENCE_TABLE = "investment_preferences";


	@BeforeEach
	void setup()
			throws InvestmentPreferenceAlreadyExists, RoboAdvisorMandatoryException, UserNotLoggedInToPerformAction {

		ClientService clientService = new ClientService(new FmtsService());

		clientService.registerClient(new Client("test@example.com", "password123", "John Doe", Date.valueOf("1990-01-01") , "IN",
				"PAN", "ID123", "ID123", "COMPLETE", "C001"));

		clientService.loginClient("test@example.com", "password123");

		clientService.registerClient(new Client("test1@example.com", "password1234", "John Doe", Date.valueOf("1990-01-01"), "IN",
				"123456", "PAN", "ID123", "COMPLETE", "C002"));

		clientService.loginClient("test1@example.com", "password1234");

		clientService.registerClient(new Client("test2@example.com", "password12345", "John Doe",Date.valueOf("1990-01-01"), "IN",
				"123456", "PAN", "ID123", "COMPLETE", "C003"));

		clientService.loginClient("test2@example.com", "password12345");

//		investmentPreferenceService.setClientService(clientService);

	}

	@Test
	void addInvestmentPreferenceToSucceed()
			throws InvestmentPreferenceAlreadyExists, RoboAdvisorMandatoryException, UserNotLoggedInToPerformAction {

		InvestmentPreference investmentPreference = this.investmentPreferenceService
				.addInvestmentPreference(new InvestmentPreference("C003", InvestmentPurpose.BUSINESS_INVESTMENT,
						InvestmentPurpose.BUSINESS_INVESTMENT.getDescription(), RiskTolerance.CONSERVATIVE,
						IncomeCategory.ABOVE_80000, InvestmentYear.SEVEN_TO_TEN, true));

		assertEquals(investmentPreference,
				new InvestmentPreference("C003", InvestmentPurpose.BUSINESS_INVESTMENT,
						InvestmentPurpose.BUSINESS_INVESTMENT.getDescription(), RiskTolerance.CONSERVATIVE,
						IncomeCategory.ABOVE_80000, InvestmentYear.SEVEN_TO_TEN, true));

	}

	@Test
	void addInvestmentPreferenceToThrowInvestmentPreferencesAlreadyExistsException()
			throws InvestmentPreferenceAlreadyExists, RoboAdvisorMandatoryException {

		assertThrows(InvestmentPreferenceAlreadyExists.class, () -> {
			this.investmentPreferenceService.addInvestmentPreference(new InvestmentPreference("C001",
					InvestmentPurpose.BUSINESS_INVESTMENT, InvestmentPurpose.BUSINESS_INVESTMENT.getDescription(),
					RiskTolerance.CONSERVATIVE, IncomeCategory.ABOVE_80000, InvestmentYear.SEVEN_TO_TEN, true));
		});
	}

	@Test
	void addInvestmentPreferenceToThrowRoboAdvisorMandatoryException()
			throws InvestmentPreferenceAlreadyExists, RoboAdvisorMandatoryException {

		assertThrows(RoboAdvisorMandatoryException.class, () -> {
			this.investmentPreferenceService.addInvestmentPreference(new InvestmentPreference("C001",
					InvestmentPurpose.BUSINESS_INVESTMENT, InvestmentPurpose.BUSINESS_INVESTMENT.getDescription(),
					RiskTolerance.CONSERVATIVE, IncomeCategory.ABOVE_80000, InvestmentYear.SEVEN_TO_TEN, false));
		});
	}

	@Test
	void addInvestmentPreferenceToThrowUserNotLoggedInToPerformAction()
			throws InvestmentPreferenceAlreadyExists, RoboAdvisorMandatoryException {

		assertThrows(UserNotLoggedInToPerformAction.class, () -> {
			this.investmentPreferenceService.addInvestmentPreference(new InvestmentPreference("C004",
					InvestmentPurpose.BUSINESS_INVESTMENT, InvestmentPurpose.BUSINESS_INVESTMENT.getDescription(),
					RiskTolerance.CONSERVATIVE, IncomeCategory.ABOVE_80000, InvestmentYear.SEVEN_TO_TEN, true));
		});
	}

	@Test
	void updateInvestmentPreferenceToSucceed()
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction {
		InvestmentPreference investmentPreference = this.investmentPreferenceService.getInvestmentPreference("C001");

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
		InvestmentPreference investmentPreference = new InvestmentPreference("C003", InvestmentPurpose.COLLEGE_FUND,
				InvestmentPurpose.COLLEGE_FUND.getDescription(), RiskTolerance.AVERAGE,
				IncomeCategory.FROM_60000_TO_80000, InvestmentYear.ZERO_TO_FIVE, true);

		investmentPreference.setIncomeCategory(IncomeCategory.FROM_40000_TO_60000);
		investmentPreference.setInvestmentYear(InvestmentYear.TEN_TO_FIFTEEN);

		assertThrows(InvestmentPreferenceWithClientIdNotFound.class, () -> {
			this.investmentPreferenceService.updateInvestmentPreference(investmentPreference);
		});

	}

	@Test
	void updateInvestmentPreferenceToThrowUserNotLoggedInToPerformAction()
			throws InvestmentPreferenceWithClientIdNotFound, RoboAdvisorMandatoryException {
		InvestmentPreference investmentPreference = new InvestmentPreference("C004", InvestmentPurpose.COLLEGE_FUND,
				InvestmentPurpose.COLLEGE_FUND.getDescription(), RiskTolerance.AVERAGE,
				IncomeCategory.FROM_60000_TO_80000, InvestmentYear.ZERO_TO_FIVE, true);

		investmentPreference.setIncomeCategory(IncomeCategory.FROM_40000_TO_60000);
		investmentPreference.setInvestmentYear(InvestmentYear.TEN_TO_FIFTEEN);

		assertThrows(UserNotLoggedInToPerformAction.class, () -> {
			this.investmentPreferenceService.updateInvestmentPreference(investmentPreference);
		});

	}

	@Test
	void getInvestmentPreferenceToSucceed()
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction {
		InvestmentPreference investmentPreference = this.investmentPreferenceService.getInvestmentPreference("C001");

		assertEquals(investmentPreference.getClientId(), "C001");
	}

	@Test
	void getInvestmentPreferenceToThrowInvestmentPreferenceWithClientIdNotFound() {
		assertThrows(InvestmentPreferenceWithClientIdNotFound.class, () -> {
			this.investmentPreferenceService.getInvestmentPreference("C003");
		});
	}

	@Test
	void getInvestmentPreferenceToThrowUserNotLoggedInToPerformAction() {
		assertThrows(UserNotLoggedInToPerformAction.class, () -> {
			this.investmentPreferenceService.getInvestmentPreference("C004");
		});
	}

	@Test
	void removeInvestmentPreferenceToSucceed()
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction {
		InvestmentPreference investmentPreference = this.investmentPreferenceService.removeInvestmentPreference("C001");

		assertEquals(investmentPreference.getClientId(), "C001");

	}

	@Test
	void removeInvestmentPreferenceToThrowInvestmentPreferenceWithClientIdNotFound()
			throws UserNotLoggedInToPerformAction, InvestmentPreferenceWithClientIdNotFound,
			RoboAdvisorMandatoryException {

		assertThrows(InvestmentPreferenceWithClientIdNotFound.class, () -> {
			this.investmentPreferenceService.removeInvestmentPreference("C003");
		});

	}

	@Test
	void removeInvestmentPreferenceToThrowUserNotLoggedInToPerformAction() throws UserNotLoggedInToPerformAction,
			InvestmentPreferenceWithClientIdNotFound, RoboAdvisorMandatoryException {

		assertThrows(UserNotLoggedInToPerformAction.class, () -> {
			this.investmentPreferenceService.removeInvestmentPreference("C004");
		});

	}

}
