package com.capstone.models;

public enum InvestmentPurpose {
    COLLEGE_FUND("College Fund", "Saving for higher education expenses"),
    RETIREMENT("Retirement", "Saving for retirement years"),
    EMERGENCY_FUND("Emergency Fund", "Setting aside funds for unexpected expenses"),
    HOME_PURCHASE("Home Purchase", "Saving for a down payment or new home"),
    VACATION("Vacation", "Funds set aside for travel or vacations"),
    HEALTHCARE("Healthcare", "Investments aimed at covering medical expenses"),
    CHILDRENS_EDUCATION("Children's Education", "Saving for future education costs for children"),
    HOBBY_INTEREST("Hobby Interest", "Funds intended for personal hobbies or interests"),
    DEBT_REPAYMENT("Debt Repayment", "Investing to pay down existing debts or loans"),
    MAJOR_PURCHASE("Major Purchase", "Saving for significant one-time purchases"),
    BUSINESS_INVESTMENT("Business Investment", "Funds meant for starting or expanding a personal business"),
    PHILANTHROPY("Philanthropy", "Investments intended to support charitable causes"),
    WEALTH_BUILDING("Wealth Building", "General investment for growing wealth"),
    TAX_PLANNING("Tax Planning", "Investments aimed at optimizing tax benefits"),
    OTHERS("Others", "Other investment purposes not listed");

    private final String name;
    private final String description;

    InvestmentPurpose(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static InvestmentPurpose of(String name) {
        for (InvestmentPurpose purpose : values()) {
            if (purpose.name.equalsIgnoreCase(name)) {
                return purpose;
            }
        }
        throw new IllegalArgumentException("No enum constant for code: " + name);
    }

    
}

