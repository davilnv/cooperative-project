CREATE TABLE "agenda" (
  "id" uuid PRIMARY KEY,
  "title" varchar(60),
  "description" varchar(255),
  "status" varchar(20),
  "start_date_time" timestamp,
  "end_date_time" timestamp,
  "voting_session_id" uuid
);

CREATE TABLE "voting_session" (
  "id" uuid PRIMARY KEY,
  "open_date_time" timestamp,
  "close_date_time" timestamp
);

CREATE TABLE "member" (
  "id" uuid PRIMARY KEY,
  "cpf" varchar(11),
  "name" varchar(150),
  "birth_date" date
);

CREATE TABLE "voting_session_member" (
  "voting_session_id" uuid,
  "member_id" uniqueidentifier,
  PRIMARY KEY ("voting_session_id", "member_id")
);

ALTER TABLE "voting_session" ADD FOREIGN KEY ("id") REFERENCES "agenda" ("voting_session_id");

ALTER TABLE "voting_session_member" ADD FOREIGN KEY ("voting_session_id") REFERENCES "voting_session" ("id");

ALTER TABLE "member" ADD FOREIGN KEY ("id") REFERENCES "voting_session_member" ("member_id");
