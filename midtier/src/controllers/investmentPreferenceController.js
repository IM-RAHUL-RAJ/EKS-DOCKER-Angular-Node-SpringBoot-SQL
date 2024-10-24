const express = require('express');
const router = express.Router();
const investmentPreferenceService = require('../services/investmentPreferenceService');
const logger = require('../utils/logger'); // Ensure you have a logger utility

// Get investment preference by clientId
router.get('/:clientId', async (req, res) => {
    const clientId = req.params.clientId;
    logger.info(`Fetching investment preference for client ID: ${clientId}`);

    try {
        const data = await investmentPreferenceService.handler.getById(clientId);
        logger.info(`Investment preference fetched successfully for client ID: ${clientId}`, data);
        res.status(200).json(data);
    } catch (error) {
        logger.error(`Error fetching investment preference for client ID: ${clientId}`, error);
        res.status(500).json({ message: error.message });
    }
});

// Create a new investment preference
router.post('/', async (req, res) => {
    logger.info('Creating a new investment preference', req.body);

    try {
        const data = await investmentPreferenceService.handler.create(req.body);
        logger.info('Investment preference created successfully', data);
        res.status(201).json(data);
    } catch (error) {
        logger.error('Error creating investment preference', error);
        res.status(500).json({ message: error.message });
    }
});

// Update an investment preference
router.put('/', async (req, res) => {
    logger.info('Updating investment preference', req.body);

    try {
        const data = await investmentPreferenceService.handler.update(req.body);
        logger.info('Investment preference updated successfully', data);
        res.status(200).json(data);
    } catch (error) {
        logger.error('Error updating investment preference', error);
        res.status(500).json({ message: error.message });
    }
});

// Delete an investment preference by clientId
router.delete('/:clientId', async (req, res) => {
    const clientId = req.params.clientId;
    logger.info(`Deleting investment preference for client ID: ${clientId}`);

    try {
        const data = await investmentPreferenceService.handler.delete(clientId);
        logger.info(`Investment preference deleted successfully for client ID: ${clientId}`, data);
        res.status(200).json(data);
    } catch (error) {
        logger.error(`Error deleting investment preference for client ID: ${clientId}`, error);
        res.status(500).json({ message: error.message });
    }
});

module.exports = router;
