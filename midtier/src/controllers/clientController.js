const express = require('express');
const router = express.Router();
const clientService = require('../services/clientService');
const logger = require('../utils/logger'); // Ensure you have a logger utility

// Verify Email
router.post('/verify-email', async (req, res) => {
    logger.info('Verifying email', req.body);
    
    try {
        const result = await clientService.verifyEmail(req.body);
        logger.info('Email verification successful', result);
        res.status(200).json(result);
    } catch (error) {
        logger.error('Error verifying email', error);
        res.status(error.statusCode || 500).json({ error: error.message });
    }
});

// Login
router.post('/login', async (req, res) => {
    logger.info('User login attempt', req.body);
    
    try {
        const result = await clientService.login(req.body);
        logger.info('Login successful', result);
        res.status(200).json(result);
    } catch (error) {
        logger.error('Error during login', error);
        res.status(error.statusCode || 500).json({ error: error.message });
    }
});

// Register
router.post('/register', async (req, res) => {
    logger.info('User registration attempt', req.body);
    
    try {
        const result = await clientService.register(req.body);
        logger.info('Registration successful', result);
        res.status(201).json(result);
    } catch (error) {
        logger.error('Error during registration', error);
        res.status(error.statusCode || 500).json({ error: error.message });
    }
});

// Get Holdings
router.get('/holdings/:id', async (req, res) => {
    const clientId = req.params.id;
    logger.info(`Fetching holdings for client ID: ${clientId}`);

    try {
        const holdings = await clientService.getClientHoldings(clientId);
        logger.info(`Holdings fetched successfully for client ID: ${clientId}`, holdings);
        res.status(holdings.length ? 200 : 204).json(holdings);
    } catch (error) {
        logger.error(`Error fetching holdings for client ID: ${clientId}`, error);
        res.status(error.statusCode || 500).json({ error: error.message });
    }
});

// Get Live Prices
router.get('/live-prices', async (req, res) => {
    logger.info('Fetching live prices');

    try {
        const livePrices = await clientService.getLivePrices();
        logger.info('Live prices fetched successfully', livePrices);
        res.status(200).json(livePrices);
    } catch (error) {
        logger.error('Error fetching live prices', error);
        res.status(error.statusCode || 500).json({ error: error.message });
    }
});

module.exports = router;
