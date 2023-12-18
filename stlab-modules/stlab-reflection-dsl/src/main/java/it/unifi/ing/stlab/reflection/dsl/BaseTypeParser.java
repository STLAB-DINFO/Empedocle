package it.unifi.ing.stlab.reflection.dsl;

import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitUseFactory;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;

import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;


public abstract class BaseTypeParser extends Parser {

	private EntityManager entityManager;
	private List<Exception> errors;
	
	public BaseTypeParser(TokenStream stream) {
		super(stream);
		errors = new ArrayList<Exception>();
	}

	public BaseTypeParser(TokenStream stream, RecognizerSharedState recognizer) {
		super(stream, recognizer);
		errors = new ArrayList<Exception>();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public void reportError(RecognitionException e) {
		String header = getErrorHeader(e);
    	String content = getErrorMessage(e, getTokenNames());
    	
		extendedReportError(new RuntimeException(header+" "+content) );
	}
	public void extendedReportError( Exception e ) {
		errors.add( e );
	}
	
	public boolean isOk() {
		return errors.isEmpty();
	}
	public boolean hasErrors() {
		return !isOk();
	}
	public String errorReport() {
		if ( isOk() ) return "OK";
		
		StringBuffer result = new StringBuffer( "" );
		for ( Exception e : errors ) {
			result.append( e.toString() ).append( "\n" );
		}
		return result.toString();
	}

	protected TextualType createTextualType() {
		return TypeFactory.createTextualType();
	}
	
	protected TemporalType createTemporalType() {
		return TypeFactory.createTemporalType();
	}

	protected EnumeratedType createEnumeratedType() {
		return TypeFactory.createEnumeratedType();
	}

	protected QueriedType createQueriedType() {
		return TypeFactory.createQueriedType();
	}

	protected QuantitativeType createQuantitativeType() {
		return TypeFactory.createQuantitativeType();
	}
	
	protected CompositeType createCompositeType() {
		return TypeFactory.createCompositeType();
	}
	
	protected TypeLink createTypeLink() {
		return TypeLinkFactory.createLink();
	}

	@Deprecated
	protected Phenomenon createPhenomenon( String value ) {
		Phenomenon result = PhenomenonFactory.createPhenomenon();
		result.setName( value );
		return result;
	}
	
	protected Phenomenon createPhenomenon( EnumeratedType enumerated, String value, boolean ordered ) {
		Phenomenon result = PhenomenonFactory.createPhenomenon();
		result.setName( value );
		if( ordered ) {
			result.setPosition( enumerated.listPhenomena().size() );
		}
		enumerated.addPhenomenon( result );
		
		return result;
	}
	
	protected UnitUse createUnitUse( String unit, Integer digits, Integer decimals ) {
		UnitUse result = UnitUseFactory.createUnitUse();
		result.setUnit( findUnit( unit ));
		result.setDigits( digits );
		result.setDecimals( decimals );
		return result;
	}
	
	private Unit findUnit( String unit ) {
		List<?> resultList = 
			entityManager
				.createQuery( "select u from Unit u where u.name = :unit" )
				.setParameter( "unit", unit )
				.setFlushMode( FlushModeType.COMMIT )
				.getResultList();

		if ( resultList == null || resultList.size() < 1 )
			throw new IllegalArgumentException("Nessuna unità di misura trovata con nome " + unit);

		if ( resultList.size() > 1 )
			throw new IllegalArgumentException("Più di un'unità di misura trovata con nome " + unit);

		return (Unit)resultList.get( 0 );
	}

	protected Type findType( String type ) {
		List<?> resultList = 
			entityManager
				.createQuery( "select t from Type t where t.name = :type" )
				.setParameter( "type", type )
				.setFlushMode( FlushModeType.COMMIT )
				.getResultList();

		if ( resultList == null || resultList.size() < 1 )
			throw new IllegalArgumentException("Nessun tipo trovato con nome " + type);

		if ( resultList.size() > 1 )
			throw new IllegalArgumentException("Più di un tipo trovato con nome " + type);

		return (Type)resultList.get( 0 );
	}
	
	protected String trim( String s ) {
		return s.replaceAll( "\"", "" );
	}
	
	protected Integer integer( String s ) {
		return Integer.parseInt( s );
	}

}