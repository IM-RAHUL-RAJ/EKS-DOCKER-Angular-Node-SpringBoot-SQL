const axios = require('axios');
const CircuitBreaker = require('opossum');

const API_BASE_URL = 'http://localhost:8080/api/clients';

class ClientService {
    constructor() {
        const breakerOptions = {
            timeout: 5000,  // If the request takes longer than 5 seconds, trigger failure
            errorThresholdPercentage: 50,  // Trip the breaker if 50% of requests fail
            resetTimeout: 10000  // After 10 seconds, try again
        };

        // Create Circuit Breakers for each type of request
        this.circuitBreakers = {
            verifyEmail: new CircuitBreaker(this.verifyEmail.bind(this), breakerOptions),
            login: new CircuitBreaker(this.login.bind(this), breakerOptions),
            register: new CircuitBreaker(this.register.bind(this), breakerOptions),
            getClientHoldings: new CircuitBreaker(this.getClientHoldings.bind(this), breakerOptions),
            getLivePrices: new CircuitBreaker(this.getLivePrices.bind(this), breakerOptions)
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

    async verifyEmail(emailDTO) {
        try {
            const response = await axios.post(`${API_BASE_URL}/verify-email`, emailDTO);
            return response.data;
        } catch (error) {
            console.error(`Error verifying email: ${error.message}`);
            throw error;
        }
    }

    async login(client) {
        try {
            const response = await axios.post(`${API_BASE_URL}/login`, client);
            return response.data;
        } catch (error) {
            console.error(`Error logging in: ${error.message}`);
            throw error;
        }
    }

    async register(client) {
        try {
            const response = await axios.post(`${API_BASE_URL}/register`, client);
            return response.data;
        } catch (error) {
            console.error(`Error registering client: ${error.message}`);
            throw error;
        }
    }

    async getClientHoldings(id) {
        try {
            const response = await axios.get(`${API_BASE_URL}/holdings/${id}`);
            return response.data;
        } catch (error) {
            console.error(`Error fetching client holdings: ${error.message}`);
            throw error;
        }
    }

    async getLivePrices() {
        try {
            const response = await axios.get(`${API_BASE_URL}/live-prices`);
            return response.data;
        } catch (error) {
            console.error(`Error fetching live prices: ${error.message}`);
            throw error;
        }
    }

    // Handlers that fire the circuit breakers
    get handler() {
        return {
            verifyEmail: emailDTO => this.circuitBreakers.verifyEmail.fire(emailDTO),
            login: client => this.circuitBreakers.login.fire(client),
            register: client => this.circuitBreakers.register.fire(client),
            getClientHoldings: id => this.circuitBreakers.getClientHoldings.fire(id),
            getLivePrices: () => this.circuitBreakers.getLivePrices.fire()
        };
    }
}

module.exports = new ClientService();
