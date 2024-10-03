package com.capstone.models;

public enum RiskTolerance {
    CONSERVATIVE("Conservative", "Focuses on capital preservation with low-risk investments and modest returns."),
    BELOW_AVERAGE("Below Average", "Takes on slightly more risk than conservative, with a mix of low and moderate-risk investments."),
    AVERAGE("Average", "Seeks a balanced approach with moderate risk and potential for average returns."),
    ABOVE_AVERAGE("Above Average", "Willing to accept higher risk for the potential of higher returns, with more volatile investments."),
    AGGRESSIVE("Aggressive", "Prioritizes high returns and accepts significant risk, often with high-growth and speculative investments.");

    private final String name;
    private final String description;

    RiskTolerance(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static RiskTolerance of(String code) {
        for (RiskTolerance tolerance : values()) {
            if (tolerance.name.equalsIgnoreCase(code)) {
                return tolerance;
            }
        }
        throw new IllegalArgumentException("No enum constant for code: " + code);
    }
}
