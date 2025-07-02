
CREATE TABLE IF NOT EXISTS "otps" (
	"id" BIGINT NOT NULL,
	"value" INTEGER NOT NULL,
	"state" VARCHAR NOT NULL,
	PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "operations" (
	"id" BIGINT NOT NULL,
	"otp_id" BIGINT NOT NULL,
	"description" VARCHAR NULL DEFAULT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "otp_id__otps_id" FOREIGN KEY ("otp_id") REFERENCES "otps" ("id")
);

CREATE TABLE IF NOT EXISTS "otp_conf" (
	"lifetime" INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS "otp_states" (
	"state" VARCHAR NOT NULL,
	PRIMARY KEY ("state")
);

CREATE TABLE IF NOT EXISTS "users" (
	"login" VARCHAR NOT NULL,
	"spass" VARCHAR NOT NULL,
	"role" VARCHAR NULL DEFAULT NULL,
	PRIMARY KEY ("login")
);

