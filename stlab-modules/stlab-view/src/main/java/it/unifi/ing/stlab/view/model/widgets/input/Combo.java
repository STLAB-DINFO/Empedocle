package it.unifi.ing.stlab.view.model.widgets.input;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.widgets.ViewerInput;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("COMBO")
public class Combo extends ViewerInput {

	public Combo(String uuid) {
		super(uuid);
	}
	protected Combo() {
		super();
	}

	@Transient
	@Override
	public String getXhtml() {
		return "../component/combo.xhtml";
	}	
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitCombo(this);
	}
}