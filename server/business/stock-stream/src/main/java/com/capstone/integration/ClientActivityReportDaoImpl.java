package com.capstone.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capstone.integration.mapper.ClientActivityReportMapper;
import com.capstone.models.ClientActivityReport;

@Repository
public class ClientActivityReportDaoImpl implements ClientActivityReportDao{

	@Autowired
	private ClientActivityReportMapper mapper;
	
	public List<ClientActivityReport> getAllReports(String clientId) {
		List<ClientActivityReport> reports = mapper.getAllReports(clientId);
		return reports;
	}
}
