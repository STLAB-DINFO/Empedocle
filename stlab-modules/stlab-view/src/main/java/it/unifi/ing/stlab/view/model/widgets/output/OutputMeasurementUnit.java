package it.unifi.ing.stlab.view.model.widgets.output;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.widgets.ViewerOutput;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("OUTPUT_MEASURE")
public class OutputMeasurementUnit extends ViewerOutput {

	public OutputMeasurementUnit(String uuid) {
		super(uuid);
	}
	protected OutputMeasurementUnit() {
		super();
	}

	@Transient
	@Override
	public String getXhtml() {
		return "../component/outputMeasurementUnit.xhtml";
	}
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitOutputMeasurementUnit(this);
	}
}