# --- !Ups

CREATE SEQUENCE hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 23
  CACHE 1;

# --- !Downs

DROP SEQUENCE hibernate_sequence;
