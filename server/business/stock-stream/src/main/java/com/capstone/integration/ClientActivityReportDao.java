package com.capstone.integration;

import java.sql.SQLException;
import java.util.List;

import com.capstone.models.ClientActivityReport;

public interface ClientActivityReportDao {
	
	
	    List<ClientActivityReport> getClientActivityReports(String clientId) throws SQLException;


}