package com.capstone.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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


	@Test
    public void testAddClient_Success() throws SQLException {
        Client client = new Client("testUser", "testPassword");
        
        dao.addClient(client);       
        verify(preparedStatement).setString(1, "testUser");
        verify(preparedStatement).setString(2, "testPassword");
        verify(preparedStatement).executeUpdate();
    }

}
