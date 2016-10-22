DROP SEQUENCE IF EXISTS hibernate_sequence;
CREATE SEQUENCE hibernate_sequence;

DROP TABLE IF EXISTS customer;

CREATE TABLE customers (
	id integer primary key,
	name varchar(100)
);