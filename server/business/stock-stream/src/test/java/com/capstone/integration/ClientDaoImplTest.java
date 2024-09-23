package com.capstone.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.capstone.exceptions.DatabaseException;
import com.capstone.models.Client;

class ClientDaoImplTest {
	private static DataSource dataSource;
	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;
	private ClientDaoImpl dao;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		dataSource = mock(DataSource.class);
		connection = mock(Connection.class);
		preparedStatement = mock(PreparedStatement.class);
		resultSet = mock(ResultSet.class);
	}


	@BeforeEach
	void setUp() throws Exception {
		dao = new ClientDaoImpl(dataSource);
		when(dataSource.getConnection()).thenReturn(connection);
	}
	
	@AfterEach
    public void tearDown() throws SQLException {
        // Clean up resources
        if (resultSet != null) resultSet.close();
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
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
	public void testVerifyEmailAddress_Fails() throws SQLException {
	    String email = "nonexistent@example.com"; // This email does not exist in the database

	    // Mocking the behavior for a non-existing email
	    when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
	    when(preparedStatement.executeQuery()).thenReturn(resultSet);
	    when(resultSet.next()).thenReturn(true); // Simulate a result set with a row
	    when(resultSet.getInt("count")).thenReturn(0); // Simulate that the count is 0, meaning the email doesn't exist

	    // Call the method and assert the result
	    boolean result = dao.verifyEmailAddress(email);
	    assertFalse(result);

	    // Verify interactions
	    verify(preparedStatement).setString(1, email);
	    verify(preparedStatement, times(1)).executeQuery(); // Ensure executeQuery is called only once
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


	@Test
    public void testAddClient_Success() throws SQLException {
        Client client = new Client("test@example.com", "password123", "Test User", "1990-01-01", "Country", "12345", "ID123", "ClientId123");
        
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulate successful insert

        assertDoesNotThrow(() -> dao.addClient(client));
        
    }

    @Test
    public void testAddClient_DuplicateEmail() throws SQLException {
        Client client = new Client("test@example.com", "password123", "Test User", "1990-01-01", "Country", "12345", "ID123", "ClientId123");
        
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Duplicate entry"));

        DatabaseException exception = assertThrows(DatabaseException.class, () -> dao.addClient(client));
        assertEquals("Cannot add user", exception.getMessage());
    }

    @Test
    public void testAddClient_MissingRequiredFields() {
        Client client = new Client(null, "password123", "Test User", "1990-01-01", "Country", "12345", "ID123", "ClientId123");

        DatabaseException exception = assertThrows(DatabaseException.class, () -> dao.addClient(client));
        assertEquals("Cannot add user: Required fields are missing", exception.getMessage());
    }

    @Test
    public void testVerifyLogin_Success() throws SQLException {
        String username = "testUser";
        String password = "password123";

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Simulate failed login

        boolean result = dao.verifyLogin(username, password);
        assertTrue(result);
    }

    @Test
    public void testVerifyLogin_InvalidUsername() throws SQLException {
        String username = "wrongUser";
        String password = "password123";

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Simulate failed login

        boolean result = dao.verifyLogin(username, password);
        assertFalse(result);
    }

    @Test
    public void testVerifyLogin_SQLException() throws SQLException {
        String username = "testUser";
        String password = "password123";

        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException("Connection error"));

        assertThrows(SQLException.class, () -> dao.verifyLogin(username, password));
    }

    

}
