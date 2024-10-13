package com.capstone.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.exceptions.ClientWithIdNotFoundException;
import com.capstone.exceptions.DatabaseException;
import com.capstone.exceptions.InvestmentPreferenceAlreadyExists;
import com.capstone.exceptions.InvestmentPreferenceWithClientIdNotFound;
import com.capstone.models.IncomeCategory;
import com.capstone.models.InvestmentPreference;
import com.capstone.models.InvestmentPurpose;
import com.capstone.models.InvestmentYear;
import com.capstone.models.RiskTolerance;



@SpringBootTest
@Transactional
class InvestmentPreferenceDoaImplTest {

	private final String PREFERENCE_TABLE = "investment_preferences";
	
	@Autowired
	InvestmentPreferenceDao dao;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private InvestmentPreference investmentPreference1 = new InvestmentPreference("C001",
			InvestmentPurpose.COLLEGE_FUND, InvestmentPurpose.COLLEGE_FUND.getDescription(), RiskTolerance.CONSERVATIVE,
			IncomeCategory.BELOW_20000, InvestmentYear.ZERO_TO_FIVE, true);

	@Test
	void getInvestmentPreferenceToSucceed() {
		InvestmentPreference investmentPreference = dao.getInvestmentPreference("C001");
		assertNotNull(investmentPreference.getClientId());
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
	void insertInvestmentPreferenceToSucceed() throws SQLException {
		InvestmentPreference newInvestmentPreference = new InvestmentPreference("C003",
				InvestmentPurpose.COLLEGE_FUND, InvestmentPurpose.COLLEGE_FUND.getDescription(),
				RiskTolerance.CONSERVATIVE, IncomeCategory.BELOW_20000, InvestmentYear.ZERO_TO_FIVE, true);

		InvestmentPreference insertedInvestmentPreference = dao.addInvestmentPreference(newInvestmentPreference);

		assertEquals(newInvestmentPreference, insertedInvestmentPreference);
		assertEquals(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"investment_preferences","client_id='C003'"), 1);

	}

	@Test
	void insertInvestmentPreferenceToThrowDataIntegrityViolationException() throws SQLException {
		InvestmentPreference newInvestmentPreference = new InvestmentPreference("abcabcda",
				InvestmentPurpose.COLLEGE_FUND, InvestmentPurpose.COLLEGE_FUND.getDescription(),
				RiskTolerance.CONSERVATIVE, IncomeCategory.BELOW_20000, InvestmentYear.ZERO_TO_FIVE, true);

		assertThrows(DataIntegrityViolationException.class, () -> {
			dao.addInvestmentPreference(newInvestmentPreference);
		});

	}

	@Test
	void insertInvestmentPreferenceToThrowDuplicateKeyException() throws SQLException {
		InvestmentPreference newInvestmentPreference = new InvestmentPreference("C002",
				InvestmentPurpose.COLLEGE_FUND, InvestmentPurpose.COLLEGE_FUND.getDescription(),
				RiskTolerance.CONSERVATIVE, IncomeCategory.BELOW_20000, InvestmentYear.ZERO_TO_FIVE, true);

		assertThrows(DuplicateKeyException.class, () -> {
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

		assertEquals(newInvestmentPreference, insertedInvestmentPreference);
		assertEquals(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"investment_preferences","client_id='C001' AND income_category='ABOVE_80000'"), 1);

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
	void removeInvestmentPreferenceToSucceed() throws SQLException {
		String clientIdToBeDeleted = "C001";

		InvestmentPreference deletedInvestmentPreference = dao.removeInvestmentPreference(clientIdToBeDeleted);

		assertEquals(clientIdToBeDeleted, deletedInvestmentPreference.getClientId());
		assertEquals(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"investment_preferences","client_id='C001'"), 0);
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
	

}
