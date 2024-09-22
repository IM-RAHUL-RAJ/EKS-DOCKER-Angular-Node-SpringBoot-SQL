package com.capstone.models;

import java.math.BigDecimal;
import java.util.Objects;

public class InstrumentReport {
	 private String instrumentId;
	 private BigDecimal peRatio;
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	public BigDecimal getPeRatio() {
		return peRatio;
	}
	public void setPeRatio(BigDecimal peRatio) {
		Objects.requireNonNull(peRatio, "PE Ratio cannot be null");
        if (peRatio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(" cannot be negative.");
        }
		this.peRatio = peRatio;
	}
	 
}
