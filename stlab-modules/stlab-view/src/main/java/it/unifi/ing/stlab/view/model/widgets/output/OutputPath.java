package it.unifi.ing.stlab.view.model.widgets.output;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.widgets.ViewerOutput;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("OUTPUT_PATH")
public class OutputPath extends ViewerOutput {

	public OutputPath(String uuid) {
		super(uuid);
	}
	protected OutputPath() {
		super();
	}
	
	@Transient
	@Override
	public String getXhtml() {
		return "../component/outputPath.xhtml";
	}
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitOutputPath(this);
	}
	
}