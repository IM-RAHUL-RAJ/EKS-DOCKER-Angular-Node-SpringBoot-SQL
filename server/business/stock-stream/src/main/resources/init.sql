
DROP TABLE SS_TRADES;
DROP TABLE SS_ORDERS;
DROP TABLE SS_PRICE;
DROP TABLE SS_INSTRUMENTS;
DROP TABLE investment_preferences;
DROP TABLE HOLDINGS;
drop table SS_CLIENT;

CREATE TABLE SS_CLIENT (
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    date_of_birth DATE,
    country VARCHAR(100),
    postal_code VARCHAR(20),
    identification_type VARCHAR(50),
    identification_number VARCHAR(50),
	profile_status VARCHAR(50),
    client_id VARCHAR(50) PRIMARY KEY
);
INSERT ALL
	INTO SS_CLIENT (email, password, full_name, date_of_birth, country, postal_code, identification_type, identification_number, profile_status, client_id) 
	VALUES ('john.doe@example.com', 'password123', 'John Doe', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'USA', '90210', 'SSN', 'ID123456', 'COMPLETE', 'C001')
	INTO SS_CLIENT (email, password, full_name, date_of_birth, country, postal_code, identification_type, identification_number, profile_status, client_id) 
	VALUES ('jane.smith@example.com', 'mypassword', 'Jane Smith', TO_DATE('1985-03-22', 'YYYY-MM-DD'), 'India', 'H2Y1A1','PAN', 'ID654321', 'PENDING','C002')
	INTO SS_CLIENT (email, password, full_name, date_of_birth, country, postal_code, identification_type, identification_number, profile_status, client_id) 
    VALUES ('mark.jones@example.com', 'securepass', 'Mark Jones', TO_DATE('1992-11-30', 'YYYY-MM-DD'), 'UK', 'SW1A2AA', 'UTR','ID789012', 'COMPLETE','C003')
	INTO SS_CLIENT (email, password, full_name, date_of_birth, country, postal_code, identification_type, identification_number, profile_status, client_id) 
    VALUES ('linda.brown@example.com', 'hello123', 'Linda Brown', TO_DATE('1988-07-19', 'YYYY-MM-DD'), 'Australia', '2000', 'TFN','ID345678', 'PENDING', 'C004')
	INTO SS_CLIENT (email, password, full_name, date_of_birth, country, postal_code, identification_type, identification_number, profile_status, client_id) 
    VALUES ('paul.wilson@example.com', 'admin123', 'Paul Wilson', TO_DATE('1975-12-01', 'YYYY-MM-DD'), 'India', '6011', 'PAN', 'ID567890', 'COMPLETE', 'C005')
	INTO SS_CLIENT (email, password, full_name, date_of_birth, country, postal_code, identification_type, identification_number, profile_status, client_id) 
    VALUES ('emma.johnson@example.com', 'qwertyuiop', 'Emma Johnson', TO_DATE('1995-02-28', 'YYYY-MM-DD'), 'Ireland', 'D02ABC', 'PPS', 'ID246810', 'PENDING', 'C006')
	INTO SS_CLIENT (email, password, full_name, date_of_birth, country, postal_code, identification_type, identification_number, profile_status,client_id) 
    VALUES ('michael.white@example.com', 'passw0rd', 'Michael White', TO_DATE('1980-08-14', 'YYYY-MM-DD'), 'USA', '10001','SSN','ID135791', 'COMPLETE', 'C007')
	INTO SS_CLIENT (email, password, full_name, date_of_birth, country, postal_code, identification_type, identification_number, profile_status, client_id) 
    VALUES ('susan.lee@example.com', 'myp@ssw0rd', 'Susan Lee', TO_DATE('1993-09-09', 'YYYY-MM-DD'), 'UK', 'E1 6AN', 'UTR', 'ID987654', 'PENDING', 'C008')
	INTO SS_CLIENT (email, password, full_name, date_of_birth, country, postal_code, identification_type, identification_number, profile_status,client_id) 
    VALUES ('david.kim@example.com', 'password1', 'David Kim', TO_DATE('1987-04-05', 'YYYY-MM-DD'), 'USA', '06000', 'SSN','ID543216', 'COMPLETE', 'C009')
	INTO SS_CLIENT (email, password, full_name, date_of_birth, country, postal_code, identification_type, identification_number, profile_status,client_id) 
    VALUES ('olivia.martin@example.com', 'abc12345', 'Olivia Martin', TO_DATE('1991-10-10', 'YYYY-MM-DD'), 'USA', '33101', 'SSN','ID102938', 'PENDING', 'C010')
