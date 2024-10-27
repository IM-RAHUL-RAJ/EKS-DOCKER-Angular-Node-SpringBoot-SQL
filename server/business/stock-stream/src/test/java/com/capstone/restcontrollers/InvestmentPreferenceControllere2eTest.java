package com.capstone.restcontrollers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import com.capstone.models.IncomeCategory;
import com.capstone.models.InvestmentPreference;
import com.capstone.models.InvestmentPurpose;
import com.capstone.models.InvestmentYear;
import com.capstone.models.RiskTolerance;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts={"classpath:init.sql", "classpath:init.sql"}, 
     executionPhase=Sql.ExecutionPhase.AFTER_TEST_METHOD)
class InvestmentPreferenceControllere2eTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private InvestmentPreference testInvestmentPreference;
	private InvestmentPreference testInvestmentPreference2;
	
	@BeforeEach
	void setUp() {
		// Initialize a test InvestmentPreference object
		testInvestmentPreference = new InvestmentPreference(
			    "C005", 
			    InvestmentPurpose.HOBBY_INTEREST, 
			    "Saving for retirement years", 
			    RiskTolerance.AVERAGE, 
			    IncomeCategory.FROM_40000_TO_60000, 
			    InvestmentYear.FIVE_TO_SEVEN, 
			    true
			);
		testInvestmentPreference2 = new InvestmentPreference(
			    "C001", 
			    InvestmentPurpose.HOBBY_INTEREST, 
			    "Saving for retirement years", 
			    RiskTolerance.AVERAGE, 
			    IncomeCategory.FROM_40000_TO_60000, 
			    InvestmentYear.FIVE_TO_SEVEN, 
			    true
			);
	}

	@Test
	void testGetClientInvestmentPreference() {
		// Make a GET request to fetch the InvestmentPreference by clientId
		ResponseEntity<InvestmentPreference> response = restTemplate.getForEntity(
				"/stock_stream/investment_preference/C001", InvestmentPreference.class);

		// Check the status code
		assertEquals(OK, response.getStatusCode());

		// Check if the response body matches the expected values
		InvestmentPreference investmentPreference = response.getBody();
		assertNotNull(investmentPreference);
		assertEquals("C001", investmentPreference.getClientId());
		assertEquals("College Fund", investmentPreference.getInvestmentPurpose().getName());
	}

	@Test
	void testAddClientInvestmentPreference() {
		// Make a POST request to add a new InvestmentPreference
		HttpEntity<InvestmentPreference> request = new HttpEntity<>(testInvestmentPreference);
		ResponseEntity<InvestmentPreference> response = restTemplate.postForEntity(
				"/stock_stream/investment_preference", request, InvestmentPreference.class);

		// Check the status code
		assertEquals(OK, response.getStatusCode());

		// Verify that the data was inserted into the database
		Integer count = jdbcTemplate.queryForObject(
			"SELECT COUNT(*) FROM investment_preferences WHERE client_id = ?", 
			Integer.class, "C001"
		);
		assertEquals(1, count);
	}

	@Test
	void testUpdateClientInvestmentPreference() {
		// Modify the InvestmentPreference object
		
		testInvestmentPreference2.setInvestmentPurpose(InvestmentPurpose.RETIREMENT);

		// Make a PUT request to update the InvestmentPreference
		HttpEntity<InvestmentPreference> request = new HttpEntity<>(testInvestmentPreference2);
		ResponseEntity<InvestmentPreference> response = restTemplate.exchange(
				"/stock_stream/investment_preference", HttpMethod.PUT, request, InvestmentPreference.class);

		// Check the status code
		assertEquals(OK, response.getStatusCode());

		// Verify that the data was updated in the database
		String updatedPurpose = jdbcTemplate.queryForObject(
			"SELECT investment_purpose FROM investment_preferences WHERE client_id = ?", 
			String.class, "C001"
		);
		assertEquals("RETIREMENT", updatedPurpose);
	}

	@Test
	void testDeleteClientInvestmentPreference() {
		// Make a DELETE request to remove the InvestmentPreference by clientId
		ResponseEntity<Void> response = restTemplate.exchange(
				"/stock_stream/investment_preference/C002", HttpMethod.DELETE, null, Void.class);

		// Check the status code
		assertEquals(OK, response.getStatusCode());

		// Verify that the data was removed from the database
		Integer count = jdbcTemplate.queryForObject(
			"SELECT COUNT(*) FROM investment_preferences WHERE client_id = ?", 
			Integer.class, "C002"
		);
		assertEquals(0, count);
	}
}
