package it.unifi.ing.stlab.view.model.widgets.output;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.widgets.ViewerOutput;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("OUTPUT_LINK")
public class OutputLink extends ViewerOutput {
	
	public OutputLink(String uuid) {
		super(uuid);
	}
	protected OutputLink() {
		super();
	}

	@Transient
	@Override
	public String getXhtml() {
		return "../component/outputLink.xhtml";
	}
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitOutputLink(this);
	}
}