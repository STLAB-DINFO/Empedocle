package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.commons.cdi.ViewScoped;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Named;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

@Named
@ViewScoped
// Can be @ViewScoped, but caution should be taken with byte[] property. You
// don't want to save it in session.
@Deprecated
public class FileUploadBean {

	private String path = "/usr/local/jboss-as-7.1.1.Final/welcome-content/pdf/";

    public void upload(FileUploadEvent event) throws Exception {
        UploadedFile item = event.getUploadedFile();
        
        byte[] content = item.getData();
        
		try {
	        String filePath = path + item.getName();
			File file = new File(filePath);
				FileOutputStream fileOuputStream = new FileOutputStream(file);
				fileOuputStream.write(content);
				fileOuputStream.close();
				
		    	TextualFact target = textual( event.getComponent().getAttributes().get( "fact" ) );
				target.setText(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Errore durante il caricamento del file", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Errore durante il caricamento del file", e);
		}
    }
    
//	public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
//		Map<String, String> requestParameterMap = ctx.getExternalContext()
//				.getRequestParameterMap();
//		String maxSize = requestParameterMap.get("maxSize");
//		String matchType = requestParameterMap.get("matchType");
//
//		List<FacesMessage> msgs = new ArrayList<FacesMessage>();
//		file = (Part) value;
//		if (file.getSize() > Long.parseLong(maxSize)) {
//			msgs.add(new FacesMessage("File too big"));
//		}
//		if (!file.getContentType().matches(matchType)) {
//			msgs.add(new FacesMessage("File type not allowed"));
//		}
//		if (!msgs.isEmpty()) {
//			throw new ValidatorException(msgs);
//		}
//	}
	
	private TextualFact textual(Object object) {
		if( ClassHelper.instanceOf( object, TextualFact.class ) )
			return ClassHelper.cast( object, TextualFact.class );
		
		throw new ClassCastException( "l'oggetto non è un fact testuale" );
	}

}