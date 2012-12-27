package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    public static void upload() {
    	render();
    }
    
    public static void process(Document document) {
    	document.save();
    	index();
    }
    
    public static void read(long id) {
    	Document doc = Document.findById(id);
    	render(doc.id, doc.title);
    }
    
    public static void document(long id) {
    	Document doc = Document.findById(id);
    	renderBinary(doc.pdf.get(), doc.title + ".pdf");
    }
    
    public static void delete(long id) {
    	Document doc = Document.findById(id);
    	doc.delete();
    	
    	index();
    }

}