package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Document extends Model {

	@Required
	public String title;
	
	@Required
	public Blob pdf;

	public Document(String title, Blob data) {
		this.title = title;
		this.pdf = data;
	}

}
