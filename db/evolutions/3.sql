# Update document
 
# --- !Ups
ALTER TABLE document ADD data OID;
 
# --- !Downs
ALTER TABLE document DROP data;