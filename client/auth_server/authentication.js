const fs = require('fs');
const path = require('path');

const usersFilePath = path.join(__dirname, './data/users.json');

// Helper function to load users
function loadUsers() {
    const data = fs.readFileSync(usersFilePath, 'utf8');
    return JSON.parse(data).users;
}

// Helper function to save users
function saveUsers(users) {
    const data = JSON.stringify({ users }, null, 2);
    fs.writeFileSync(usersFilePath, data, 'utf8');
}

// Authentication logic
function authenticateUser(email, password) {
    const users = loadUsers();
    const user = users[email];
    if (user && user.password === password) {
        return { success: true, user };
    }
    return { success: false, message: 'Invalid email or password' };
}

function registerUser(newUser) {
    const users = loadUsers();
    if (users[newUser.email]) {
        return { success: false, message: 'Email already exists' };
    }
    users[newUser.email] = newUser;
    saveUsers(users);
    return { success: true, user: newUser };
}

module.exports = {
    authenticateUser,
    registerUser
};
