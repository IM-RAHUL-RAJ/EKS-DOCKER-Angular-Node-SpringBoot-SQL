package com.capstone.integration;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.integration.mapper.ClientMapper;
import com.capstone.models.Client;
import com.capstone.models.ProfileStatus;

@SpringBootTest
@Transactional
class ClientMyBatisImplementaionTest {
	
	@Autowired
	ClientMapper clientMapper;

	@Autowired
	private ClientMyBatisImplementaion dao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void test_verifyEmailAddress_succeeds() {
		String email = "paul.wilson@example.com";

		assertTrue(dao.isEmailUnique(email));

	}

	@Test
	public void testVerifyEmailAddress_Fails() {
		String email = "nonexistent@example.com"; // This email does not exist in the database
		boolean result = dao.isEmailUnique(email);
		assertFalse(result);
	}

	@Test
	void test_verifyEmailAddress_emptyEmail() {
		String email = "";
		assertThrows(IllegalArgumentException.class, () -> dao.isEmailUnique(email));
	}

	@Test
	void test_verifyEmailAddress_nullEmail() {
		String email = null;
		assertThrows(IllegalArgumentException.class, () -> dao.isEmailUnique(email));
	}

//	@Test
//	public void testVerifyLogin_Success(){
//		String clientId = "C005";
//		String password = "admin123";
//
//		boolean result = dao.verifyLogin(clientId, password);
//		assertTrue(result);
//	}

	@Test
	public void testVerifyLogin_InvalidLogin() {
		String clientId = "wrongUser";
		String password = "password123";

		boolean result = dao.verifyLogin(clientId, password);
		assertFalse(result);
	}

	 @Test
	    public void testAddClient_Success() throws SQLException {
	        Client client = new Client("test@example.com", "password123", "Test User", "22-MAR-85", "Country", "12345", "PAN", "ID1236780", ProfileStatus.COMPLETE,"C111");
	        assertDoesNotThrow(() -> dao.save(client));
	        assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"ss_client", "client_id = 'C111'"));
	    }

	 @Test
	    public void testAddClient_MissingRequiredFields() {
	        Client client = new Client(null, "password123", "Test User", "22-MAR-85", "Country", "12345", "PAN", "ID123", ProfileStatus.COMPLETE, "ClientId123");
	        IllegalsArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dao.addClient(client));
	        assertEquals("Cannot add user: Required fields are missing", exception.getMessage());
	        assertEquals(0,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"ss_client", "client_id = 'ClientId123'"));

	    }
	 
	 @Test
	    public void testAddClient_DuplicateEmail() throws SQLException {
	        Client client = new Client("linda.brown@example.com", "password123", "Test User", "22-MAR-85", "Country", "12345", "PAN", "ID123", ProfileStatus.PENDING, "ClientId123");
	        
	        assertThrows(DuplicateKeyException.class, () -> dao.addClient(client));
	        assertEquals(0,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"ss_client", "client_id = 'ClientId123'"));

	    }


}
