package it.unifi.ing.stlab.view.model.widgets.container;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerContainer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("PARAGRAPH")
public class Paragraph extends ViewerContainer {
	
	public Paragraph(String uuid) {
		super(uuid);
	}
	protected Paragraph() {
		super();
	}
	
	@Transient
	@Override
	public String getXhtml() {
		return "../component/paragraph.xhtml";
	}
	
	@Override
	public boolean isValidSubViewer(ViewerLink sv) {

		if( sv == null || !ClassHelper.instanceOf( sv, SubViewer.class ))
			return false;
		
		return true;
	}
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitParagraph(this);
	}

}