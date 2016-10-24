DROP SEQUENCE IF EXISTS customers_id_seq;

CREATE SEQUENCE customers_id_seq;

DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
	id serial primary key,
	name varchar(100)
);