package controllers;

import java.io.IOException;
import java.io.InputStream;

import models.Document;

import org.apache.commons.io.IOUtils;

import play.db.jpa.Blob;
import play.libs.WS;
import play.mvc.Controller;

public class Application extends Controller {

	public static void index() {
		render();
	}

	public static void upload() {
		render();
	}

	public static void process(String title, Blob pdf, String url)
			throws IOException {
		final Document doc;

		if (pdf != null) {
			doc = new Document(title, IOUtils.toByteArray(pdf.get()));
		} else if (pdf == null && url != null) {
			final InputStream stream = WS.url(url).get().getStream();
			doc = new Document(title, IOUtils.toByteArray(stream));
		} else {
			doc = null;
			error("no document");
		}

		doc.save();

		read(doc.id);
	}

	public static void read(long id) {
		final Document doc = Document.findById(id);
		if (doc != null)
			render(doc);
		else
			notFound("document with ID of " + id);
	}

	public static void document(long id) {
		final Document doc = Document.findById(id);
		if (doc != null)
			renderBinary(doc.data(), doc.title + ".pdf");
		else
			notFound("document with ID of " + id);
	}

	public static void delete(long id) {
		final Document doc = Document.findById(id);
		if (doc != null)
			doc.delete();

		index();
	}

	public static void latest(long id, int page) {
		final Document doc = Document.findById(id);
		if (doc != null) {
			doc.latest = page;
			doc.save();

			ok();
		} else {
			notFound("document with ID of " + id);
		}
	}

}