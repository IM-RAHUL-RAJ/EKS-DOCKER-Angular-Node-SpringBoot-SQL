drop table SS_CLIENT;
CREATE TABLE SS_CLIENT (
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    fullName VARCHAR(100),
    dateOfBirth DATE,
    country VARCHAR(100),
    postalCode VARCHAR(20),
    identificationType VARCHAR(50),
    identificationNumber VARCHAR(50),
	profileStatus VARCHAR(50),
    clientId VARCHAR(50) PRIMARY KEY
);
INSERT ALL
	INTO SS_CLIENT (email, password, fullName, dateOfBirth, country, postalCode, identificationType, identificationNumber, profileStatus, clientId) 
	VALUES ('john.doe@example.com', 'password123', 'John Doe', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'USA', '90210', 'SSN', 'ID123456', 'Complete', 'C001')
	INTO SS_CLIENT (email, password, fullName, dateOfBirth, country, postalCode, identificationType, identificationNumber, profileStatus, clientId) 
	VALUES ('jane.smith@example.com', 'mypassword', 'Jane Smith', TO_DATE('1985-03-22', 'YYYY-MM-DD'), 'India', 'H2Y1A1','PAN', 'ID654321', 'Pending','C002')
	INTO SS_CLIENT (email, password, fullName, dateOfBirth, country, postalCode, identificationType, identificationNumber, profileStatus, clientId) 
    VALUES ('mark.jones@example.com', 'securepass', 'Mark Jones', TO_DATE('1992-11-30', 'YYYY-MM-DD'), 'UK', 'SW1A2AA', 'UTR','ID789012', 'Complete','C003')
	INTO SS_CLIENT (email, password, fullName, dateOfBirth, country, postalCode, identificationType, identificationNumber, profileStatus, clientId) 
    VALUES ('linda.brown@example.com', 'hello123', 'Linda Brown', TO_DATE('1988-07-19', 'YYYY-MM-DD'), 'Australia', '2000', 'TFN','ID345678', 'Pending', 'C004')
	INTO SS_CLIENT (email, password, fullName, dateOfBirth, country, postalCode, identificationType, identificationNumber, profileStatus, clientId) 
    VALUES ('paul.wilson@example.com', 'admin123', 'Paul Wilson', TO_DATE('1975-12-01', 'YYYY-MM-DD'), 'India', '6011', 'PAN', 'ID567890', 'Complete', 'C005')
	INTO SS_CLIENT (email, password, fullName, dateOfBirth, country, postalCode, identificationType, identificationNumber, profileStatus, clientId) 
    VALUES ('emma.johnson@example.com', 'qwertyuiop', 'Emma Johnson', TO_DATE('1995-02-28', 'YYYY-MM-DD'), 'Ireland', 'D02ABC', 'PPS', 'ID246810', 'Pending', 'C006')
	INTO SS_CLIENT (email, password, fullName, dateOfBirth, country, postalCode, identificationType, identificationNumber, profileStatus,clientId) 
    VALUES ('michael.white@example.com', 'passw0rd', 'Michael White', TO_DATE('1980-08-14', 'YYYY-MM-DD'), 'USA', '10001','SSN','ID135791', 'Complete', 'C007')
	INTO SS_CLIENT (email, password, fullName, dateOfBirth, country, postalCode, identificationType, identificationNumber, profileStatus, clientId) 
    VALUES ('susan.lee@example.com', 'myp@ssw0rd', 'Susan Lee', TO_DATE('1993-09-09', 'YYYY-MM-DD'), 'UK', 'E1 6AN', 'UTR', 'ID987654', 'Pending', 'C008')
	INTO SS_CLIENT (email, password, fullName, dateOfBirth, country, postalCode, identificationType, identificationNumber, profileStatus,clientId) 
    VALUES ('david.kim@example.com', 'password1', 'David Kim', TO_DATE('1987-04-05', 'YYYY-MM-DD'), 'USA', '06000', 'SSN','ID543216', 'Complete', 'C009')
	INTO SS_CLIENT (email, password, fullName, dateOfBirth, country, postalCode, identificationType, identificationNumber, profileStatus,clientId) 
    VALUES ('olivia.martin@example.com', 'abc12345', 'Olivia Martin', TO_DATE('1991-10-10', 'YYYY-MM-DD'), 'USA', '33101', 'SSN','ID102938', 'Pending', 'C010')
SELECT * FROM dual;
COMMIT;