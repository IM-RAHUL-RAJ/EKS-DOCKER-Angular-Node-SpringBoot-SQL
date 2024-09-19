package com.fidelity.capstone.stock_stream;

public enum InvestmentYear {
	ZERO_TO_FIVE("0 - 5 years", 0, 5),
    FIVE_TO_SEVEN("5 - 7 years", 5, 7),
    SEVEN_TO_TEN("7 - 10 years", 7, 10),
    TEN_TO_FIFTEEN("10 - 15 years", 10, 15);

    private final String description;
    private final int minYears;
    private final int maxYears;

    InvestmentYear(String description, int minYears, int maxYears) {
        this.description = description;
        this.minYears = minYears;
        this.maxYears = maxYears;
    }

    public String getDescription() {
        return description;
    }

    public int getMinYears() {
        return minYears;
    }

    public int getMaxYears() {
        return maxYears;
    }

    @Override
    public String toString() {
        return description;
    }
}
