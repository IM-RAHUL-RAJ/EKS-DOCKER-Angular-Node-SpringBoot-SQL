package com.capstone.models;

public enum InvestmentYear {
	ZERO_TO_FIVE("0 - 5 years", 0, 5),
    FIVE_TO_SEVEN("5 - 7 years", 5, 7),
    SEVEN_TO_TEN("7 - 10 years", 7, 10),
    TEN_TO_FIFTEEN("10 - 15 years", 10, 15);

    private final String name;
    private final int minYears;
    private final int maxYears;

    InvestmentYear(String description, int minYears, int maxYears) {
        this.name = description;
        this.minYears = minYears;
        this.maxYears = maxYears;
    }

    public String getName() {
        return name;
    }

    public int getMinYears() {
        return minYears;
    }

    public int getMaxYears() {
        return maxYears;
    }

    public static InvestmentYear of(String description) {
        for (InvestmentYear category : values()) {
            if (category.name.equalsIgnoreCase(description)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No IncomeCategory for description: " + description);
    }
    

}
