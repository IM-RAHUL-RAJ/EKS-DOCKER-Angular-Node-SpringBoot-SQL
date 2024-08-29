const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const { authenticateUser, registerUser } = require('./authentication');

const app = express();
const port = 3001;

app.use(bodyParser.json());
app.use(cors());

// Login route
app.post('/login', (req, res) => {
    const { email, password } = req.body;

    if (!email || !password) {
        return res.status(400).json({ message: 'Email and password are required' });
    }

    const result = authenticateUser(email, password);
    if (result.success) {
        res.status(200).json({ message: 'Login successful', user: result.user, clientId: result.user.clientId });
    } else {
        res.status(401).json({ message: result.message });
    }
});

// Registration route
app.post('/register', (req, res) => {
    const { email, password, fullName, dateOfBirth, country, postalCode, identification, clientId } = req.body;

    if (!email || !password || !fullName || !dateOfBirth || !country || !postalCode || !identification || !clientId) {
        return res.status(400).json({ message: 'All fields are required' });
    }

    const result = registerUser(req.body);
    if (result.success) {
        res.status(201).json({ message: 'Registration successful', user: result.user });
    } else {
        res.status(400).json({ message: result.message });
    }
});


app.listen(port, () => {
    console.log(`Server running on http://localhost:${port}`);
});
