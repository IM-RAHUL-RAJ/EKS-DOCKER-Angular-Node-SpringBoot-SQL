package com.capstone.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.exceptions.ClientHasNoHoldingsToSellException;
import com.capstone.models.Holding;
import com.capstone.models.IncomeCategory;
import com.capstone.models.InvestmentPreference;
import com.capstone.models.InvestmentPurpose;
import com.capstone.models.InvestmentYear;
import com.capstone.models.Price;
import com.capstone.models.RiskTolerance;
import com.capstone.services.v2.RoboAdvisorService;
import com.capstone.services.v2.RoboAdvisorServiceImpl;

@SpringBootTest
class RoboAdvisorServiceTest {

	@Autowired
	private RoboAdvisorService roboAdvisorService;

	@Test
	void getSuggestionsToSucceed() {
		List<Price> suggestedInstruments = roboAdvisorService.getBuySuggestions("C001");
		System.out.println(suggestedInstruments);
		assertNotNull(suggestedInstruments);

	}

	@Test
	void getSuggestionsToThrowIllegalArgument() {

		assertThrows(IllegalArgumentException.class, () -> {
			roboAdvisorService.getBuySuggestions(null);
		});

	}

	@Test
	void getSellSuggestionsToSucceed() {
		List<Holding> suggestedHoldings = roboAdvisorService.getSellSuggestions("C002");
		System.out.println("Holdings:" + suggestedHoldings);
		assertNotNull(suggestedHoldings);
	}
	
	@Test
	void getSellSuggestionsToThrowNoHoldingsToSell() {
		assertThrows(ClientHasNoHoldingsToSellException.class, () -> {
			roboAdvisorService.getSellSuggestions("C004");
		});
	}
	
	@Test
	void getSellSuggestionsToThrowIllegalArgument() {

		assertThrows(IllegalArgumentException.class, () -> {
			roboAdvisorService.getSellSuggestions(null);
		});

	}

}