SELECT * FROM dual;

CREATE TABLE investment_preferences (
    client_id VARCHAR(255),
    investment_purpose VARCHAR(50) NOT NULL,
    investment_purpose_description VARCHAR(255),
    risk_tolerance VARCHAR(50) NOT NULL,
    income_category VARCHAR(50) NOT NULL,
    investment_year VARCHAR(50) NOT NULL,
    is_robo_advisor_terms_accepted NUMBER(1) CHECK (is_robo_advisor_terms_accepted IN (0, 1)),
    PRIMARY KEY (client_id),
    FOREIGN KEY (client_id) REFERENCES SS_CLIENT(client_id)
);
 
INSERT INTO investment_preferences 
(client_id,investment_purpose,investment_purpose_description,risk_tolerance,income_category,investment_year,is_robo_advisor_terms_accepted)
VALUES
('C001','COLLEGE_FUND','Saving for higher education expenses','CONSERVATIVE','BELOW_20000','ZERO_TO_FIVE',1);
 
INSERT INTO investment_preferences 
(client_id,investment_purpose,investment_purpose_description,risk_tolerance,income_category,investment_year,is_robo_advisor_terms_accepted)
VALUES
('C002','RETIREMENT','Saving for retirement years','AVERAGE','FROM_40000_TO_60000','FIVE_TO_SEVEN',1);

CREATE TABLE HOLDINGS (
    INSTRUMENT VARCHAR2(255 BYTE) NOT NULL,
    INSTRUMENT_ID VARCHAR2(255 BYTE) NOT NULL,
    CLIENT_ID VARCHAR2(255 BYTE) NOT NULL,
    QUANTITY NUMBER(10, 0) NOT NULL,
    AVERAGE_PRICE NUMBER(10, 2) NOT NULL,
    INVESTED_CAPITAL NUMBER(15, 2) NOT NULL,
    LTP NUMBER(10, 2) NOT NULL,
    PERCENT_CHANGE NUMBER(5, 2) NOT NULL,
    PROFIT_LOSS NUMBER(15, 2) NOT NULL,
    DAY_CHANGE_PERCENT NUMBER(5, 2) NOT NULL,
    PRIMARY KEY (INSTRUMENT_ID, CLIENT_ID),
    FOREIGN KEY (CLIENT_ID) REFERENCES SS_CLIENT(CLIENT_ID)
);
 
 
INSERT INTO HOLDINGS (INSTRUMENT, INSTRUMENT_ID, CLIENT_ID, QUANTITY, AVERAGE_PRICE, INVESTED_CAPITAL, LTP, PERCENT_CHANGE, PROFIT_LOSS, DAY_CHANGE_PERCENT) VALUES
('Stock A', 'INST001', 'C001', 100, 150.50, 15050.00, 155.00, 3.00, 450.00, 2.50);
 
INSERT INTO HOLDINGS (INSTRUMENT, INSTRUMENT_ID, CLIENT_ID, QUANTITY, AVERAGE_PRICE, INVESTED_CAPITAL, LTP, PERCENT_CHANGE, PROFIT_LOSS, DAY_CHANGE_PERCENT) VALUES
('Stock B', 'INST002', 'C001', 50, 200.00, 10000.00, 195.00, -2.50, -250.00, -1.25);
 
INSERT INTO HOLDINGS (INSTRUMENT, INSTRUMENT_ID, CLIENT_ID, QUANTITY, AVERAGE_PRICE, INVESTED_CAPITAL, LTP, PERCENT_CHANGE, PROFIT_LOSS, DAY_CHANGE_PERCENT) VALUES
('Stock C', 'INST003', 'C002', 200, 75.00, 15000.00, 80.00, 6.67, 1000.00, 3.00);
 
INSERT INTO HOLDINGS (INSTRUMENT, INSTRUMENT_ID, CLIENT_ID, QUANTITY, AVERAGE_PRICE, INVESTED_CAPITAL, LTP, PERCENT_CHANGE, PROFIT_LOSS, DAY_CHANGE_PERCENT) VALUES
('Stock D', 'INST004', 'C002', 150, 120.00, 18000.00, 118.00, -1.67, -300.00, -0.83);
 
INSERT INTO HOLDINGS (INSTRUMENT, INSTRUMENT_ID, CLIENT_ID, QUANTITY, AVERAGE_PRICE, INVESTED_CAPITAL, LTP, PERCENT_CHANGE, PROFIT_LOSS, DAY_CHANGE_PERCENT) VALUES
('Stock E', 'INST005', 'C003', 80, 250.00, 20000.00, 260.00, 4.00, 800.00, 2.00);


