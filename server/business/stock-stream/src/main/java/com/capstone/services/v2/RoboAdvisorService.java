package com.capstone.services.v2;

import java.util.List;

import com.capstone.models.Holding;
import com.capstone.models.InvestmentPreference;
import com.capstone.models.Price;

public interface RoboAdvisorService {

	List<Price> getBuySuggestions(String clientId);

	List<Holding> getSellSuggestions(String clientId);

}