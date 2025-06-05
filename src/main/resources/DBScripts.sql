CREATE TABLE person (
    id number(10) DEFAULT dept_seq.nextval NOT NULL,
    full_name varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL
);
ALTER TABLE person ADD (
  CONSTRAINT dept_pk PRIMARY KEY (id));