package com.fidelity.capstone.utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fidelity.capstone.exceptions.RoboAdvisorException;
import com.fidelity.capstone.stock_stream.Instrument;
import com.fidelity.capstone.stock_stream.InstrumentReport;
import com.fidelity.capstone.stock_stream.InvestmentPreference;
import com.fidelity.capstone.stock_stream.InvestmentYear;
import com.fidelity.capstone.stock_stream.Price;
import com.fidelity.capstone.stock_stream.RiskTolerance;

public class RoboAdvisorService {
	private List<Price> roboAdvisorSuggestionsList = new ArrayList<Price>();

	public void addSuggestion(BigDecimal askPrice, BigDecimal bidPrice, LocalDateTime priceTimeStamp,
			Instrument instrument) {
		Price newRoboAdvisorSuggestion = new Price();
		try {
			newRoboAdvisorSuggestion.setAskPrice(askPrice);
			newRoboAdvisorSuggestion.setBidPrice(bidPrice);
			newRoboAdvisorSuggestion.setPriceTimeStamp(priceTimeStamp);
			newRoboAdvisorSuggestion.setInstrument(instrument);
		} catch (Exception e) {
			throw new RoboAdvisorException.RoboAdvisorValidationException(
					"Invalid suggestion data : " + e.getMessage());
		}
		this.roboAdvisorSuggestionsList.add(newRoboAdvisorSuggestion);
	}

	public void generateSuggestionBasedOnInvestmentPreferences(InvestmentPreference investmentPreference,
			List<Price> allPrices,List<InstrumentReport> instrumentReports) {
		Objects.requireNonNull(investmentPreference, "Investment Preferences cannot be null");
		Objects.requireNonNull(allPrices, "Price list cannot be null");
		Objects.requireNonNull(instrumentReports, "Instrument Reports cannot be null");
		
		int numberOfSuggestions = 5;
		
		 // Convert instrumentReports to a map for quick lookup
	    Map<String, BigDecimal> peRatioMap = instrumentReports.stream()
	            .collect(Collectors.toMap(InstrumentReport::getInstrumentId, InstrumentReport::getPeRatio));

	    // Get Risk Tolerance and Investment Duration
	    RiskTolerance riskTolerance = investmentPreference.getRiskTolerance();
	    InvestmentYear investmentDuration = investmentPreference.getInvestmentYear();

	    // Calculate and sort prices
	    List<Price> sortedPrices = allPrices.stream()
	            .filter(price -> peRatioMap.containsKey(price.getInstrument().getInstrumentId()))
	            .map(price -> {
	                // Get PE Ratio for the instrument
	                BigDecimal peRatioBigDecimal = peRatioMap.get(price.getInstrument().getInstrumentId());
	                double peRatio = peRatioBigDecimal.doubleValue();

	                // Calculate rank score
	                double riskToleranceScore = getRiskToleranceScore(riskTolerance);
	                double adjustedPeRatio = adjustPeRatioForDuration(peRatio, investmentDuration);
	                double rankScore = (riskToleranceScore * 2) - adjustedPeRatio;

	                // Set rank score in the price (assuming Price has a method to store rank score)
	                price.setRankScore(rankScore); 

	                return price;
	            })
	            .sorted(Comparator.comparingDouble(Price::getRankScore).reversed()) // Sort by rankScore in descending order
	            .collect(Collectors.toList());

	    roboAdvisorSuggestionsList = new ArrayList<>(sortedPrices.subList(0, numberOfSuggestions));
		

	}

	public List<Price> getAllSuggestions() {
		return roboAdvisorSuggestionsList;
	}

//	private static Price createPrice(BigDecimal askPrice, BigDecimal bidPrice, LocalDateTime timestamp, String instrumentId,
//			String externalIdType, String externalId, String categoryId, String description, int maxQuantity,
//			int minQuantity) {
//		Price price = new Price();
//		price.setAskPrice(askPrice);
//		price.setBidPrice(bidPrice);
//		price.setPriceTimeStamp(timestamp);
//
//		Instrument instrument = new Instrument();
//		instrument.setInstrumentId(instrumentId);
//		instrument.setExternalIdType(externalIdType);
//		instrument.setExternalId(externalId);
//		instrument.setCategoryId(categoryId);
//		instrument.setInstrumentDescription(description);
//		instrument.setMaxQuantity(maxQuantity);
//		instrument.setMinQuantity(minQuantity);
//
//		price.setInstrument(instrument);
//		return price;
//	}
	 private static double getRiskToleranceScore(RiskTolerance riskTolerance) {
	        switch (riskTolerance) {
	            case CONSERVATIVE:
	                return 1.0;
	            case BELOW_AVERAGE:
	                return 2.0;
	            case AVERAGE:
	                return 3.0;
	            case ABOVE_AVERAGE:
	                return 4.0;
	            case AGGRESSIVE:
	                return 5.0;
	            default:
	                throw new IllegalArgumentException("Unknown risk tolerance: " + riskTolerance);
	        }
	    }

	    private static double adjustPeRatioForDuration(double peRatio, InvestmentYear duration) {
	        switch (duration) {
	            case ZERO_TO_FIVE:
	                return peRatio * 1.2; // Example: Higher multiplier for short duration
	            case FIVE_TO_SEVEN:
	                return peRatio * 1.1;
	            case SEVEN_TO_TEN:
	                return peRatio * 1.05;
	            case TEN_TO_FIFTEEN:
	                return peRatio * 1.0; // Base case: No adjustment
	            default:
	                throw new IllegalArgumentException("Unknown investment duration: " + duration);
	        }
	    }
}
