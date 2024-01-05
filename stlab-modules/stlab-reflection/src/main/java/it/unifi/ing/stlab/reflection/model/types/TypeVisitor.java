package it.unifi.ing.stlab.reflection.model.types;


public interface TypeVisitor {

	void visitTextualType(TextualType type);
	void visitEnumeratedType(EnumeratedType type);
	void visitQueriedType(QueriedType type);
	void visitQuantitativeType(QuantitativeType type);
	void visitTemporalType(TemporalType type);
	void visitCompositeType(CompositeType type);
}
