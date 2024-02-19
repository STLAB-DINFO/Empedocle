package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.commons.cdi.properties.Property;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FilenameUtils;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

@Named
@RequestScoped
public class FileUploadController {
	
	@Inject
	private FacesContext facesContext;
	
	@Inject @Property(file="META-INF/file-upload.properties", name="local_content_path")
//	@Inject @Property(file="META-INF/file-upload.properties", name="production_content_path")	
	private String content_path;
	
    public void upload(FileUploadEvent event) throws Exception {
        UploadedFile item = event.getUploadedFile();

        String fileName = item.getName();
        String extension = FilenameUtils.getExtension(fileName);
        byte[] content = item.getData();
        
		try {
	    	TextualFact target = (TextualFact) event.getComponent().getAttributes().get( "fact" );			
				    	
	    	fileName = retryFactId(target) + "." + extension;
	        String filePath = content_path + fileName;
			File file = new File(filePath);
				FileOutputStream fileOuputStream = new FileOutputStream(file);
				fileOuputStream.write(content);
				fileOuputStream.close();
				
				target.setText( content_path.substring(content_path.indexOf("welcome-content") + 
						"welcome-content".length()) + fileName );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Error during file upload", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Error during file upload", e);
		}
    }
    
	public void delete(ActionEvent event) {
    	TextualFact target = (TextualFact) event.getComponent().getAttributes().get( "fact" );
    	String text = target.getText();
    	String fileName = text.substring(text.lastIndexOf("/") + 1);
    	File toRemove = new File(content_path + fileName);
    	if(!toRemove.delete()) {
			throw new RuntimeException(
					"Error during file deletion");
    	}
    	target.setText(null);
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
	
    private Long retryFactId(Fact fact) {
    	if(fact instanceof FactImpl) {
    		return ((FactImpl) fact).getId();
    	}
    	throw new UnsupportedOperationException();
    }
    
	public boolean isSupported(Fact fact){
		if(fact == null){
			facesContext.addMessage( null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN, "No observation to show", ""));
			return false;
		}
		if(!isTextualFact(fact)){
			facesContext.addMessage( null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "FileUpload not supported for the observation of type " +
					fact.getType().getClass().getSimpleName(), ""));
			return false;
		}
		return true;
	}
	
	public boolean isTextualFact(Fact fact) {
		return fact != null && ClassHelper.instanceOf( fact, TextualFact.class );
	}
}
