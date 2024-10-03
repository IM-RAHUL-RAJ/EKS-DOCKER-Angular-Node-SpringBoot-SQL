package com.capstone.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.exceptions.DatabaseException;
import com.capstone.integration.mapper.ClientMapper;
import com.capstone.models.Client;
import com.capstone.models.ProfileStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:beans.xml")
@Transactional
class ClientMyBatisImplementaionTest {
	
	@Autowired
	ClientMapper clientMapper;

	@Autowired
	private ClientMyBatisImplementaion dao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void test_verifyEmailAddress_succeeds() throws SQLException {
		String email = "paul.wilson@example.com";

		assertTrue(dao.verifyEmailAddress(email));

	}

	@Test
	public void testVerifyEmailAddress_Fails() throws SQLException {
		String email = "nonexistent@example.com"; // This email does not exist in the database
		boolean result = dao.verifyEmailAddress(email);
		assertFalse(result);
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
	public void testVerifyLogin_Success() throws SQLException {
		String clientId = "C005";
		String password = "admin123";

		boolean result = dao.verifyLogin(clientId, password);
		assertTrue(result);
	}

	@Test
	public void testVerifyLogin_InvalidLogin() throws SQLException {
		String clientId = "wrongUser";
		String password = "password123";

		boolean result = dao.verifyLogin(clientId, password);
		assertFalse(result);
	}

	 @Test
	    public void testAddClient_Success() {
	        Client client = new Client("test@example.com", "password123", "Test User", "22-MAR-85", "Country", "12345", "PAN", "ID1236780", ProfileStatus.COMPLETE,"ClientId1236745");
	        assertDoesNotThrow(() -> dao.addClient(client));
	    }

	 @Test
	    public void testAddClient_MissingRequiredFields() {
	        Client client = new Client(null, "password123", "Test User", "22-MAR-85", "Country", "12345", "PAN", "ID123", ProfileStatus.COMPLETE, "ClientId123");
	        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dao.addClient(client));
	        assertEquals("Cannot add user: Required fields are missing", exception.getMessage());
	    }
	 
	 @Test
	    public void testAddClient_DuplicateEmail() throws SQLException {
	        Client client = new Client("linda.brown@example.com", "password123", "Test User", "22-MAR-85", "Country", "12345", "PAN", "ID123", ProfileStatus.PENDING, "ClientId123");
	        
	        assertThrows(DuplicateKeyException.class, () -> dao.addClient(client));
	    }


}
