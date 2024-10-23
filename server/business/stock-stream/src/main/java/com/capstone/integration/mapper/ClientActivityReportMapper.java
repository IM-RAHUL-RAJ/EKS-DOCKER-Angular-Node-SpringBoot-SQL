package com.capstone.integration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.capstone.models.ClientActivityReport;

@Mapper
public interface ClientActivityReportMapper {
	List<ClientActivityReport> getAllReports(@Param("clientId") String clientId);
}
