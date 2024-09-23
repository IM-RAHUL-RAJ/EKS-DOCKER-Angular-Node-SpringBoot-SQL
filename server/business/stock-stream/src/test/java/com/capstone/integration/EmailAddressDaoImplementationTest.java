package com.capstone.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.capstone.exceptions.DatabaseException;

class EmailAddressDaoImplementationTest {
	private static DataSource dataSource;
	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;
	private EmailAddressDaoImplementation dao;

	@BeforeAll
	static void init() throws IOException {
		dataSource = mock(DataSource.class);
		connection = mock(Connection.class);
		preparedStatement = mock(PreparedStatement.class);
		resultSet = mock(ResultSet.class);
	}

	@BeforeEach
	void setUp() throws SQLException {
		dao = new EmailAddressDaoImplementation(dataSource);
		when(dataSource.getConnection()).thenReturn(connection);
	}

	@Test
	void test_verifyEmailAddress_succeeds() throws SQLException {
		String email = "paul.wilson@example.com";
		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true);
		when(resultSet.getInt("count")).thenReturn(1);

		assertTrue(dao.verifyEmailAddress(email));

		// Verify that executeQuery was called the expected number of times
		verify(preparedStatement, times(1)).setString(1, email);
		verify(preparedStatement, atLeastOnce()).executeQuery(); // Adjust based on how many times it should be called
	}

	@Test
	void test_verifyEmailAddress_fails() throws SQLException {
		String email = "dont.exist@example.com";

		// Mocking the behavior for a non-existing email
		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true);
		when(resultSet.getInt("count")).thenReturn(0); // Simulate non-existing email

		assertFalse(dao.verifyEmailAddress(email));

		// Verify interactions
		verify(preparedStatement).setString(1, email);
		verify(preparedStatement).executeQuery();
	}

	@Test
	void test_verifyEmailAddress_emptyEmail() throws SQLException {
		String email = "";
		assertThrows(IllegalArgumentException.class, () -> dao.verifyEmailAddress(email));
	}

	@Test
	void test_verifyEmailAddress_nullEmail() {
		String email = null;
		assertThrows(IllegalArgumentException.class, () -> dao.verifyEmailAddress(email));
	}

}
