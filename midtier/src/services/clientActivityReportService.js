const axios = require('axios');
const CircuitBreaker = require('opossum');

class ClientActivityReportService {
    constructor() {
        // Define Circuit Breaker options
        const breakerOptions = {
            timeout: 5000, // Timeout after 5 seconds
            errorThresholdPercentage: 50, // Open circuit if 50% of requests fail
            resetTimeout: 10000, // Wait 10 seconds before trying again
        };

        // Create a Circuit Breaker for the getClientActivityReports method
        this.getClientActivityReportsBreaker = new CircuitBreaker(
            this.getClientActivityReports.bind(this),
            breakerOptions
        );

        // Optional: Setup events for the Circuit Breaker
        this.getClientActivityReportsBreaker.on('open', () => {
            console.warn('Circuit Breaker for getClientActivityReports is open.');
        });

        this.getClientActivityReportsBreaker.on('close', () => {
            console.info('Circuit Breaker for getClientActivityReports is closed.');
        });

        this.getClientActivityReportsBreaker.on('fallback', () => {
            console.warn('Fallback for getClientActivityReports activated.');
        });
    }

    // Function to fetch client activity reports for a specific client ID
    async getClientActivityReports(clientId) {
        try {
            const url = `http://localhost:8080/api/clients/reports/${clientId}`; // Update URL as needed
            const response = await axios.get(url);

            if (response.status === 200) {
                return response.data;
            } else {
                throw new Error(`No reports found for client ID: ${clientId}`);
            }
        } catch (err) {
            console.error(`Error fetching reports for client ID: ${clientId}`, err.message);
            throw err;
        }
    }
}

module.exports = ClientActivityReportService;
