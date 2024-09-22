package com.capstone.models;
import java.util.Objects;

public class Instrument {
    private String instrumentId;
    private String externalIdType;
    private String externalId;
    private String categoryId;
    private String instrumentDescription;
    private int maxQuantity;
    private int minQuantity;

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        Objects.requireNonNull(instrumentId, "Instrument ID cannot be null");
        if (instrumentId.isEmpty()) {
            throw new IllegalArgumentException("Instrument ID cannot be empty");
        }
        this.instrumentId = instrumentId;
    }

    public String getExternalIdType() {
        return externalIdType;
    }

    public void setExternalIdType(String externalIdType) {
        Objects.requireNonNull(externalIdType, "External ID type cannot be null");
        if (externalIdType.isEmpty()) {
            throw new IllegalArgumentException("External ID type cannot be empty");
        }
        this.externalIdType = externalIdType;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        Objects.requireNonNull(externalId, "External ID cannot be null");
        if (externalId.isEmpty()) {
            throw new IllegalArgumentException("External ID cannot be empty");
        }
        this.externalId = externalId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        Objects.requireNonNull(categoryId, "Category ID cannot be null");
        if (categoryId.isEmpty()) {
            throw new IllegalArgumentException("Category ID cannot be empty");
        }
        this.categoryId = categoryId;
    }

    public String getInstrumentDescription() {
        return instrumentDescription;
    }

    public void setInstrumentDescription(String instrumentDescription) {
        Objects.requireNonNull(instrumentDescription, "Instrument description cannot be null");
        if (instrumentDescription.isEmpty()) {
            throw new IllegalArgumentException("Instrument description cannot be empty");
        }
        this.instrumentDescription = instrumentDescription;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        if (maxQuantity <= 0) {
            throw new IllegalArgumentException("Max quantity must be greater than zero");
        }
        this.maxQuantity = maxQuantity;
    }
    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        if (minQuantity <= 0) {
            throw new IllegalArgumentException("Min quantity must be greater than zero");
        }
        this.minQuantity = minQuantity;
    }
}
