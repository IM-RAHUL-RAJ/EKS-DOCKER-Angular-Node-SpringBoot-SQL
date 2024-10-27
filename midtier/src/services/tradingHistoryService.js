const axios = require('axios');
const CircuitBreaker = require('opossum');

// Create a circuit breaker for the trade history service
const breakerOptions = {
    timeout: 5000,
    errorThresholdPercentage: 50,
    resetTimeout: 10000
};

class TradeHistoryService {
    constructor() {
        this.tradeHistoryCircuitBreaker = new CircuitBreaker(this.getClientTradingHistory.bind(this), breakerOptions);
    }

    // Function to fetch trading history for a specific client ID
    async getClientTradingHistory(clientId) {
        try {
            const url = `http://localhost:8080/stock_stream/trades/trade_history/${clientId}`;
            const response = await axios.get(url);

            if (response.status === 200) {
                return response.data;
            } else {
                throw new Error(`No trading history found for client ID: ${clientId}`);
            }
        } catch (err) {
            console.error(`Error fetching trade history for client ID: ${clientId}`, err.message);
            throw err;
        }
    }
}

module.exports = TradeHistoryService;
