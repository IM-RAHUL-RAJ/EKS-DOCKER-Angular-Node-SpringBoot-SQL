package com.capstone.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.capstone.exceptions.InvestmentPreferenceAlreadyExists;
import com.capstone.exceptions.InvestmentPreferenceWithClientIdNotFound;
import com.capstone.exceptions.RoboAdvisorMandatoryException;
import com.capstone.exceptions.UserNotLoggedInToPerformAction;
import com.capstone.integration.InvestmentPreferenceDao;
import com.capstone.integration.InvestmentPreferenceDaoImpl;
import com.capstone.integration.PoolableDataSource;
import com.capstone.integration.TransactionManager;
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

class InvestmentPreferenceServiceTest {

	InvestmentPreferenceService investmentPreferenceService;

	
	private final String PREFERENCE_TABLE = "investment_preferences";

	static PoolableDataSource dataSource;
	InvestmentPreferenceDao dao;
	TransactionManager transactionManager;
	private InvestmentPreference investmentPreference1 = new InvestmentPreference("abcabcab",
			InvestmentPurpose.COLLEGE_FUND, InvestmentPurpose.COLLEGE_FUND.getDescription(), RiskTolerance.CONSERVATIVE,
			IncomeCategory.BELOW_20000, InvestmentYear.ZERO_TO_FIVE, true);

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		dataSource = new PoolableDataSource();

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		dataSource.shutdown();
	}

	@AfterEach
	void tearDown() throws Exception {
		transactionManager.rollbackTransaction();
	}
	
	
	@BeforeEach
	void setup()
			throws InvestmentPreferenceAlreadyExists, RoboAdvisorMandatoryException, UserNotLoggedInToPerformAction {

		ClientService clientService = new ClientService(new FmtsService());

		clientService.registerClient(new Client("test@example.com", "password123", "John Doe", "1990-01-01", "IN",
				 "PAN", "ID123", "ID123",  ProfileStatus.COMPLETE,"abdcabdc"));
		
		clientService.loginClient("test@example.com", "password123");

		clientService.registerClient(new Client("test1@example.com", "password1234", "John Doe", "1990-01-01", "IN", "123456", "PAN", "ID123",
				 ProfileStatus.COMPLETE,"abcabcab"));

		clientService.loginClient("test1@example.com", "password1234");
		
		clientService.registerClient(new Client("test2@example.com", "password12345", "John Doe", "1990-01-01", "IN", "123456", "PAN", "ID123",
				 ProfileStatus.COMPLETE,"abcabcjk"));

		clientService.loginClient("test2@example.com", "password1235");
		
		clientService.registerClient(new Client("test3@example.com", "password12346", "John Doe", "1990-01-01", "IN", "123456", "PAN", "ID123",
				 ProfileStatus.COMPLETE,"aaaabbbb"));

		clientService.loginClient("test3@example.com", "password12346");
		
		clientService.registerClient(new Client("test4@example.com", "password12346", "John Doe", "1990-01-01", "IN", "123456", "ID123",
				"aaaabbbb"));

		clientService.loginClient("test4@example.com", "password12346");
		
		dao = new InvestmentPreferenceDaoImpl(dataSource);
		transactionManager = new TransactionManager(dataSource);
		transactionManager.startTransaction();
		

		investmentPreferenceService = new InvestmentPreferenceService(clientService,dao);

	}

	@Test
	void addInvestmentPreferenceToSucceed()
			throws InvestmentPreferenceAlreadyExists, RoboAdvisorMandatoryException, UserNotLoggedInToPerformAction {
		
		InvestmentPreference investmentPreference = this.investmentPreferenceService
				.addInvestmentPreference(new InvestmentPreference("abcddcba", InvestmentPurpose.BUSINESS_INVESTMENT,
						InvestmentPurpose.BUSINESS_INVESTMENT.getDescription(), RiskTolerance.CONSERVATIVE,
						IncomeCategory.ABOVE_80000, InvestmentYear.SEVEN_TO_TEN, true));

		assertEquals(investmentPreference,
				new InvestmentPreference("abcddcba", InvestmentPurpose.BUSINESS_INVESTMENT,
						InvestmentPurpose.BUSINESS_INVESTMENT.getDescription(), RiskTolerance.CONSERVATIVE,
						IncomeCategory.ABOVE_80000, InvestmentYear.SEVEN_TO_TEN, true));

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
	void addInvestmentPreferenceToThrowUserNotLoggedInToPerformAction()
			throws InvestmentPreferenceAlreadyExists, RoboAdvisorMandatoryException {

		assertThrows(UserNotLoggedInToPerformAction.class, () -> {
			this.investmentPreferenceService.addInvestmentPreference(new InvestmentPreference("abcabcll",
					InvestmentPurpose.BUSINESS_INVESTMENT, InvestmentPurpose.BUSINESS_INVESTMENT.getDescription(),
					RiskTolerance.CONSERVATIVE, IncomeCategory.ABOVE_80000, InvestmentYear.SEVEN_TO_TEN, true));
		});
	}

	@Test
	void updateInvestmentPreferenceToSucceed()
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction {
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
		InvestmentPreference investmentPreference = new InvestmentPreference("aaaabbbb", InvestmentPurpose.COLLEGE_FUND,
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
		InvestmentPreference investmentPreference = new InvestmentPreference("adfadfaa", InvestmentPurpose.COLLEGE_FUND,
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
		InvestmentPreference investmentPreference = this.investmentPreferenceService
				.getInvestmentPreference("abcabcab");

		assertEquals(investmentPreference.getClientId(), "abcabcab");
	}

	@Test
	void getInvestmentPreferenceToThrowInvestmentPreferenceWithClientIdNotFound() {
		assertThrows(InvestmentPreferenceWithClientIdNotFound.class, () -> {
			this.investmentPreferenceService.getInvestmentPreference("aaaabbbb");
		});
	}
	
	@Test
	void getInvestmentPreferenceToThrowUserNotLoggedInToPerformAction() {
		assertThrows(UserNotLoggedInToPerformAction.class, () -> {
			this.investmentPreferenceService.getInvestmentPreference("aaaabvvb");
		});
	}

	@Test
	void removeInvestmentPreferenceToSucceed()
			throws InvestmentPreferenceWithClientIdNotFound, UserNotLoggedInToPerformAction {
		InvestmentPreference investmentPreference = this.investmentPreferenceService
				.removeInvestmentPreference("abcabcab");

		assertEquals(investmentPreference.getClientId(), "abcabcab");

	}

	@Test
	void removeInvestmentPreferenceToThrowInvestmentPreferenceWithClientIdNotFound()
			throws UserNotLoggedInToPerformAction,InvestmentPreferenceWithClientIdNotFound, RoboAdvisorMandatoryException {

		assertThrows(InvestmentPreferenceWithClientIdNotFound.class, () -> {
			this.investmentPreferenceService.removeInvestmentPreference("aaaabbbb");
		});

	}
	
	@Test
	void removeInvestmentPreferenceToThrowUserNotLoggedInToPerformAction()
			throws UserNotLoggedInToPerformAction,InvestmentPreferenceWithClientIdNotFound, RoboAdvisorMandatoryException {

		assertThrows(UserNotLoggedInToPerformAction.class, () -> {
			this.investmentPreferenceService.removeInvestmentPreference("aaaabbkk");
		});

	}

}
