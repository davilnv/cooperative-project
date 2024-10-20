CREATE TABLE AGENDA
(
    ID              UUID        NOT NULL PRIMARY KEY,
    TITLE           VARCHAR(60) NOT NULL,
    DESCRIPTION     VARCHAR(255),
    STATUS          VARCHAR(20) NOT NULL,
    START_DATE_TIME TIMESTAMP,
    END_DATE_TIME   TIMESTAMP
);