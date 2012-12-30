# Add documents
 
# --- !Ups
CREATE TABLE document
(
  id bigint NOT NULL,
  latest integer NOT NULL,
  pdf character varying(255),
  title character varying(255),
  CONSTRAINT document_pkey PRIMARY KEY (id)
)
 
# --- !Downs
DROP TABLE document;