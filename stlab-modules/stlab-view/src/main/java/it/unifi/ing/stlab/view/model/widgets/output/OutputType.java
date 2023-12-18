package it.unifi.ing.stlab.view.model.widgets.output;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.widgets.ViewerOutput;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("OUTPUT_TYPE")
public class OutputType extends ViewerOutput {

	public OutputType(String uuid) {
		super(uuid);
	}
	protected OutputType() {
		super();
	}
	
	@Transient
	@Override
	public String getXhtml() {
		return "../component/outputType.xhtml";
	}	
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitOutputType(this);
	}
}