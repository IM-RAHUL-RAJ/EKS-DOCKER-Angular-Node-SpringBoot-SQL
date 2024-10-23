package com.capstone.services.v2;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.exceptions.ClientHasNoHoldingsToSellException;
import com.capstone.integration.FmtsDao;
import com.capstone.integration.InvestmentPreferenceDao;
import com.capstone.models.Holding;
import com.capstone.models.InvestmentPreference;
import com.capstone.models.Price;

@Service
public class RoboAdvisorServiceImpl implements RoboAdvisorService {

	@Autowired
	FmtsDao instrumentDao;

	@Autowired
	InvestmentPreferenceDao investmentPreferenceDao;

	@Autowired
	HoldingService holdingService;

	private List<Price> instruments;

	public RoboAdvisorServiceImpl() {
		super();
	}

	@Override
	public List<Price> getBuySuggestions(String clientId) {

		if (clientId == null) {
			throw new IllegalArgumentException("client Id cannot be null");
		}

		InvestmentPreference preference = investmentPreferenceDao.getInvestmentPreference(clientId);

		instruments = instrumentDao.getAllInstruments();
		List<Price> suggestions = new ArrayList<>();

		switch (preference.getInvestmentPurpose()) {
		case RETIREMENT:
			suggestions.addAll(suggestInstruments(preference, new BigDecimal("0.05"), new BigDecimal("0.2")));
			break;
		case COLLEGE_FUND:
			suggestions.addAll(suggestInstruments(preference, new BigDecimal("0.03"), new BigDecimal("0.15")));
			break;
		case EMERGENCY_FUND:
			suggestions.addAll(suggestInstruments(preference, new BigDecimal("0.01"), new BigDecimal("0.05")));
			break;
		case HOME_PURCHASE:
			suggestions.addAll(suggestInstruments(preference, new BigDecimal("0.02"), new BigDecimal("0.1")));
			break;
		case VACATION:
			suggestions.addAll(suggestInstruments(preference, new BigDecimal("0.02"), new BigDecimal("0.1")));
			break;
		case HEALTHCARE:
			suggestions.addAll(suggestInstruments(preference, new BigDecimal("0.02"), new BigDecimal("0.1")));
			break;
		case CHILDRENS_EDUCATION:
			suggestions.addAll(suggestInstruments(preference, new BigDecimal("0.04"), new BigDecimal("0.15")));
			break;
		case HOBBY_INTEREST:
			suggestions.addAll(suggestInstruments(preference, new BigDecimal("0.02"), new BigDecimal("0.2")));
			break;
		case DEBT_REPAYMENT:
			suggestions.addAll(suggestInstruments(preference, new BigDecimal("0.01"), new BigDecimal("0.05")));
			break;
		case MAJOR_PURCHASE:
			suggestions.addAll(suggestInstruments(preference, new BigDecimal("0.02"), new BigDecimal("0.1")));
			break;
		case BUSINESS_INVESTMENT:
			suggestions.addAll(suggestInstruments(preference, new BigDecimal("0.06"), new BigDecimal("0.25")));
			break;
		case PHILANTHROPY:
			suggestions.addAll(suggestInstruments(preference, new BigDecimal("0.01"), new BigDecimal("0.05")));
			break;
		case WEALTH_BUILDING:
			suggestions.addAll(suggestInstruments(preference, new BigDecimal("0.05"), new BigDecimal("0.2")));
			break;
		case TAX_PLANNING:
			suggestions.addAll(suggestInstruments(preference, new BigDecimal("0.02"), new BigDecimal("0.1")));
			break;
		case OTHERS:
			suggestions.addAll(suggestInstruments(preference, new BigDecimal("0.02"), new BigDecimal("0.1")));
			break;
		default:
			break;
		}

		return suggestions;
	}

