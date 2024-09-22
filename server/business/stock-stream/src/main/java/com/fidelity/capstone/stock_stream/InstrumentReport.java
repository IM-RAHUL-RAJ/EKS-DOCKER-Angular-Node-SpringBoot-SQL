package com.fidelity.capstone.stock_stream;

import java.math.BigDecimal;

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
		this.peRatio = peRatio;
	}
	 
}
