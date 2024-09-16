package com.fidelity.capstone.utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fidelity.capstone.exceptions.RoboAdvisorException;
import com.fidelity.capstone.stock_stream.Instrument;
import com.fidelity.capstone.stock_stream.RoboAdvisorSuggestion;

public class RoboAdvisorService {
	private List<RoboAdvisorSuggestion> roboAdvisorSuggestionsList = new ArrayList<RoboAdvisorSuggestion>();
	
	public void addSuggestion(BigDecimal askPrice, BigDecimal bidPrice, LocalDateTime priceTimeStamp, Instrument instrument) {
		RoboAdvisorSuggestion newRoboAdvisorSuggestion = new RoboAdvisorSuggestion();
		try {
			newRoboAdvisorSuggestion.setAskPrice(askPrice);
			newRoboAdvisorSuggestion.setBidPrice(bidPrice);
			newRoboAdvisorSuggestion.setPriceTimeStamp(priceTimeStamp);
			newRoboAdvisorSuggestion.setInstrument(instrument);
		}catch (Exception e) {
			throw new RoboAdvisorException.RoboAdvisorValidationException("Invalid suggestion data : " + e.getMessage());
		}
		this.roboAdvisorSuggestionsList.add(newRoboAdvisorSuggestion);
	}
	
	public List<RoboAdvisorSuggestion> getAllSuggestions() {
		return roboAdvisorSuggestionsList;
	}
}
