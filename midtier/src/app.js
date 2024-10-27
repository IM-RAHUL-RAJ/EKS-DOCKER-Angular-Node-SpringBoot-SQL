const express = require('express');
const cors = require('cors');
const investmentPreferenceController = require('./controllers/investmentPreferenceController');
const tradingHistoryRestController = require('./controllers/tradingHistoryRestController');
const clientActivityReportController = require('./controllers/clientActivityReportController');
const clientController = require('./controllers/clientController'); // Import the client controller

const app = express();
const port = process.env.PORT || 3030;

app.use(express.json());
app.use(cors());

// Define routes
app.use('/api/investment_preference', investmentPreferenceController);
app.use('/api/trades', tradingHistoryRestController);
app.use('/api/clients', clientController);
app.use('/api/clients/reports', clientActivityReportController);


app.listen(port, () => {
    console.log(`Server running on port ${port}`);
});
