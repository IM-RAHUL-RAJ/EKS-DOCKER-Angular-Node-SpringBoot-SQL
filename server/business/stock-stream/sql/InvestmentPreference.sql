--CREATE TABLE clients (
--    client_id VARCHAR(255),
--    name VARCHAR2(100) NOT NULL,
--    email_address VARCHAR2(100) NOT NULL UNIQUE,
--    password VARCHAR2(16) NOT NULL,
--    CONSTRAINT client_id PRIMARY KEY(client_id) 
--);

DROP TABLE investment_preferences;

CREATE TABLE investment_preferences (
    clientId VARCHAR(255),
    investmentPurpose VARCHAR(50) NOT NULL,
    investmentPurposeDescription VARCHAR(255),
    riskTolerance VARCHAR(50) NOT NULL,
    incomeCategory VARCHAR(50) NOT NULL,
    investmentYear VARCHAR(50) NOT NULL,
    isRoboAdvisorTermsAccepted NUMBER(1) CHECK (isRoboAdvisorTermsAccepted IN (0, 1)),
    PRIMARY KEY (clientId),
    FOREIGN KEY (clientId) REFERENCES SS_CLIENT(clientId)
);


--INSERT INTO clients (client_id,name,email_address,password) VALUES ('abcabcab','Ryu','ryu@gmail.com','ryu224');
--INSERT INTO clients (client_id,name,email_address,password) VALUES ('abcdabcd','Ryugen','ryugen@gmail.com','ryugen224');

INSERT INTO investment_preferences 
(clientId,investmentPurpose,investmentPurposeDescription,riskTolerance,incomeCategory,investmentYear,isRoboAdvisorTermsAccepted)
VALUES
('C001','COLLEGE_FUND','Saving for higher education expenses','CONSERVATIVE','BELOW_20000','ZERO_TO_FIVE',1);

INSERT INTO investment_preferences 
(clientId,investmentPurpose,investmentPurposeDescription,riskTolerance,incomeCategory,investmentYear,isRoboAdvisorTermsAccepted)
VALUES
('C002','RETIREMENT','Saving for retirement years','AVERAGE','FROM_40000_TO_60000','FIVE_TO_SEVEN',1);

COMMIT




