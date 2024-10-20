CREATE TABLE VOTING_SESSION
(
    ID              UUID NOT NULL PRIMARY KEY,
    OPEN_DATE_TIME  TIMESTAMP,
    CLOSE_DATE_TIME TIMESTAMP
);

ALTER TABLE AGENDA
    ADD COLUMN VOTING_SESSION_ID UUID REFERENCES VOTING_SESSION (ID);