package controllers;

import play.*;
import play.db.jpa.Blob;
import play.libs.WS;
import play.mvc.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.pdfbox.pdmodel.PDDocument;

import models.*;

public class Application extends Controller {

	public static void index() {
		render();
	}

	public static void upload() {
		render();
	}

	public static void process(Document doc, String url) throws IOException {
		doc.latest = 1;

		if (doc.pdf == null && url != null) {
			InputStream stream = WS.url(url).get().getStream();
			doc.pdf = new Blob();
			doc.pdf.set(stream, "application/pdf");
		}

		Document.updatePageCount(doc);

		doc.save();

		read(doc.id);
	}

	public static void read(long id) {
		Document doc = Document.findById(id);
		if (doc != null)
			render(doc);
		else
			notFound("document with ID of " + id);
	}

	public static void document(long id) {
		Document doc = Document.findById(id);
		if (doc != null)
			renderBinary(doc.pdf.get(), doc.title + ".pdf");
		else
			notFound("document with ID of " + id);
	}

	public static void delete(long id) {
		Document doc = Document.findById(id);
		doc.delete();

		index();
	}

	public static void latest(long id, int page) {
		Document doc = Document.findById(id);
		if (doc != null) {
			doc.latest = page;
			doc.save();

			ok();
		} else {
			notFound("document with ID of " + id);
		}
	}

}