CREATE TABLE SS_INSTRUMENTS (
    INSTRUMENT_ID VARCHAR(100) NOT NULL PRIMARY KEY,
    EXTERNAL_ID_TYPE VARCHAR(100) NOT NULL,
    EXTERNAL_ID VARCHAR(100) NOT NULL,
    CATEGORY_ID VARCHAR(100) NOT NULL,
    DESCRIPTION VARCHAR(100),  
    MAX_QUANTITY NUMERIC(6, 2) NOT NULL,
    MIN_QUANTITY NUMERIC(6, 2) NOT NULL
);

CREATE TABLE SS_PRICE (
    INSTRUMENT_ID VARCHAR(100) NOT NULL,
    ASKPRICE NUMERIC(6, 2) NOT NULL,
    BIDPRICE NUMERIC(6, 2) NOT NULL,
    UPDATED_AT TIMESTAMP NOT NULL,
    PRIMARY KEY (INSTRUMENT_ID),
    FOREIGN KEY (INSTRUMENT_ID) REFERENCES SS_INSTRUMENTS(INSTRUMENT_ID)
);

CREATE TABLE SS_ORDERS (
    ORDER_ID VARCHAR(100) NOT NULL PRIMARY KEY,
    client_id VARCHAR(100) NOT NULL,
    INSTRUMENT_ID VARCHAR(100) NOT NULL,
    QUANTITY INT NOT NULL,
    TARGET_PRICE NUMERIC(6, 2) NOT NULL,
    DIRECTION VARCHAR(1) NOT NULL,
    STATUS NUMERIC(1, 0) NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL,
    FOREIGN KEY (INSTRUMENT_ID) REFERENCES SS_INSTRUMENTS(INSTRUMENT_ID),
    FOREIGN KEY (client_id) REFERENCES SS_CLIENT(client_id)  
);

CREATE TABLE SS_TRADES (
    TRADE_ID VARCHAR(100) NOT NULL PRIMARY KEY,
    client_id VARCHAR(100) NOT NULL,
    ORDER_ID VARCHAR(100) NOT NULL,
    INSTRUMENT_ID VARCHAR(100) NOT NULL,
    QUANTITY INT NOT NULL,
    EXECUTION_PRICE NUMERIC(6, 2) NOT NULL,
    DIRECTION VARCHAR(1) NOT NULL CHECK (DIRECTION IN ('B', 'S')),  
    EXECUTED_AT TIMESTAMP NOT NULL,
    FOREIGN KEY (client_id) REFERENCES SS_CLIENT(client_id), 
    FOREIGN KEY (ORDER_ID) REFERENCES SS_ORDERS(ORDER_ID),    
    FOREIGN KEY (INSTRUMENT_ID) REFERENCES SS_INSTRUMENTS(INSTRUMENT_ID) 
);



INSERT INTO SS_INSTRUMENTS (INSTRUMENT_ID, EXTERNAL_ID_TYPE, EXTERNAL_ID, CATEGORY_ID, DESCRIPTION, MAX_QUANTITY, MIN_QUANTITY) VALUES ('I001', 'Type1', 'EID001', 'Cat1', 'Instrument 1', 100.00, 10.00);
INSERT INTO SS_INSTRUMENTS (INSTRUMENT_ID, EXTERNAL_ID_TYPE, EXTERNAL_ID, CATEGORY_ID, DESCRIPTION, MAX_QUANTITY, MIN_QUANTITY) VALUES ('I002', 'Type2', 'EID002', 'Cat2', 'Instrument 2', 200.00, 20.00);
INSERT INTO SS_INSTRUMENTS (INSTRUMENT_ID, EXTERNAL_ID_TYPE, EXTERNAL_ID, CATEGORY_ID, DESCRIPTION, MAX_QUANTITY, MIN_QUANTITY) VALUES ('I003', 'Type3', 'EID003', 'Cat3', 'Instrument 3', 300.00, 30.00);
INSERT INTO SS_INSTRUMENTS (INSTRUMENT_ID, EXTERNAL_ID_TYPE, EXTERNAL_ID, CATEGORY_ID, DESCRIPTION, MAX_QUANTITY, MIN_QUANTITY) VALUES ('I004', 'Type4', 'EID004', 'Cat4', 'Instrument 4', 400.00, 40.00);
INSERT INTO SS_INSTRUMENTS (INSTRUMENT_ID, EXTERNAL_ID_TYPE, EXTERNAL_ID, CATEGORY_ID, DESCRIPTION, MAX_QUANTITY, MIN_QUANTITY) VALUES ('I005', 'Type5', 'EID005', 'Cat5', 'Instrument 5', 500.00, 50.00);

