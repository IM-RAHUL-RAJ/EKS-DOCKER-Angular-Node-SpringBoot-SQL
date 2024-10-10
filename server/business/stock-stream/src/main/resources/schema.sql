-- Schema definition for presidents

-- drop table
DROP TABLE presidents;

-- create new table
CREATE TABLE presidents
   ("PRESIDENT_ID" integer PRIMARY KEY,
	"FIRST_NAME" VARCHAR(45) NOT NULL,
	"LAST_NAME" VARCHAR(45) NOT NULL,
	"START_YEAR" integer NOT NULL,
	"END_YEAR" integer NOT NULL,
	"IMAGE_PATH" VARCHAR(80),
	"BIO" VARCHAR(2048) NOT NULL
   );

