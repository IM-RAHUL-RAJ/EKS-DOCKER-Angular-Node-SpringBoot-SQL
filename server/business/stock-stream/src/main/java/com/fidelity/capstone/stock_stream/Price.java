package com.fidelity.capstone.stock_stream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

public class Price {
	private BigDecimal askPrice;
	private BigDecimal bidPrice;
	private LocalDateTime priceTimeStamp;
	private Instrument instrument; 
	private double rankScore;
	
	public double getRankScore() {
		return rankScore;
	}
	public void setRankScore(double rankScore) {
		this.rankScore = rankScore;
	}
	public BigDecimal getAskPrice() {
		return askPrice;
	}
	public void setAskPrice(BigDecimal askPrice) {
        Objects.requireNonNull(askPrice, "Ask Price cannot be null");
        if (askPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Ask Price cannot be negative.");
        }
        this.askPrice = askPrice.setScale(2, RoundingMode.HALF_UP);
	}
	public BigDecimal getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(BigDecimal bidPrice) {
		Objects.requireNonNull(bidPrice, "Ask Price cannot be null");
        if (bidPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Bid Price cannot be negative.");
        }
		this.bidPrice = bidPrice.setScale(2, RoundingMode.HALF_UP);
	}
	public LocalDateTime getPriceTimeStamp() {
		return priceTimeStamp;
	}
	public void setPriceTimeStamp(LocalDateTime priceTimeStamp) {
		Objects.requireNonNull(priceTimeStamp, "Price TimeStamp cannot be null");
		this.priceTimeStamp = priceTimeStamp;
	}
	public Instrument getInstrument() {
		return instrument;
	}
	public void setInstrument(Instrument instrument) {
		Objects.requireNonNull(instrument, "Instrument cannot be null");
		this.instrument = instrument;
	}

}
