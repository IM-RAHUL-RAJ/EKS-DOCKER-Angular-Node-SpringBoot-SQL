package com.fidelity.capstone.stock_stream;

public class ClientActivityReport {
    private String title;
    private String summary;

    public ClientActivityReport(String title, String summary) {
        this.title = title;
        this.summary = summary;
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
