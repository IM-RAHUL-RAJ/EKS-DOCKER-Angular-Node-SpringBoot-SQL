DROP TABLE SS_TRADES;
DROP TABLE SS_ORDERS;
DROP TABLE SS_PRICE;
DROP TABLE SS_INSTRUMENTS;
--DROP TABLE SS_CLIENT;
--
--CREATE TABLE SS_CLIENT (
--    CLIENT_ID VARCHAR(100) NOT NULL PRIMARY KEY,
--    CLIENT_NAME VARCHAR(100) NOT NULL,
--    CONTACT_INFO VARCHAR(100),
--    CREATED_AT TIMESTAMP NOT NULL
--);

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
    CLIENT_ID VARCHAR(100) NOT NULL,
    INSTRUMENT_ID VARCHAR(100) NOT NULL,
    QUANTITY INT NOT NULL,
    TARGET_PRICE NUMERIC(6, 2) NOT NULL,
    DIRECTION VARCHAR(1) NOT NULL,
    STATUS NUMERIC(1, 0) NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL,
    FOREIGN KEY (INSTRUMENT_ID) REFERENCES SS_INSTRUMENTS(INSTRUMENT_ID),
    FOREIGN KEY (CLIENT_ID) REFERENCES SS_CLIENT(CLIENT_ID)  
);

CREATE TABLE SS_TRADES (
    TRADE_ID VARCHAR(100) NOT NULL PRIMARY KEY,
    CLIENT_ID VARCHAR(100) NOT NULL,
    ORDER_ID VARCHAR(100) NOT NULL,
    INSTRUMENT_ID VARCHAR(100) NOT NULL,
    QUANTITY INT NOT NULL,
    EXECUTION_PRICE NUMERIC(6, 2) NOT NULL,
    DIRECTION VARCHAR(1) NOT NULL CHECK (DIRECTION IN ('B', 'S')),  
    EXECUTED_AT TIMESTAMP NOT NULL,
    FOREIGN KEY (CLIENT_ID) REFERENCES SS_CLIENT(CLIENT_ID), 
    FOREIGN KEY (ORDER_ID) REFERENCES SS_ORDERS(ORDER_ID),    
    FOREIGN KEY (INSTRUMENT_ID) REFERENCES SS_INSTRUMENTS(INSTRUMENT_ID) 
);

INSERT INTO SS_CLIENT (CLIENT_ID, CLIENT_NAME, CONTACT_INFO, CREATED_AT) VALUES ('C001', 'Client A', 'contactA@example.com', TO_TIMESTAMP('2023-01-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_CLIENT (CLIENT_ID, CLIENT_NAME, CONTACT_INFO, CREATED_AT) VALUES ('C002', 'Client B', 'contactB@example.com', TO_TIMESTAMP('2023-01-02 11:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_CLIENT (CLIENT_ID, CLIENT_NAME, CONTACT_INFO, CREATED_AT) VALUES ('C003', 'Client C', 'contactC@example.com', TO_TIMESTAMP('2023-01-03 12:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_CLIENT (CLIENT_ID, CLIENT_NAME, CONTACT_INFO, CREATED_AT) VALUES ('C004', 'Client D', 'contactD@example.com', TO_TIMESTAMP('2023-01-04 13:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_CLIENT (CLIENT_ID, CLIENT_NAME, CONTACT_INFO, CREATED_AT) VALUES ('C005', 'Client E', 'contactE@example.com', TO_TIMESTAMP('2023-01-05 14:00:00', 'YYYY-MM-DD HH24:MI:SS'));

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

INSERT INTO SS_ORDERS (ORDER_ID, CLIENT_ID, INSTRUMENT_ID, QUANTITY, TARGET_PRICE, DIRECTION, STATUS, CREATED_AT) VALUES ('O001', 'C001', 'I001', 10, 145.00, 'B', 1, TO_TIMESTAMP('2023-01-01 20:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_ORDERS (ORDER_ID, CLIENT_ID, INSTRUMENT_ID, QUANTITY, TARGET_PRICE, DIRECTION, STATUS, CREATED_AT) VALUES ('O002', 'C002', 'I002', 20, 245.00, 'S', 2, TO_TIMESTAMP('2023-01-02 21:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_ORDERS (ORDER_ID, CLIENT_ID, INSTRUMENT_ID, QUANTITY, TARGET_PRICE, DIRECTION, STATUS, CREATED_AT) VALUES ('O003', 'C003', 'I003', 30, 345.00, 'B', 0, TO_TIMESTAMP('2023-01-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_ORDERS (ORDER_ID, CLIENT_ID, INSTRUMENT_ID, QUANTITY, TARGET_PRICE, DIRECTION, STATUS, CREATED_AT) VALUES ('O004', 'C004', 'I004', 40, 445.00, 'S', 1, TO_TIMESTAMP('2023-01-04 23:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_ORDERS (ORDER_ID, CLIENT_ID, INSTRUMENT_ID, QUANTITY, TARGET_PRICE, DIRECTION, STATUS, CREATED_AT) VALUES ('O005', 'C005', 'I005', 50, 545.00, 'B', 2, TO_TIMESTAMP('2023-01-05 00:00:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO SS_TRADES (TRADE_ID, CLIENT_ID, ORDER_ID, INSTRUMENT_ID, QUANTITY, EXECUTION_PRICE, DIRECTION, EXECUTED_AT) VALUES ('T001', 'C001', 'O001', 'I001', 10, 145.00, 'B', TO_TIMESTAMP('2023-01-01 21:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_TRADES (TRADE_ID, CLIENT_ID, ORDER_ID, INSTRUMENT_ID, QUANTITY, EXECUTION_PRICE, DIRECTION, EXECUTED_AT) VALUES ('T002', 'C002', 'O002', 'I002', 20, 245.00, 'S', TO_TIMESTAMP('2023-01-02 22:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_TRADES (TRADE_ID, CLIENT_ID, ORDER_ID, INSTRUMENT_ID, QUANTITY, EXECUTION_PRICE, DIRECTION, EXECUTED_AT) VALUES ('T003', 'C003', 'O003', 'I003', 30, 345.00, 'B', TO_TIMESTAMP('2023-01-03 23:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_TRADES (TRADE_ID, CLIENT_ID, ORDER_ID, INSTRUMENT_ID, QUANTITY, EXECUTION_PRICE, DIRECTION, EXECUTED_AT) VALUES ('T004', 'C004', 'O004', 'I004', 40, 445.00, 'S', TO_TIMESTAMP('2023-01-04 00:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO SS_TRADES (TRADE_ID, CLIENT_ID, ORDER_ID, INSTRUMENT_ID, QUANTITY, EXECUTION_PRICE, DIRECTION, EXECUTED_AT) VALUES ('T005', 'C005', 'O005', 'I005', 50, 545.00, 'B', TO_TIMESTAMP('2023-01-05 01:00:00', 'YYYY-MM-DD HH24:MI:SS'));

COMMIT;