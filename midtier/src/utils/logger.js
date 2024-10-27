const winston = require('winston');
const path = require('path');
const fs = require('fs');

// Create the logs directory if it doesn't exist
const logDir = path.join(__dirname, '../logs'); // Adjust path as needed

if (!fs.existsSync(logDir)) {
    fs.mkdirSync(logDir, { recursive: true }); // Create logs directory
}

// Create a logger instance
const logger = winston.createLogger({
    level: 'info',
    format: winston.format.combine(
        winston.format.timestamp(),
        winston.format.json()
    ),
    transports: [
        new winston.transports.Console(), // Log to the console
        new winston.transports.File({ 
            filename: path.join(logDir, 'combined.log') // Log to combined.log in logs directory
        }) 
    ],
});

module.exports = logger;
