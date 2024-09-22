package com.capstone.models;

public enum IncomeCategory {
	BELOW_20000("Below $20,000", 0, 19999),
    FROM_20000_TO_40000("$20,000 - $40,000", 20000, 39999),
    FROM_40000_TO_60000("$40,000 - $60,000", 40000, 59999),
    FROM_60000_TO_80000("$60,000 - $80,000", 60000, 79999),
    ABOVE_80000("Above $80,000", 80000, Integer.MAX_VALUE);

    private final String description;
    private final int minIncome;
    private final int maxIncome;

    IncomeCategory(String description, int minIncome, int maxIncome) {
        this.description = description;
        this.minIncome = minIncome;
        this.maxIncome = maxIncome;
    }

    public String getDescription() {
        return description;
    }

    public int getMinIncome() {
        return minIncome;
    }

    public int getMaxIncome() {
        return maxIncome;
    }

    @Override
    public String toString() {
        return description;
    }
}