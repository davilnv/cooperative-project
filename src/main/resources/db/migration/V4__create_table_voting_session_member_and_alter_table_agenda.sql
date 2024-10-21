CREATE TABLE VOTING_SESSION_MEMBER
(
    VOTING_SESSION_ID UUID       NOT NULL,
    MEMBER_ID         UUID       NOT NULL,
    MEMBER_VOTE       VARCHAR(3) NOT NULL,
    VOTE_DATE_TIME    TIMESTAMP  NOT NULL,
    PRIMARY KEY (VOTING_SESSION_ID, MEMBER_ID),
    FOREIGN KEY (VOTING_SESSION_ID) REFERENCES VOTING_SESSION (ID),
    FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (ID)
);

ALTER TABLE AGENDA
    ADD COLUMN VOTES_YES INT DEFAULT 0;
ALTER TABLE AGENDA
    ADD COLUMN VOTES_NO INT DEFAULT 0;