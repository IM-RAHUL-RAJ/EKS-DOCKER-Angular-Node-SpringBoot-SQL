const express = require('express');
const TradeHistoryService = require('../services/tradingHistoryService');
const router = express.Router();
const logger = require('../utils/logger'); // Make sure to create a logger utility

const tradeHistoryService = new TradeHistoryService();

// Endpoint to get trading history for a specific client ID
router.get('/trade_history/:clientId', async (req, res) => {
    const { clientId } = req.params;
    logger.info(`Fetching trading history for client ID: ${clientId}`);

    try {
        const tradeHistory = await tradeHistoryService.tradeHistoryCircuitBreaker.fire(clientId);

        if (tradeHistory && tradeHistory.length > 0) {
            logger.info(`Trading history found for client ID: ${clientId}`, tradeHistory);
            res.status(200).json(tradeHistory);
        } else {
            logger.warn(`No trading history found for client ID: ${clientId}`);
            res.status(404).json({ message: `No Trade History Found for client ID: ${clientId}` });
        }
    } catch (error) {
        logger.error(`Error fetching trading history for client ID: ${clientId}`, error);
        res.status(500).json({ message: error.message });
    }
});

module.exports = router;
