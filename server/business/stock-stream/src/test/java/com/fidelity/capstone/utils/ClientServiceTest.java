package com.fidelity.capstone.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.capstone.stock_stream.Client;

public class ClientServiceTest {

    private ClientService clientService;
    private FmtsService mockFMTSService;

    @BeforeEach
    public void setUp() {
        mockFMTSService = new FmtsService();
        clientService = new ClientService(mockFMTSService);
    }

    @Test
    public void testVerifyEmailAddress_EmailExists() {
        Client client = new Client("test@example.com", "password123", "John Doe", "1990-01-01", "IN", "123456", "ID123", "clientId123");
        clientService.registerClient(client);

        assertTrue(clientService.verifyEmailAddress("test@example.com"));
    }

    @Test
    public void testVerifyEmailAddress_EmailDoesNotExist() {
        assertFalse(clientService.verifyEmailAddress("nonexistent@example.com"));
    }

    @Test
    public void testRegisterClient_Success() {
        Client client = new Client("test@example.com", "password123", "John Doe", "1990-01-01", "IN", "123456", "ID123", "clientId123");
        assertTrue(clientService.registerClient(client));
    }

    @Test
    public void testRegisterClient_EmailAlreadyRegistered() {
        Client client = new Client("test@example.com", "password123", "John Doe", "1990-01-01", "IN", "123456", "ID123", "clientId123");
        clientService.registerClient(client);
        assertFalse(clientService.registerClient(client)); // Email already registered
    }

    @Test
    public void testLoginClient_Success() {
        Client client = new Client("test@example.com", "password123", "John Doe", "1990-01-01", "IN", "123456", "ID123", "clientId123");
        clientService.registerClient(client);
        assertTrue(clientService.loginClient("test@example.com", "password123"));
    }

    @Test
    public void testLoginClient_WrongPassword() {
        Client client = new Client("test@example.com", "password123", "John Doe", "1990-01-01", "IN", "123456", "ID123", "clientId123");
        clientService.registerClient(client);
        assertFalse(clientService.loginClient("test@example.com", "wrongPassword"));
    }

    @Test
    public void testLoginClient_WrongEmail() {
        Client client = new Client("test@example.com", "password123", "John Doe", "1990-01-01", "IN", "123456", "ID123", "clientId123");
        clientService.registerClient(client);
        assertFalse(clientService.loginClient("nonexistent@example.com", "password123"));
    }
}


