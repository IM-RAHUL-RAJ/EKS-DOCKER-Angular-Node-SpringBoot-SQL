const axios = require('axios');
const CircuitBreaker = require('opossum');

const API_BASE_URL = 'http://localhost:8080/stock_stream/investment_preference';

class InvestmentPreferenceService {
    constructor() {
        const breakerOptions = {
            timeout: 5000,  // If the request takes longer than 5 seconds, trigger failure
            errorThresholdPercentage: 50,  // Trip the breaker if 50% of requests fail
            resetTimeout: 10000  // After 10 seconds, try again
        };

        // Create Circuit Breakers for each type of request
        this.circuitBreakers = {
            getById: new CircuitBreaker(this.getInvestmentPreferenceById.bind(this), breakerOptions),
            create: new CircuitBreaker(this.createInvestmentPreference.bind(this), breakerOptions),
            update: new CircuitBreaker(this.updateInvestmentPreference.bind(this), breakerOptions),
            delete: new CircuitBreaker(this.deleteInvestmentPreference.bind(this), breakerOptions)
        };

        this.setupCircuitBreakerEvents();
    }

    setupCircuitBreakerEvents() {
        Object.keys(this.circuitBreakers).forEach(action => {
            const breaker = this.circuitBreakers[action];

            breaker.on('open', () => console.log(`Circuit breaker for ${action} is open!`));
            breaker.on('close', () => console.log(`Circuit breaker for ${action} is closed!`));
            breaker.on('halfOpen', () => console.log(`Circuit breaker for ${action} is half-open.`));
            breaker.on('fallback', () => console.log(`Fallback triggered for ${action}`));

            breaker.fallback(() => {
                throw new Error({ statusCode: 503, message: `Service unavailable for action: ${action}` });
            });
        });
    }

    // Get investment preference by ID
    async getInvestmentPreferenceById(clientId) {
        try {
            const response = await axios.get(`${API_BASE_URL}/${clientId}`);
            return response.data;
        } catch (error) {
            console.error(`Error fetching investment preference: ${error.message}`);
            throw error;
        }
    }

    // Create a new investment preference
    async createInvestmentPreference(investmentPreference) {
        try {
            const response = await axios.post(API_BASE_URL, investmentPreference);
            return response.data;
        } catch (error) {
            console.error(`Error creating investment preference: ${error.message}`);
            throw error;
        }
    }

    // Update an existing investment preference
    async updateInvestmentPreference(investmentPreference) {
        try {
            const response = await axios.put(API_BASE_URL, investmentPreference);
            return response.data;
        } catch (error) {
            console.error(`Error updating investment preference: ${error.message}`);
            throw error;
        }
    }

    // Delete an investment preference by ID
    async deleteInvestmentPreference(clientId) {
        try {
            const response = await axios.delete(`${API_BASE_URL}/${clientId}`);
            return response.data;
        } catch (error) {
            console.error(`Error deleting investment preference: ${error.message}`);
            throw error;
        }
    }

    // Handlers that fire the circuit breakers
    get handler() {
        return {
            getById: clientId => this.circuitBreakers.getById.fire(clientId),
            create: investmentPreference => this.circuitBreakers.create.fire(investmentPreference),
            update: investmentPreference => this.circuitBreakers.update.fire(investmentPreference),
            delete: clientId => this.circuitBreakers.delete.fire(clientId)
        };
    }
}

module.exports = new InvestmentPreferenceService();
