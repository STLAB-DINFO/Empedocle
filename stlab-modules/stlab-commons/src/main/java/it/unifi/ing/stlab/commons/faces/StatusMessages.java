package it.unifi.ing.stlab.commons.faces;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class StatusMessages implements Serializable {
	
	private static final long serialVersionUID = 4597662408089030785L;
	
	@Inject
	private FacesContext facesContext;

	public void addMessage(String summary) {
		facesContext.addMessage(null, new FacesMessage(summary));
	}
	
	public void addMessage(String summary, String detail) {
		facesContext.addMessage(null, new FacesMessage(summary, detail));
	}
	
	public void addMessage(Severity severity, String summary, String detail) {
		facesContext.addMessage(null, new FacesMessage(severity, summary, detail));
	}
	
}
