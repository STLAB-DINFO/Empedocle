package it.unifi.ing.stlab.view.model.widgets.input;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.widgets.ViewerInput;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("INPUTTEMP")
public class InputTemporal extends ViewerInput {

	public InputTemporal(String uuid) {
		super(uuid);
	}
	protected InputTemporal() {
		super();
	}
	
	@Transient
	@Override
	public String getXhtml() {
		return "../component/inputTemporal.xhtml";
	}
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitInputTemporal(this);
	}
}