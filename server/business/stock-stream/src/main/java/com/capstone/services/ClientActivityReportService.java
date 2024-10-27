package com.capstone.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.integration.ClientActivityReportDaoImpl;
import com.capstone.models.ClientActivityReport;

@Service
public class ClientActivityReportService {

    @Autowired
    private ClientActivityReportDaoImpl clientActivityReportDao;

    public List<ClientActivityReport> getClientActivityReports(String clientId) {
        if (clientId == null || clientId.isEmpty()) {
            throw new IllegalArgumentException("Client ID cannot be null or empty");
        }

        return clientActivityReportDao.getAllReports(clientId);
    }
}
