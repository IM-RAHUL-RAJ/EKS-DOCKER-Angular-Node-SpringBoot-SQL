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
		
	    Map<String, BigDecimal> peRatioMap = instrumentReports.stream()
	            .collect(Collectors.toMap(InstrumentReport::getInstrumentId, InstrumentReport::getPeRatio));

	    RiskTolerance riskTolerance = investmentPreference.getRiskTolerance();
	    InvestmentYear investmentDuration = investmentPreference.getInvestmentYear();

	    List<Price> sortedPrices = allPrices.stream()
	            .filter(price -> peRatioMap.containsKey(price.getInstrument().getInstrumentId()))
	            .map(price -> {
	                BigDecimal peRatioBigDecimal = peRatioMap.get(price.getInstrument().getInstrumentId());
	                double peRatio = peRatioBigDecimal.doubleValue();

	                BigDecimal riskToleranceScore = getRiskToleranceScore(riskTolerance);
                    BigDecimal adjustedPeRatio = adjustPeRatioForDuration(peRatioBigDecimal, investmentDuration);
                    BigDecimal rankScore = (riskToleranceScore.multiply(BigDecimal.valueOf(2))).subtract(adjustedPeRatio);

                    price.setRankScore(rankScore);

                    return price;
	            })
	            .sorted(Comparator.comparing(Price::getRankScore).reversed()) 
                .collect(Collectors.toList());
	    
	    

	    roboAdvisorSuggestionsList = new ArrayList<>(sortedPrices.subList(0, sortedPrices.size()> numberOfSuggestions ? numberOfSuggestions : sortedPrices.size()));
		

	}

	public List<Price> getAllSuggestions() {
		return roboAdvisorSuggestionsList;
	}

	private static BigDecimal getRiskToleranceScore(RiskTolerance riskTolerance) {
        switch (riskTolerance) {
            case CONSERVATIVE:
                return BigDecimal.valueOf(1.0);
            case BELOW_AVERAGE:
                return BigDecimal.valueOf(2.0);
            case AVERAGE:
                return BigDecimal.valueOf(3.0);
            case ABOVE_AVERAGE:
                return BigDecimal.valueOf(4.0);
            case AGGRESSIVE:
                return BigDecimal.valueOf(5.0);
            default:
                throw new IllegalArgumentException("Unknown risk tolerance: " + riskTolerance);
        }
    }

    private static BigDecimal adjustPeRatioForDuration(BigDecimal peRatio, InvestmentYear duration) {
        switch (duration) {
            case ZERO_TO_FIVE:
                return peRatio.multiply(BigDecimal.valueOf(1.2)); 
            case FIVE_TO_SEVEN:
                return peRatio.multiply(BigDecimal.valueOf(1.1));
            case SEVEN_TO_TEN:
                return peRatio.multiply(BigDecimal.valueOf(1.05));
            case TEN_TO_FIFTEEN:
                return peRatio; 
            default:
                throw new IllegalArgumentException("Unknown investment duration: " + duration);
        }
    }
}
