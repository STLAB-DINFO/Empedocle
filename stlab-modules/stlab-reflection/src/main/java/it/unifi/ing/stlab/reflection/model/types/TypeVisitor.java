package it.unifi.ing.stlab.reflection.model.types;


public interface TypeVisitor {

	public void visitTextualType( TextualType type );
	public void visitEnumeratedType( EnumeratedType type );
	public void visitQueriedType( QueriedType type );
	public void visitQuantitativeType( QuantitativeType type );
	public void visitTemporalType( TemporalType type );
	public void visitCompositeType( CompositeType type );
}
