package com.capstone.models;

public enum ProfileStatus {
	COMPLETE("Complete"),
    PENDING("Pending");

	private final String status;

    ProfileStatus(String status) {
        this.status = status;
    }

	public String getStatus() {
		return status;
	}
	
	 @Override
	    public String toString() {
	        return this.status;
	    }
}
