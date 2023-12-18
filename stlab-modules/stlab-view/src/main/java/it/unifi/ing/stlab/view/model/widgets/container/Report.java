package it.unifi.ing.stlab.view.model.widgets.container;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerContainer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("REPORT")
public class Report extends ViewerContainer {

	public Report(String uuid){
		super(uuid);
	}
	protected Report(){
		super();
	}
	
	@Override
	public boolean isValidSubViewer(ViewerLink sv) {
		if( sv == null || !ClassHelper.instanceOf( sv, SubViewer.class ))
			return false;
		
		if(listChildren().size() > 3)
			return false;
		
		return true;
	}
	
	
	@Transient
	public Viewer getHeader(){
		return getByPriority(0l).getTarget();
	}
	
	@Transient
	public Viewer getContent(){
		return getByPriority(1l).getTarget();
	}
	
	@Transient
	public Viewer getFooter(){
		return getByPriority(2l).getTarget();
	}
	
	@Transient
	@Override
	public String getXhtml() {
		return "../component/report.xhtml";

	}
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitReport(this);
	}

}
