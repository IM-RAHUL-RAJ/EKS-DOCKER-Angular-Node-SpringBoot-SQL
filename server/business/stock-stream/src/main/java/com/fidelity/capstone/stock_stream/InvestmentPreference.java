package com.fidelity.capstone.stock_stream;

import java.util.Objects;

import com.fidelity.capstone.exceptions.RoboAdvisorMandatoryException;

public class InvestmentPreference {
	private String clientId;
	private InvestmentPurpose investmentPurpose;
	private String investmentPurposeDescription;
	private RiskTolerance riskTolerance;
	private IncomeCategory incomeCategory;
	private InvestmentYear investmentYear;
	private boolean isRoboAdvisorTermsAccepted;

	public InvestmentPreference(String clientId, InvestmentPurpose investmentPurpose,
			String investmentPurposeDescription, RiskTolerance riskTolerance, IncomeCategory incomeCategory,
			InvestmentYear investmentYear, boolean isRoboAdvisorTermsAccepted) throws RoboAdvisorMandatoryException {
		super();

		if (Objects.isNull(clientId) || Objects.isNull(investmentPurpose) || Objects.isNull(riskTolerance)
				|| Objects.isNull(incomeCategory) || Objects.isNull(investmentYear)
				|| Objects.isNull(investmentPurposeDescription)) {

			throw new IllegalArgumentException(
					"All parameters are mandatory to create an investment preference cannot be null");

		}

		if (!isRoboAdvisorTermsAccepted) {
			throw new RoboAdvisorMandatoryException(
					"User must accept the robo advisor terms & conditions in order to proceed.");
		}

		this.clientId = clientId;
		this.investmentPurpose = investmentPurpose;
		this.investmentPurposeDescription = investmentPurposeDescription;
		this.riskTolerance = riskTolerance;
		this.incomeCategory = incomeCategory;
		this.investmentYear = investmentYear;
		this.isRoboAdvisorTermsAccepted = isRoboAdvisorTermsAccepted;
	}

	public InvestmentPurpose getInvestmentPurpose() {
		return investmentPurpose;
	}

	public void setInvestmentPurpose(InvestmentPurpose investmentPurpose) {
		this.investmentPurpose = investmentPurpose;
	}

	public RiskTolerance getRiskTolerance() {
		return riskTolerance;
	}

	public String getInvestmentPurposeDescription() {
		return investmentPurposeDescription;
	}

	public void setInvestmentPurposeDescription(String investmentPurposeDescription) {
		this.investmentPurposeDescription = investmentPurposeDescription;
	}

	public void setRiskTolerance(RiskTolerance riskTolerance) {
		this.riskTolerance = riskTolerance;
	}

	public IncomeCategory getIncomeCategory() {
		return incomeCategory;
	}

	public void setIncomeCategory(IncomeCategory incomeCategory) {
		this.incomeCategory = incomeCategory;
	}

	public InvestmentYear getInvestmentYear() {
		return investmentYear;
	}

	public void setInvestmentYear(InvestmentYear investmentYear) {
		this.investmentYear = investmentYear;
	}

	public boolean isRoboAdvisorTermsAccepted() {
		return isRoboAdvisorTermsAccepted;
	}

	private void setRoboAdvisorTermsAccepted(boolean isRoboAdvisorTermsAccepted) {
		this.isRoboAdvisorTermsAccepted = isRoboAdvisorTermsAccepted;
	}

	public String getClientId() {
		return clientId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(clientId, incomeCategory, investmentPurpose, investmentYear, isRoboAdvisorTermsAccepted,
				riskTolerance);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvestmentPreference other = (InvestmentPreference) obj;
		return Objects.equals(clientId, other.clientId) && incomeCategory == other.incomeCategory
				&& investmentPurpose == other.investmentPurpose && investmentYear == other.investmentYear
				&& isRoboAdvisorTermsAccepted == other.isRoboAdvisorTermsAccepted
				&& riskTolerance == other.riskTolerance;
	}

	@Override
	public String toString() {
		return "InvestmentPreference [clientId=" + clientId + ", investmentPurpose=" + investmentPurpose
				+ ", riskTolerance=" + riskTolerance + ", incomeCategory=" + incomeCategory + ", investmentYear="
				+ investmentYear + ", isRoboAdvisorTermsAccepted=" + isRoboAdvisorTermsAccepted + "]";
	}

}
