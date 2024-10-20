package com.capstone.models;

public class ClientActivityReport {
    private String title;
    private String summary;
    private String clientId; 

    public ClientActivityReport(String clientId,String title, String summary) {
    	this.clientId=clientId;
        this.title = title;
        this.summary = summary;
    }

    public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	// Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
