package com.capstone.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.integration.ClientMybatisHoldingsDaoImpl;
import com.capstone.models.Client;
import com.capstone.models.ProfileStatus;

public class ClientServiceTest {

	@Autowired
    private ClientMybatisHoldingsDaoImpl clientHoldingsDao;
	
	private ClientService clientService;
	private FmtsService mockFMTSService;

	@BeforeEach
	public void setUp() {
		mockFMTSService = new FmtsService();
		clientService = new ClientService(mockFMTSService);
		Client client = new Client("test1@example.com", "password1234", "John Doe", "1990-01-01", "IN", "123456","PAN",
				"ID123",  ProfileStatus.COMPLETE, "clientId12356");
		clientService.registerClient(client);
	}

	@Test
	public void testVerifyEmailAddress_EmailExists() {
		Client client = new Client("test@example.com", "password123", "John Doe", "1990-01-01", "IN", "123456", "PAN","ID123", ProfileStatus.COMPLETE,
				"clientId123");
		clientService.registerClient(client);

		assertTrue(clientService.verifyEmailAddress("test@example.com"));
	}

	@Test
	public void testVerifyEmailAddress_EmailDoesNotExist() {
		assertFalse(clientService.verifyEmailAddress("nonexistent@example.com"));
	}

	@Test
	public void testRegisterClient_Success() {
		Client client = new Client("test@example.com", "password123", "John Doe", "1990-01-01", "IN", "123456", "PAN", "ID123",
				 ProfileStatus.COMPLETE, "clientId123");
		assertTrue(clientService.registerClient(client));
	}

	@Test
	public void testRegisterClient_EmailAlreadyRegistered() {
		Client client = new Client("test@example.com", "password123", "John Doe", "1990-01-01", "IN", "123456", "PAN","ID123", ProfileStatus.COMPLETE,
				"clientId123");
		clientService.registerClient(client);
		assertFalse(clientService.registerClient(client)); // Email already registered
	}

	@Test
	public void testLoginClient_Success() {
		Client client = new Client("test@example.com", "password123", "John Doe", "1990-01-01", "IN", "123456", "PAN","ID123",
				 ProfileStatus.COMPLETE,"clientId123");
		clientService.registerClient(client);
		assertTrue(clientService.loginClient("test@example.com", "password123"));
	}

	@Test
	public void testLoginClient_AlreadyLoggedIn() {
		Client client = new Client("test@example.com", "password123", "John Doe", "1990-01-01", "IN", "123456", "PAN", "ID123",
				 ProfileStatus.COMPLETE,"clientId123");
		clientService.registerClient(client);
		clientService.loginClient("test@example.com", "password123");
		assertFalse(clientService.loginClient("test@example.com", "wrongPassword"));
	}

	@Test
	public void testLoginClient_WrongPassword() {
		Client client = new Client("test@example.com", "password123", "John Doe", "1990-01-01", "IN", "123456", "PAN", "ID123",
				 ProfileStatus.COMPLETE,"clientId123");
		clientService.registerClient(client);
		assertFalse(clientService.loginClient("test@example.com", "wrongPassword"));
	}

	@Test
	public void testLoginClient_WrongEmail() {
		Client client = new Client("test@example.com", "password123", "John Doe", "1990-01-01", "IN", "123456", "PAN", "ID123",
				 ProfileStatus.COMPLETE,"clientId123");
		clientService.registerClient(client);
		assertFalse(clientService.loginClient("nonexistent@example.com", "password123"));
	}

	@Test
	public void isUserLoggedInToBeTrue() {
		Client client = new Client("test@example.com", "password123", "John Doe", "1990-01-01", "IN", "123456", "PAN", "ID123",
				 ProfileStatus.COMPLETE,"clientId123");
		clientService.registerClient(client);
		clientService.loginClient("test@example.com", "password123");
		assertTrue(clientService.isUserLoggedIn("clientId123"));
	}

	@Test
	public void isUserLoggedInToBeFalse() {
		Client client = new Client("test@example.com", "password123", "John Doe", "1990-01-01", "IN", "123456", "PAN", "ID123",
				 ProfileStatus.COMPLETE,"clientId123");
		clientService.registerClient(client);
		assertFalse(clientService.isUserLoggedIn("clientId123"));
	}
}
