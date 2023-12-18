package it.unifi.ing.stlab.view.model.widgets.input;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.widgets.ViewerInput;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "FILEUPLOAD" )
public class FileUpload extends ViewerInput {

	private String acceptedTypes;
	
	public FileUpload(String uuid){
		super(uuid);
	}
	protected FileUpload(){
		super();
	}
	
	@Column( name = "accepted_types")
	public String getAcceptedTypes() {
		return acceptedTypes;
	}
	public void setAcceptedTypes(String acceptedTypes) {
		this.acceptedTypes = acceptedTypes;
	}
	
	@Transient
	@Override
	public String getXhtml() {
		return "../component/fileUpload.xhtml";
	}

	@Override
	public void accept(ViewerVisitor v){
		v.visitFileUpload(this);
	}

}
