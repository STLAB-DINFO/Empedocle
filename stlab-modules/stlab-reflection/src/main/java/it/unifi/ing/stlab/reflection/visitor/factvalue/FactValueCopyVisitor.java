package it.unifi.ing.stlab.reflection.visitor.factvalue;

import it.unifi.ing.stlab.reflection.factory.values.FactValueFactory;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValueVisitor;
import it.unifi.ing.stlab.reflection.model.facts.values.QualitativeFactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.QuantitativeFactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.TemporalFactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.TextualFactValue;

public class FactValueCopyVisitor implements FactValueVisitor {
	
	private FactValue result;

	@Override
	public void visitTextual(TextualFactValue fv) {
		result = FactValueFactory.createTextualValue();
		((TextualFactValue)result).setText(fv.getText());
		
	}

	@Override
	public void visitQualitative(QualitativeFactValue fv) {
		result = FactValueFactory.createQualitativeValue();
		((QualitativeFactValue)result).setPhenomenon(fv.getPhenomenon());
		
	}

	@Override
	public void visitQuantitative(QuantitativeFactValue fv) {
		result = FactValueFactory.createQuantitativeValue();
		((QuantitativeFactValue)result).setQuantity(fv.getQuantity());
		
	}

	@Override
	public void visitTemporal(TemporalFactValue fv) {
		result = null;
		
	}

	public FactValue getResult() {
		return result;
	}

	public void setResult(FactValue result) {
		this.result = result;
	}

}
