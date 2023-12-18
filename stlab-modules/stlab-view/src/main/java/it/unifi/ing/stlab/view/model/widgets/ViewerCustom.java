package it.unifi.ing.stlab.view.model.widgets;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.ViewerLink;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "CUSTOM" )
public class ViewerCustom extends Viewer {

	private String xhtmlFileName;
	
	public ViewerCustom(String uuid) {
		super(uuid);
	}
	protected ViewerCustom() {
		super();
	}
	
	@Column( name="file_name", nullable=true )
	public String getXhtmlFileName() {
		return xhtmlFileName;
	}
	public void setXhtmlFileName(String xhtmlFileName) {
		this.xhtmlFileName = xhtmlFileName;
	}
	
	
	@Transient
	@Override
	public String getXhtml() {
		return "../component/"+getXhtmlFileName()+".xhtml";
		
	}
	
	
	@Override
	public boolean isValidSubViewer(ViewerLink sv) {
		if( sv == null || !ClassHelper.instanceOf( sv, SubViewer.class ))
			return false;
		
		return true;
	}
	
	@Override
	public void accept(ViewerVisitor v) {
		v.visitViewerCustom(this);
		
	}
	
}