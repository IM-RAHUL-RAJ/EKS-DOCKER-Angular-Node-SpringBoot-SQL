const express = require('express');
const ClientActivityReportService = require('../services/clientActivityReportService');
const router = express.Router();
const logger = require('../utils/logger'); // Make sure to create a logger utility

const clientActivityReportService = new ClientActivityReportService();

// Enable CORS for a specific origin
const cors = require('cors');
router.use(cors({ origin: 'http://localhost:4200' }));

// Endpoint to get client activity reports for a specific client ID
router.get('/:clientId', async (req, res) => {
    const { clientId } = req.params;
    logger.info(`Fetching client activity reports for client ID: ${clientId}`);

    try {
        const reports = await clientActivityReportService.getClientActivityReportsBreaker.fire(clientId);
        logger.info(`Reports fetched: ${reports}`);

        if (reports.length === 0) {
            logger.info(`No content for client ID: ${clientId}`);
            return res.status(204).send(); // No Content
        }

        res.status(200).json(reports);
    } catch (error) {
        if (error.response && error.response.status === 400) {
            logger.error(`Invalid client ID: ${clientId}`, error);
            return res.status(400).send(); // Bad Request
        } else {
            logger.error(`Error fetching client activity reports for client ID: ${clientId}`, error);
            return res.status(500).send(); // Internal Server Error
        }
    }
});

module.exports = router;
