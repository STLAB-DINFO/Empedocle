package it.unifi.ing.stlab.reflection.model.facts.values;

public interface FactValueVisitor {
	
	void visitTextual(TextualFactValue fv);
	void visitQualitative(QualitativeFactValue fv);
	void visitQuantitative(QuantitativeFactValue fv);
	void visitTemporal(TemporalFactValue fv);

}
