package com.capstone.integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capstone.exceptions.DatabaseException;
import com.capstone.models.Client;

public class ClientDaoImpl implements ClientDao{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private DataSource dataSource;
	
	public ClientDaoImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	@Override
	public boolean verifyEmailAddress(String emailAddress) {
		final String checkEmailQuery = "SELECT COUNT(*) as count FROM clients WHERE email = ?";
		
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

	@Override
	public void addClient(Client client) throws SQLException {
		if (client.getEmail() == null || client.getPassword() == null || client.getFullName() == null) {
	        throw new DatabaseException("Cannot add user: Required fields are missing");
	    }
		String sql = "INSERT INTO clients (email, password, fullName, dateOfBirth, country, postalCode, identificationType, identificationNumber clientId) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		Connection connection = dataSource.getConnection();			
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
			    statement.setString(1, client.getEmail());
			    statement.setString(2, client.getPassword());
	            statement.setString(3, client.getFullName());
	            statement.setString(4, client.getDateOfBirth());
	            statement.setString(5, client.getCountry());
	            statement.setString(6, client.getPostalCode());
	            statement.setString(7, client.getIdentificationType());
	            statement.setString(7, client.getIdentificationNumber());
	            statement.setString(8, client.getClientId());           
	            statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DatabaseException("Cannot add user",e);
		}
	}
			
	@Override
	public boolean verifyLogin(String username, String password) throws SQLException {
			if ( password == null ||  password.isEmpty()) {
			throw new IllegalArgumentException(" password cannot be null or empty");
		}
		String sql = "SELECT * FROM clients WHERE username = ? AND password = ?";
		Connection connection = dataSource.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if a matching record is found
        } catch (SQLException e) {
            throw new SQLException("Error verifying client login", e);
        }
    }
}
