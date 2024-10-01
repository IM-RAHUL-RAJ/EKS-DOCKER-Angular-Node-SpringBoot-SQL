package com.capstone.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.capstone.exceptions.ClientWithIdNotFoundException;
import com.capstone.exceptions.DatabaseException;
import com.capstone.exceptions.InvestmentPreferenceAlreadyExists;
import com.capstone.exceptions.InvestmentPreferenceWithClientIdNotFound;
import com.capstone.models.IncomeCategory;
import com.capstone.models.InvestmentPreference;
import com.capstone.models.InvestmentPurpose;
import com.capstone.models.InvestmentYear;
import com.capstone.models.RiskTolerance;

class InvestmentPreferenceDoaImplTest {

	private final String PREFERENCE_TABLE = "investment_preferences";

	static PoolableDataSource dataSource;
	InvestmentPreferenceDao dao;
	TransactionManager transactionManager;
	private InvestmentPreference investmentPreference1 = new InvestmentPreference("C001",
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
		InvestmentPreference investmentPreference = dao.getInvestmentPreference("C001");
		assertNotNull(investmentPreference);
		assertEquals(investmentPreference, investmentPreference1);
	}

	@Test
	void getInvestmentPreferenceToThrowInvestmentPreferenceWithClientIdNotFound() {
		assertThrows(InvestmentPreferenceWithClientIdNotFound.class, () -> {
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
	void getInvestmentPreferenceToThrowDatabaseExceptionConnectionError() throws SQLException
	{
		PoolableDataSource dataSource = mock(PoolableDataSource.class);
		when(dataSource.getConnection()).thenThrow(SQLException.class);
		InvestmentPreferenceDao dao2 = new InvestmentPreferenceDaoImpl(dataSource);
		assertThrows(DatabaseException.class,()->{
			dao2.getInvestmentPreference("abcabcab");
		});
	}
	
	@Test
	void insertInvestmentPreferenceToSucceed() throws SQLException {
		InvestmentPreference newInvestmentPreference = new InvestmentPreference("C003",
				InvestmentPurpose.COLLEGE_FUND, InvestmentPurpose.COLLEGE_FUND.getDescription(),
				RiskTolerance.CONSERVATIVE, IncomeCategory.BELOW_20000, InvestmentYear.ZERO_TO_FIVE, true);

		InvestmentPreference insertedInvestmentPreference = dao.addInvestmentPreference(newInvestmentPreference);

		assertEquals(1, DbTestUtils.countRowsInTableWhere(dataSource.getConnection(), PREFERENCE_TABLE,
				"clientId='C003'"));
		assertEquals(newInvestmentPreference, insertedInvestmentPreference);

	}

	@Test
	void insertInvestmentPreferenceToThrowClientWithIdNotFoundException() throws SQLException {
		InvestmentPreference newInvestmentPreference = new InvestmentPreference("abcabcda",
				InvestmentPurpose.COLLEGE_FUND, InvestmentPurpose.COLLEGE_FUND.getDescription(),
				RiskTolerance.CONSERVATIVE, IncomeCategory.BELOW_20000, InvestmentYear.ZERO_TO_FIVE, true);

		assertThrows(ClientWithIdNotFoundException.class, () -> {
			dao.addInvestmentPreference(newInvestmentPreference);
		});

	}

	@Test
	void insertInvestmentPreferenceToThrowInvestmentPreferenceAlreadyExists() throws SQLException {
		InvestmentPreference newInvestmentPreference = new InvestmentPreference("C002",
				InvestmentPurpose.COLLEGE_FUND, InvestmentPurpose.COLLEGE_FUND.getDescription(),
				RiskTolerance.CONSERVATIVE, IncomeCategory.BELOW_20000, InvestmentYear.ZERO_TO_FIVE, true);

		assertThrows(InvestmentPreferenceAlreadyExists.class, () -> {
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
	void insertInvestmentPreferenceToThrowDatabaseExceptionConnectionError() throws SQLException
	{
		PoolableDataSource dataSource = mock(PoolableDataSource.class);
		when(dataSource.getConnection()).thenThrow(SQLException.class);
		InvestmentPreferenceDao dao2 = new InvestmentPreferenceDaoImpl(dataSource);
		assertThrows(DatabaseException.class,()->{
			dao2.addInvestmentPreference(investmentPreference1);
		});
	}

	@Test
	void updateInvestmentPreferenceToSucceed() throws SQLException {
		InvestmentPreference newInvestmentPreference = investmentPreference1;
		newInvestmentPreference.setIncomeCategory(IncomeCategory.ABOVE_80000);

		InvestmentPreference insertedInvestmentPreference = dao.updateInvestmentPreference(newInvestmentPreference);

		assertEquals(1, DbTestUtils.countRowsInTableWhere(dataSource.getConnection(), PREFERENCE_TABLE,
				"clientId='C001' AND incomeCategory='Above $80,000'"));
		assertEquals(newInvestmentPreference, insertedInvestmentPreference);

	}

	@Test
	void updateInvestmentPreferenceToThrowInvestmentPreferenceWithClientIdNotFound() throws SQLException {
		InvestmentPreference newInvestmentPreference = new InvestmentPreference("abcabcda",
				InvestmentPurpose.COLLEGE_FUND, InvestmentPurpose.COLLEGE_FUND.getDescription(),
				RiskTolerance.CONSERVATIVE, IncomeCategory.BELOW_20000, InvestmentYear.ZERO_TO_FIVE, true);

		assertThrows(InvestmentPreferenceWithClientIdNotFound.class, () -> {
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
	void updateInvestmentPreferenceToThrowDatabaseExceptionConnectionError() throws SQLException
	{
		PoolableDataSource dataSource = mock(PoolableDataSource.class);
		when(dataSource.getConnection()).thenThrow(SQLException.class);
		InvestmentPreferenceDao dao2 = new InvestmentPreferenceDaoImpl(dataSource);
		assertThrows(DatabaseException.class,()->{
			dao2.updateInvestmentPreference(investmentPreference1);
		});
	}
	
	@Test
	void removeInvestmentPreferenceToSucceed() throws SQLException {
		String clientIdToBeDeleted = "C001";

		InvestmentPreference deletedInvestmentPreference = dao.removeInvestmentPreference(clientIdToBeDeleted);

		assertEquals(0, DbTestUtils.countRowsInTableWhere(dataSource.getConnection(), PREFERENCE_TABLE,
				"clientId='C001'"));
		assertEquals(clientIdToBeDeleted, deletedInvestmentPreference.getClientId());
	}
	
	@Test
	void removeInvestmentPreferenceToThrowInvestmentPreferenceWithClientIdNotFound() throws SQLException {

		String clientIdToBeDeleted = "abcabczz";
		
		assertThrows(InvestmentPreferenceWithClientIdNotFound.class, () -> {
			dao.removeInvestmentPreference(clientIdToBeDeleted);
		});
	}

	@Test
	void removeInvestmentPreferenceToThrowIllegalArgumentException() throws SQLException {

		assertThrows(IllegalArgumentException.class, () -> {
			dao.removeInvestmentPreference(null);
		});
	}
	
	@Test
	void removeInvestmentPreferenceToThrowDatabaseExceptionConnectionError() throws SQLException
	{
		PoolableDataSource dataSource = mock(PoolableDataSource.class);
		when(dataSource.getConnection()).thenThrow(SQLException.class);
		InvestmentPreferenceDao dao2 = new InvestmentPreferenceDaoImpl(dataSource);
		assertThrows(DatabaseException.class,()->{
			dao2.removeInvestmentPreference("abcabcab");
		});
	}

}
