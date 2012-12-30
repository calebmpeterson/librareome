# Update document
 
# --- !Ups
ALTER TABLE document DROP pdf;
 
# --- !Downs
ALTER TABLE document ADD pdf CHARACTER VARYING[255];