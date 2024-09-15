package com.fidelity.capstone.stock_stream;
import java.util.Objects;

public class Price {
    private double askPrice;
    private double bidPrice;
    private String priceTimestamp;
    private Instrument instrument;

    public double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(double askPrice) {
        if (askPrice < 0) {
            throw new IllegalArgumentException("Ask price cannot be negative");
        }
        this.askPrice = askPrice;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        if (bidPrice < 0) {
            throw new IllegalArgumentException("Bid price cannot be negative");
        }
        this.bidPrice = bidPrice;
    }

    public String getPriceTimestamp() {
        return priceTimestamp;
    }

    public void setPriceTimestamp(String priceTimestamp) {
        Objects.requireNonNull(priceTimestamp, "Price timestamp cannot be null");
        if (priceTimestamp.isEmpty()) {
            throw new IllegalArgumentException("Price timestamp cannot be empty");
        }
        this.priceTimestamp = priceTimestamp;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = Objects.requireNonNull(instrument, "Instrument cannot be null");
    }
}