INSERT INTO SS_PRICE (INSTRUMENT_ID, ASKPRICE, BIDPRICE, UPDATED_AT) VALUES ('I001', 150.00, 140.00, TO_TIMESTAMP('2023-01-01 15:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_PRICE (INSTRUMENT_ID, ASKPRICE, BIDPRICE, UPDATED_AT) VALUES ('I002', 250.00, 240.00, TO_TIMESTAMP('2023-01-02 16:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_PRICE (INSTRUMENT_ID, ASKPRICE, BIDPRICE, UPDATED_AT) VALUES ('I003', 350.00, 340.00, TO_TIMESTAMP('2023-01-03 17:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_PRICE (INSTRUMENT_ID, ASKPRICE, BIDPRICE, UPDATED_AT) VALUES ('I004', 450.00, 440.00, TO_TIMESTAMP('2023-01-04 18:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_PRICE (INSTRUMENT_ID, ASKPRICE, BIDPRICE, UPDATED_AT) VALUES ('I005', 550.00, 540.00, TO_TIMESTAMP('2023-01-05 19:00:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO SS_ORDERS (ORDER_ID, client_id, INSTRUMENT_ID, QUANTITY, TARGET_PRICE, DIRECTION, STATUS, CREATED_AT) VALUES ('O001', 'C001', 'I001', 10, 145.00, 'B', 1, TO_TIMESTAMP('2023-01-01 20:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_ORDERS (ORDER_ID, client_id, INSTRUMENT_ID, QUANTITY, TARGET_PRICE, DIRECTION, STATUS, CREATED_AT) VALUES ('O002', 'C002', 'I002', 20, 245.00, 'S', 2, TO_TIMESTAMP('2023-01-02 21:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_ORDERS (ORDER_ID, client_id, INSTRUMENT_ID, QUANTITY, TARGET_PRICE, DIRECTION, STATUS, CREATED_AT) VALUES ('O003', 'C003', 'I003', 30, 345.00, 'B', 0, TO_TIMESTAMP('2023-01-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_ORDERS (ORDER_ID, client_id, INSTRUMENT_ID, QUANTITY, TARGET_PRICE, DIRECTION, STATUS, CREATED_AT) VALUES ('O004', 'C004', 'I004', 40, 445.00, 'S', 1, TO_TIMESTAMP('2023-01-04 23:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_ORDERS (ORDER_ID, client_id, INSTRUMENT_ID, QUANTITY, TARGET_PRICE, DIRECTION, STATUS, CREATED_AT) VALUES ('O005', 'C005', 'I005', 50, 545.00, 'B', 2, TO_TIMESTAMP('2023-01-05 00:00:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO SS_TRADES (TRADE_ID, client_id, ORDER_ID, INSTRUMENT_ID, QUANTITY, EXECUTION_PRICE, DIRECTION, EXECUTED_AT) VALUES ('T001', 'C001', 'O001', 'I001', 10, 145.00, 'B', TO_TIMESTAMP('2023-01-01 21:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_TRADES (TRADE_ID, client_id, ORDER_ID, INSTRUMENT_ID, QUANTITY, EXECUTION_PRICE, DIRECTION, EXECUTED_AT) VALUES ('T002', 'C002', 'O002', 'I002', 20, 245.00, 'S', TO_TIMESTAMP('2023-01-02 22:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_TRADES (TRADE_ID, client_id, ORDER_ID, INSTRUMENT_ID, QUANTITY, EXECUTION_PRICE, DIRECTION, EXECUTED_AT) VALUES ('T003', 'C003', 'O003', 'I003', 30, 345.00, 'B', TO_TIMESTAMP('2023-01-03 23:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_TRADES (TRADE_ID, client_id, ORDER_ID, INSTRUMENT_ID, QUANTITY, EXECUTION_PRICE, DIRECTION, EXECUTED_AT) VALUES ('T004', 'C004', 'O004', 'I004', 40, 445.00, 'S', TO_TIMESTAMP('2023-01-04 00:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_TRADES (TRADE_ID, client_id, ORDER_ID, INSTRUMENT_ID, QUANTITY, EXECUTION_PRICE, DIRECTION, EXECUTED_AT) VALUES ('T005', 'C005', 'O005', 'I005', 50, 545.00, 'B', TO_TIMESTAMP('2023-01-05 01:00:00', 'YYYY-MM-DD HH24:MI:SS'));

COMMIT;