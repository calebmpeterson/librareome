import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

import models.Document;
import play.Logger;
import play.db.jpa.JPA;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Bootstrap extends Job<Void> {

	@Override
	public void run() {
		JPA.execute("DROP TABLE document");
	}

}
