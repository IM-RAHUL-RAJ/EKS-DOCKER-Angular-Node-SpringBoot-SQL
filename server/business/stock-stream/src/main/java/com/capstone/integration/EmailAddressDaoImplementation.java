package com.capstone.integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capstone.exceptions.DatabaseException;

public class EmailAddressDaoImplementation implements EmailAddressDao {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private DataSource dataSource;

	public EmailAddressDaoImplementation(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	final String checkEmailQuery = "SELECT COUNT(*) as count FROM clients WHERE email = ?";

	@Override
	public boolean verifyEmailAddress(String emailAddress) {
		
		if (emailAddress == null || emailAddress.isEmpty()) {
			throw new IllegalArgumentException("Email cannot be null or empty");
		}

		try {
			Connection connection = dataSource.getConnection();
			try (PreparedStatement stmt = connection.prepareStatement(checkEmailQuery)) {

				stmt.setString(1, emailAddress);
				ResultSet resultSet = stmt.executeQuery();

				if (resultSet.next()) {
					int count = resultSet.getInt("count");
					return count > 0;
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DatabaseException("Cannot verify emailAddress" + checkEmailQuery, e);

		}
		return false;
	}

}
