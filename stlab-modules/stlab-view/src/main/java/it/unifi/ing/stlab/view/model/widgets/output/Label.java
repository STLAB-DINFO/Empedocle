package it.unifi.ing.stlab.view.model.widgets.output;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.widgets.ViewerOutput;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "LABEL" )
public class Label extends ViewerOutput {
	
	private String value;

	public Label(String uuid) {
		super(uuid);
	}
	protected Label() {
		super();
	}

	@Lob
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Transient
	@Override
	public String getXhtml() {
		return "../component/label.xhtml";
	}
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitLabel(this);
	}
}