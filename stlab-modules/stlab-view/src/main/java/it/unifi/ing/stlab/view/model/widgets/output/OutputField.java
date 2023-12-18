package it.unifi.ing.stlab.view.model.widgets.output;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.widgets.ViewerOutput;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "OUTPUT_FIELD" )
public class OutputField extends ViewerOutput {

	private String path;
	
	public OutputField(String uuid){
		super(uuid);
	}
	
	protected OutputField(){
		super();
	}
	

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
	@Transient
	public String getRoot(){
		int idx = 0;
		if(path == null || (idx = path.indexOf('.')) == -1)
			return null;
		else
			return path.substring(0, idx);
	}
	
	@Transient
	public void setRoot(String s){
		if(s == null || s.isEmpty())
			return;
		
		int idx = 0;
		if(path == null || (idx = path.indexOf('.')) == -1)
			path = s;
		else
			path = s+path.substring(idx);
			
	}

	public void addFieldPath(String s){
		if(s == null || s.isEmpty())
			return;
		
		StringBuffer sb = new StringBuffer(path);
		sb.append('.');
		sb.append(s);
		
		path = sb.toString();
	}

	@Transient
	@Override
	public String getXhtml() {
		return "../component/outputField.xhtml";
	}
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitOutputField(this);
	}

}
