package com.capstone.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.capstone.exceptions.DatabaseException;
import com.capstone.models.IncomeCategory;
import com.capstone.models.InvestmentPreference;
import com.capstone.models.InvestmentPurpose;
import com.capstone.models.InvestmentYear;
import com.capstone.models.RiskTolerance;

class InvestmentPreferenceDoaImplTest {

	private final String PREFERENCE_TABLE = "investment_preferences";

	static PoolableDataSource dataSource;
	InvestmentPreferenceDoa dao;
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

	@BeforeEach
	void setUp() throws Exception {
		dao = new InvestmentPreferenceDaoImpl(dataSource);
		transactionManager = new TransactionManager(dataSource);
		transactionManager.startTransaction();
	}

	@AfterEach
	void tearDown() throws Exception {
		transactionManager.rollbackTransaction();
	}

	@Test
	void getInvestmentPreferenceToSucceed() {
		InvestmentPreference investmentPreference = dao.getInvestmentPreference("abcabcab");
		assertNotNull(investmentPreference);
		assertEquals(investmentPreference, investmentPreference1);
	}

	@Test
	void getInvestmentPreferenceToThrowDatabaseException() {
		assertThrows(DatabaseException.class, () -> {
			dao.getInvestmentPreference("dbcdbcdb");
		});
	}

	@Test
	void getInvestmentPreferencesToThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> {
			dao.getInvestmentPreference("");
		});
	}

	@Test
	void insertInvestmentPreferenceToSucceed() throws SQLException {
		InvestmentPreference newInvestmentPreference = new InvestmentPreference("abcddcba",
				InvestmentPurpose.COLLEGE_FUND, InvestmentPurpose.COLLEGE_FUND.getDescription(),
				RiskTolerance.CONSERVATIVE, IncomeCategory.BELOW_20000, InvestmentYear.ZERO_TO_FIVE, true);

		InvestmentPreference insertedInvestmentPreference = dao.addInvestmentPreference(newInvestmentPreference);

		assertEquals(1, DbTestUtils.countRowsInTableWhere(dataSource.getConnection(), PREFERENCE_TABLE,
				"client_id='abcddcba'"));
		assertEquals(newInvestmentPreference, insertedInvestmentPreference);

	}

	@Test
	void insertInvestmentPreferenceToThrowDatabaseExceptionParentKeyNotPresent() throws SQLException {
		InvestmentPreference newInvestmentPreference = new InvestmentPreference("abcabcda",
				InvestmentPurpose.COLLEGE_FUND, InvestmentPurpose.COLLEGE_FUND.getDescription(),
				RiskTolerance.CONSERVATIVE, IncomeCategory.BELOW_20000, InvestmentYear.ZERO_TO_FIVE, true);

		assertThrows(DatabaseException.class, () -> {
			dao.addInvestmentPreference(newInvestmentPreference);
		});

	}

	@Test
	void insertInvestmentPreferenceToThrowDatabaseExceptionAlreadyExistsWithPrimaryKey() throws SQLException {
		InvestmentPreference newInvestmentPreference = new InvestmentPreference("abcabcab",
				InvestmentPurpose.COLLEGE_FUND, InvestmentPurpose.COLLEGE_FUND.getDescription(),
				RiskTolerance.CONSERVATIVE, IncomeCategory.BELOW_20000, InvestmentYear.ZERO_TO_FIVE, true);

		assertThrows(DatabaseException.class, () -> {
			dao.addInvestmentPreference(newInvestmentPreference);
		});

	}

	@Test
	void insertInvestmentPreferenceToThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> {
			dao.addInvestmentPreference(null);
		});
	}

	@Test
	void updateInvestmentPreferenceToSucceed() throws SQLException {
		InvestmentPreference newInvestmentPreference = investmentPreference1;
		newInvestmentPreference.setIncomeCategory(IncomeCategory.ABOVE_80000);

		InvestmentPreference insertedInvestmentPreference = dao.updateInvestmentPreference(newInvestmentPreference);

		assertEquals(1, DbTestUtils.countRowsInTableWhere(dataSource.getConnection(), PREFERENCE_TABLE,
				"client_id='abcabcab' AND income_category='Above $80,000'"));
		assertEquals(newInvestmentPreference, insertedInvestmentPreference);

	}

	@Test
	void updateInvestmentPreferenceToThrowDatabaseException() throws SQLException {
		InvestmentPreference newInvestmentPreference = new InvestmentPreference("abcabcda",
				InvestmentPurpose.COLLEGE_FUND, InvestmentPurpose.COLLEGE_FUND.getDescription(),
				RiskTolerance.CONSERVATIVE, IncomeCategory.BELOW_20000, InvestmentYear.ZERO_TO_FIVE, true);

		assertThrows(DatabaseException.class, () -> {
			dao.updateInvestmentPreference(newInvestmentPreference);
		});

	}

	@Test
	void updateInvestmentPreferenceToThrowIllegalArgumentException() throws SQLException {

		assertThrows(IllegalArgumentException.class, () -> {
			dao.updateInvestmentPreference(null);
		});

	}

	@Test
	void removeInvestmentPreferenceToSucceed() throws SQLException {
		String clientIdToBeDeleted = "abcabcab";

		InvestmentPreference deletedInvestmentPreference = dao.removeInvestmentPreference(clientIdToBeDeleted);

		assertEquals(0, DbTestUtils.countRowsInTableWhere(dataSource.getConnection(), PREFERENCE_TABLE,
				"client_id='abcabcab'"));
		assertEquals(clientIdToBeDeleted, deletedInvestmentPreference.getClientId());
	}
	
	@Test
	void removeInvestmentPreferenceToThrowDatabaseException() throws SQLException {

		String clientIdToBeDeleted = "abcabczz";
		
		assertThrows(DatabaseException.class, () -> {
			dao.removeInvestmentPreference(clientIdToBeDeleted);
		});
	}

	@Test
	void removeInvestmentPreferenceToThrowIllegalArgumentException() throws SQLException {

		assertThrows(IllegalArgumentException.class, () -> {
			dao.removeInvestmentPreference(null);
		});
	}

}
