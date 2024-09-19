package com.fidelity.capstone.utils;

import java.util.ArrayList;
import java.util.List;

import com.fidelity.capstone.stock_stream.ClientActivityReport;

public class ClientActivityReportService {

    // Stub method to get client activity reports
	public List<ClientActivityReport> getClientActivityReports(String clientId) {
        if (clientId == null || clientId.isEmpty()) {
            throw new IllegalArgumentException("Client ID cannot be null or empty");
        }

        List<ClientActivityReport> reports = new ArrayList<>();
        reports.add(new ClientActivityReport("Annual Report 2023", "Summary of the annual performance for 2023."));
        reports.add(new ClientActivityReport("Quarterly Report Q1 2024", "Summary of the first quarter performance for 2024."));
        reports.add(new ClientActivityReport("Monthly Report August 2024", "Summary of the performance for August 2024."));
        return reports;
    }

}

