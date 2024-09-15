package com.fidelity.capstone.stock_stream;

public enum InvestmentPurpose {
	COLLEGE_FUND("Saving for higher education expenses"),
    RETIREMENT("Saving for retirement years"),
    EMERGENCY_FUND("Setting aside funds for unexpected expenses"),
    HOME_PURCHASE("Saving for a down payment or new home"),
    VACATION("Funds set aside for travel or vacations"),
    HEALTHCARE("Investments aimed at covering medical expenses"),
    CHILDRENS_EDUCATION("Saving for future education costs for children"),
    HOBBY_INTEREST("Funds intended for personal hobbies or interests"),
    DEBT_REPAYMENT("Investing to pay down existing debts or loans"),
    MAJOR_PURCHASE("Saving for significant one-time purchases"),
    BUSINESS_INVESTMENT("Funds meant for starting or expanding a personal business"),
    PHILANTHROPY("Investments intended to support charitable causes"),
    WEALTH_BUILDING("General investment for growing wealth"),
    TAX_PLANNING("Investments aimed at optimizing tax benefits"),
    OTHERS("Other investment purposes not listed");

    private final String description;

    InvestmentPurpose(String description) {
        this.description = description;
    }

	public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name() + ": " + description;
    }
}
