package com.capstone.integration.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Param;

import com.capstone.models.Client;

public interface ClientMapper {
	int verifyEmailAddress(String emailAddress);

	void addClient(Client client) throws SQLException;

	int verifyLogin(@Param("clientId") String clientId, @Param("password") String password) throws SQLException;
}
