CREATE TABLE clients (
    client_id VARCHAR(255),
    name VARCHAR2(100) NOT NULL,
    email_address VARCHAR2(100) NOT NULL UNIQUE,
    password VARCHAR2(16) NOT NULL,
    CONSTRAINT client_id PRIMARY KEY(client_id) 
);

CREATE TABLE investment_preferences (
    client_id VARCHAR(255),
    investment_purpose VARCHAR(50) NOT NULL,
    investment_purpose_description VARCHAR(255),
    risk_tolerance VARCHAR(50) NOT NULL,
    income_category VARCHAR(50) NOT NULL,
    investment_year VARCHAR(50) NOT NULL,
    is_robo_advisor_terms_accepted NUMBER(1) CHECK (is_robo_advisor_terms_accepted IN (0, 1)),
    PRIMARY KEY (client_id),
    FOREIGN KEY (client_id) REFERENCES Clients(client_id)
);


INSERT INTO clients (client_id,name,email_address,password) VALUES ('abcabcab','Ryu','ryu@gmail.com','ryu224');
INSERT INTO clients (client_id,name,email_address,password) VALUES ('abcdabcd','Ryugen','ryugen@gmail.com','ryugen224');

INSERT INTO investment_preferences 
(client_id,investment_purpose,investment_purpose_description,risk_tolerance,income_category,investment_year,is_robo_advisor_terms_accepted)
VALUES
('abcabcab','College Fund','Saving for higher education expenses','Conservative','Below $20,000','0 - 5 years',1);

INSERT INTO investment_preferences 
(client_id,investment_purpose,investment_purpose_description,risk_tolerance,income_category,investment_year,is_robo_advisor_terms_accepted)
VALUES
('abcdabcd','Retirement','Saving for retirement years','Average','$40,000 - $60,000','5 - 7 years',1);

SELECT * FROM investment_preferences WHERE client_id='abcabcab';


