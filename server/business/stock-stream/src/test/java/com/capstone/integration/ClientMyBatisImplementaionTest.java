package com.capstone.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:beans.xml")
@Transactional
class ClientMyBatisImplementaionTest {
	

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
	


}
