DROP TABLE HOLDINGS;


CREATE TABLE "SCOTT"."HOLDINGS" (
    "INSTRUMENT" VARCHAR2(255 BYTE) NOT NULL,
    "INSTRUMENTID" VARCHAR2(255 BYTE) NOT NULL,
    "CLIENTID" VARCHAR2(255 BYTE) NOT NULL,
    "QUANTITY" NUMBER(10, 0) NOT NULL,
    "AVERAGEPRICE" NUMBER(10, 2) NOT NULL,
    "INVESTEDCAPITAL" NUMBER(15, 2) NOT NULL,
    "LTP" NUMBER(10, 2) NOT NULL,
    "PERCENTCHANGE" NUMBER(5, 2) NOT NULL,
    "PROFITLOSS" NUMBER(15, 2) NOT NULL,
    "DAYCHANGEPERCENT" NUMBER(5, 2) NOT NULL,
    PRIMARY KEY ("INSTRUMENTID", "clientId"),
    FOREIGN KEY (clientId) REFERENCES SS_CLIENT(clientId)
);



INSERT INTO "SCOTT"."HOLDINGS" (INSTRUMENT, INSTRUMENTID, clientId, QUANTITY, AVERAGEPRICE, INVESTEDCAPITAL, LTP, PERCENTCHANGE, PROFITLOSS, DAYCHANGEPERCENT) VALUES
('Stock A', 'INST001', 'C001', 100, 150.50, 15050.00, 155.00, 3.00, 450.00, 2.50);
INSERT INTO "SCOTT"."HOLDINGS" (INSTRUMENT, INSTRUMENTID, clientId, QUANTITY, AVERAGEPRICE, INVESTEDCAPITAL, LTP, PERCENTCHANGE, PROFITLOSS, DAYCHANGEPERCENT) VALUES
('Stock B', 'INST002', 'C001', 50, 200.00, 10000.00, 195.00, -2.50, -250.00, -1.25);
INSERT INTO "SCOTT"."HOLDINGS" (INSTRUMENT, INSTRUMENTID, clientId, QUANTITY, AVERAGEPRICE, INVESTEDCAPITAL, LTP, PERCENTCHANGE, PROFITLOSS, DAYCHANGEPERCENT) VALUES
('Stock C', 'INST003', 'C002', 200, 75.00, 15000.00, 80.00, 6.67, 1000.00, 3.00);
INSERT INTO "SCOTT"."HOLDINGS" (INSTRUMENT, INSTRUMENTID, clientId, QUANTITY, AVERAGEPRICE, INVESTEDCAPITAL, LTP, PERCENTCHANGE, PROFITLOSS, DAYCHANGEPERCENT) VALUES
('Stock D', 'INST004', 'C002', 150, 120.00, 18000.00, 118.00, -1.67, -300.00, -0.83);
INSERT INTO "SCOTT"."HOLDINGS" (INSTRUMENT, INSTRUMENTID, clientId, QUANTITY, AVERAGEPRICE, INVESTEDCAPITAL, LTP, PERCENTCHANGE, PROFITLOSS, DAYCHANGEPERCENT) VALUES
('Stock E', 'INST005', 'C003', 80, 250.00, 20000.00, 260.00, 4.00, 800.00, 2.00);

Commit;
