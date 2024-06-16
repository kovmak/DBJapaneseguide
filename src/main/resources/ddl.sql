DROP TABLE IF EXISTS Title;
DROP TABLE IF EXISTS Client;
DROP TABLE IF EXISTS Authors;
DROP TABLE IF EXISTS Story;
DROP TABLE IF EXISTS users;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE Story (
                           id              UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
                           name            VARCHAR(255)    NOT NULL,
                           description     TEXT            NOT NULL
);

CREATE TABLE Authors (
                        id              UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
                        name            VARCHAR(64)     NOT NULL,
                        address         TEXT            NOT NULL UNIQUE
);

CREATE TABLE Title (
                          id              UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
                          name            VARCHAR(255)    NOT NULL,
                          description     TEXT            NOT NULL
);

CREATE TABLE Client (
                        id              UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
                        name            VARCHAR(64)     NOT NULL,
                        phone           VARCHAR(64)     NOT NULL UNIQUE,
                        address         TEXT            NOT NULL UNIQUE
);

CREATE TABLE users (
                       id              UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
                       login           VARCHAR(32)     NOT NULL UNIQUE,
                       password        VARCHAR(255)    NOT NULL,
                       role            VARCHAR(20)     CHECK (role IN ('admin', 'moder', 'client')) NOT NULL,
                       name            VARCHAR(64)     NOT NULL
);