	@Override
	public List<Holding> getSellSuggestions(String clientId) {
		if (clientId == null) {
			throw new IllegalArgumentException("client Id cannot be null");
		}

		List<Holding> ownedHoldings;
		try {
			ownedHoldings = holdingService.getClientPortfolio(clientId);

		} catch (NoSuchElementException e) {
			throw new ClientHasNoHoldingsToSellException("The client has no holdings to sell :" + clientId);
		} catch (SQLException e) {
			throw new RuntimeException("Could not fetch portfolio information for client Id :" + clientId);
		}
		InvestmentPreference preference = investmentPreferenceDao.getInvestmentPreference(clientId);

		List<Holding> suggestions = new ArrayList<Holding>();

		for (Holding holding : ownedHoldings) {
			BigDecimal expectedReturn = calculateExpectedReturn(holding);
			BigDecimal risk = calculateRisk(holding);
			BigDecimal currentPrice = holding.getLtp(); // Last traded price (LTP)

			// Adjust criteria based on investment preference
			switch (preference.getRiskTolerance()) {
			case AGGRESSIVE:
				// Suggest selling if expected return is low or risk is unacceptable
				if (expectedReturn.compareTo(new BigDecimal("0.05")) < 0 || risk.compareTo(new BigDecimal("0.3")) > 0) {
					suggestions.add(holding);
				}
				break;

			case ABOVE_AVERAGE:
				// Suggest selling if risk is higher than acceptable level
				if (risk.compareTo(new BigDecimal("0.25")) > 0) {
					suggestions.add(holding);
				}
				break;

			case AVERAGE:
				// Suggest selling if expected return is below average and risk is moderate
				if (expectedReturn.compareTo(new BigDecimal("0.02")) < 0 && risk.compareTo(new BigDecimal("0.2")) < 0) {
					suggestions.add(holding);
				}
				break;

			case BELOW_AVERAGE:
				// Suggest selling if current price is significantly lower (e.g., 10% lower)
				if (currentPrice.compareTo(holding.getAveragePrice().multiply(BigDecimal.valueOf(0.9))) < 0) {
					suggestions.add(holding);
				}
				break;

			case CONSERVATIVE:
				// Suggest selling if expected return is negative or risk is high
				if (expectedReturn.compareTo(BigDecimal.ZERO) < 0 || risk.compareTo(new BigDecimal("0.15")) > 0) {
					suggestions.add(holding);
				}
				break;
			}
		}

		return suggestions;
	}

	private List<Price> suggestInstruments(InvestmentPreference preference, BigDecimal minExpectedReturn,
			BigDecimal maxRisk) {
		List<Price> suggested = new ArrayList<>();

		for (Price price : instruments) {
			BigDecimal expectedReturn = calculateExpectedReturn(price);
			BigDecimal risk = calculateRisk(price);

			if (expectedReturn.compareTo(minExpectedReturn) >= 0 && risk.compareTo(maxRisk) <= 0) {
				suggested.add(price);
			}
		}

		return suggested;
	}

	private BigDecimal calculateExpectedReturn(Price price) {
		switch (price.getInstrument().getCategoryId()) {
		case "STOCK":
			return new BigDecimal("0.07"); // Example return for stocks
		case "GOVT":
			return new BigDecimal("0.03"); // Example return for government bonds
		case "CD":
			return new BigDecimal("0.02"); // Example return for CDs
		default:
			return new BigDecimal("0.01"); // Default return
		}
	}

	private BigDecimal calculateRisk(Price price) {
		switch (price.getInstrument().getCategoryId()) {
		case "STOCK":
			return new BigDecimal("0.15"); // Higher risk for stocks
		case "GOVT":
			return new BigDecimal("0.05"); // Lower risk for government bonds
		case "CD":
			return new BigDecimal("0.02"); // Very low risk for CDs
		default:
			return new BigDecimal("0.1"); // Default risk
		}
	}

	private BigDecimal calculateExpectedReturn(Holding holding) {
		double profitLoss = holding.getProfitLoss(); // Ensure this is a valid double
		double investedCapital = holding.getInvestedCapital(); // Ensure this is not zero to avoid division by zero

		if (investedCapital <= 0) {
			return BigDecimal.ZERO; // or handle accordingly
		}

		return BigDecimal.valueOf(profitLoss / investedCapital); // Ensure this division results in a valid decimal
	}

	private BigDecimal calculateRisk(Holding holding) {
		double dayChangePercent = holding.getDayChangePercent(); // Ensure this is a valid double
		return BigDecimal.valueOf(dayChangePercent / 100); // Convert percent to decimal
	}

}
