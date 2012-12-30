package models;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.PostLoad;

import org.apache.pdfbox.pdmodel.PDDocument;

import play.Logger;
import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Document extends Model {

	@Required
	public String title;

	@Required
	public Blob pdf;

	public int latest = 1;

	public int pages = 1;

	public Document(String title, Blob data) {
		this.title = title;
		this.pdf = data;
		this.latest = 1;
	}

	public static void updatePageCount(Document doc) {
		try {
			PDDocument pdf = PDDocument.load(doc.pdf.get());
			doc.pages = pdf.getNumberOfPages();
			pdf.close();
		} catch (IOException e) {
			Logger.error(
					e, 
					"error correcting pages for %d: %s",
					doc.id,
					e.getMessage());
		}
	}

}
