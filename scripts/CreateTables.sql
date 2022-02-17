DROP TABLE if exists user;
DROP TABLE if exists person;
DROP TABLE if exists event;
DROP TABLE if exists auth_token;

CREATE TABLE user
(
	username varchar(255) NOT NULL PRIMARY KEY,
	password varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	firstName varchar(255) NOT NULL,
	lastName varchar(255) NOT NULL,
	gender char(1) NOT NULL,
	personID varchar(255) NOT NULL,
	FOREIGN KEY(personID) REFERENCES person(personID)
);

CREATE TABLE person
(
	personID varchar(255) NOT NULL PRIMARY KEY,
	associatedUsername varchar(255) NOT NULL,
	firstName varchar(255) NOT NULL,
	lastName varchar(255) NOT NULL,
	gender CHAR(1) NOT NULL,
	fatherID varchar(255) NULL,
	motherID varchar(255) NULL,
	spouseID varchar(255) NULL,
	FOREIGN KEY(associatedUsername) REFERENCES user(username)
);

CREATE TABLE event
(
	eventID varchar(255) NOT NULL PRIMARY KEY,
	associatedUsername varchar(255),
	personID varchar(255),
	latitude float,
	longitude float,
	country varchar(255),
	city varchar(255),
	eventType varchar(255) NOT NULL,
	year INTEGER,
	FOREIGN KEY(associatedUsername) REFERENCES user(username),
	FOREIGN KEY(personID) REFERENCES person(personID)
);

CREATE TABLE auth_token
(
	authtoken varchar(255) NOT NULL PRIMARY KEY,
	username varchar(255),
	FOREIGN KEY(username) REFERENCES user(username)
);







