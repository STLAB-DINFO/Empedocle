package it.unifi.ing.stlab.view.model.widgets.output;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.widgets.ViewerOutput;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("OUTPUT_VALUE")
public class OutputValue extends ViewerOutput {
	
	public OutputValue(String uuid) {
		super(uuid);
	}
	protected OutputValue() {
		super();
	}

	@Transient
	@Override
	public String getXhtml() {
		return "../component/outputValue.xhtml";
	}
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitOutputValue(this);
	}
}