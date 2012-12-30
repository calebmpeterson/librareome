package models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.PostLoad;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;

import play.Logger;
import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;
import sun.awt.image.ByteArrayImageSource;

@Entity
public class Document extends Model {

	@Required
	public String title;
	
	@Lob
	public byte[] data;

	public int latest = 1;

	public int pages = 1;

	public Document(String title, byte[] data) {
		this.title = title;
		this.latest = 1;
		this.data = data;
		determinePages();
		Logger.info("%s length in bytes is %d", title, this.data.length);
	}
	
	public InputStream data() {
		return new ByteArrayInputStream(data);
	}

	public static void updatePageCount(Document doc) {
		doc.determinePages();
	}
	
	private void determinePages() {
		try {
			PDDocument pdf = PDDocument.load(data());
			pages = pdf.getNumberOfPages();
			pdf.close();
		} catch (IOException e) {
			Logger.error(
					e, 
					"error correcting pages for %d: %s",
					id,
					e.getMessage());
		}
	}

}
