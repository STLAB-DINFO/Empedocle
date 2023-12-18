package it.unifi.ing.stlab.reflection.model.facts;



public interface FactVisitor {
	
	  void visitTextual( TextualFact fact );
	  void visitQuantitative( QuantitativeFact fact );
	  void visitQualitative( QualitativeFact fact );
	  void visitTemporal( TemporalFact fact );
	  void visitComposite( CompositeFact fact );

}
