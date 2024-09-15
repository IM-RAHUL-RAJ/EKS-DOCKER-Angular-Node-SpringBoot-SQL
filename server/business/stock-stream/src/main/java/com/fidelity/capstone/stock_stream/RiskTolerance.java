package com.fidelity.capstone.stock_stream;

public enum RiskTolerance {
	CONSERVATIVE("Focuses on capital preservation with low-risk investments and modest returns."),
	BELOW_AVERAGE("Takes on slightly more risk than conservative, with a mix of low and moderate-risk investments."),
	AVERAGE("Seeks a balanced approach with moderate risk and potential for average returns."),
	ABOVE_AVERAGE("Willing to accept higher risk for the potential of higher returns, with more volatile investments."),
	AGGRESSIVE(
			"Prioritizes high returns and accepts significant risk, often with high-growth and speculative investments.");

	private final String description;

	RiskTolerance(String string) {
		this.description = string;
	}

	public String getDescription() {
		return description;
	}

}
