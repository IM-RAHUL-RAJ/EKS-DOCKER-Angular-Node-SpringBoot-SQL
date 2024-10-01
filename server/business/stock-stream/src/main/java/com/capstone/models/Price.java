package com.capstone.models;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Price {
	private String instrumentId;
	private BigDecimal askPrice;
	private BigDecimal bidPrice;
	private Timestamp updateTime;
	private Instrument instrument; 
	private BigDecimal rankScore;
	
	public Price(String instrumentId, BigDecimal askPrice, BigDecimal bidPrice, Timestamp updateTime) {
		super();
		this.instrumentId = instrumentId;
		this.askPrice = askPrice;
		this.bidPrice = bidPrice;
		this.updateTime = updateTime;
	}
	public Price() {
		// TODO Auto-generated constructor stub
	}
	public BigDecimal getRankScore() {
		return rankScore;
	}
	public void setRankScore(BigDecimal rankScore) {
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
	public Timestamp getPriceTimeStamp() {
		return updateTime;
	}
	public void setPriceTimeStamp(Timestamp priceTimeStamp) {
		Objects.requireNonNull(priceTimeStamp, "Price TimeStamp cannot be null");
		this.updateTime = priceTimeStamp;
	}
	public Instrument getInstrument() {
		return instrument;
	}
	public void setInstrument(Instrument instrument) {
		Objects.requireNonNull(instrument, "Instrument cannot be null");
		this.instrument = instrument;
	}
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

}
