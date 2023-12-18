package it.unifi.ing.stlab.empedocle.actions.factquery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.factquery.factory.FactQueryFactory;
import it.unifi.ing.stlab.factquery.factory.expression.ExpressionFactory;
import it.unifi.ing.stlab.factquery.factory.expression.ExpressionLinkFactory;
import it.unifi.ing.stlab.factquery.model.FactQuery;
import it.unifi.ing.stlab.factquery.model.expression.AndExpression;
import it.unifi.ing.stlab.factquery.model.expression.AtomicExpression;
import it.unifi.ing.stlab.factquery.model.expression.ComparisonOperator;
import it.unifi.ing.stlab.factquery.model.expression.OrExpression;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.QuantitativeFactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.TextualFactValue;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

public class ExaminationFactQueryConstructorTest {

	protected FactQuery factQuery;
	protected ExaminationFactQueryConstructor builder;
	
	protected EntityManager entityManager;
	protected Query jpaQuery;
	
	@Before
	public void setUp() throws Exception {
		factQuery = FactQueryFactory.createQuery();
		
		entityManager = mock( EntityManager.class );
		jpaQuery = mock( Query.class );
				
		when( entityManager.createQuery( anyString() ) ).thenReturn( jpaQuery );
		
		
	}
	
	@Test
	public void testBuildQueryDef1() {
		factQuery.setExpression( 
				buildQueryElement( TypeFactory.createTextualType(), "Condition0", buildTextualFactValue( "Parametro0" ), ComparisonOperator.EQUALS ));
		
		builder = new ExaminationFactQueryConstructor();
		
		String result = builder.buildQueryDef( factQuery );
		
		assertNotNull( result );
		assertEquals( " select distinct ( factroot ) " +
						" from FactImpl factroot " +
						" join factroot.context.appointment.patient.after aa " +
						" where factroot.type = factroot.context.type.type " +
						" and aa.id = :pid " +
						" and factroot.context.appointment.agenda in :agendas " +
						" and factroot.destination is null " +
						" and factroot.status != :notStatus " + 
						" and factroot.context.status in :contextStatuses " +
						" and (" +
						" exists ( select fl0.target from FactLinkImpl fl0 where fl0.type = :type0 and " +
								"fl0.target.text = :value0 and fl0.target.destination is null and " +
								"fl0.target.status != :notStatus and fl0.target.context = factroot.context ) )" +
						" order by factroot.context.appointment.date desc " , result );
		
		assertEquals( 2, builder.getAdditionalParams().size() );
		
		AtomicExpression expr = ClassHelper.cast( factQuery.getExpression(), AtomicExpression.class );
		assertEquals( expr.getTypeLink(), builder.getAdditionalParams().get( "type0" ) );
		
		assertEquals( "Parametro0", builder.getAdditionalParams().get( "value0" ) );
		
	}
	
	@Test
	public void testBuildQueryDef2() {
		factQuery.setExpression(
				buildQueryElement( TypeFactory.createQuantitativeType(), "Condition0", null, ComparisonOperator.NOT_NULL ));
		
		builder = new ExaminationFactQueryConstructor();
		
		String result = builder.buildQueryDef( factQuery );
		
		assertNotNull( result );
		assertEquals(" select distinct ( factroot ) " +
						" from FactImpl factroot " +
						" join factroot.context.appointment.patient.after aa " +
						" where factroot.type = factroot.context.type.type " +
						" and aa.id = :pid " +
						" and factroot.context.appointment.agenda in :agendas " +
						" and factroot.destination is null " +
						" and factroot.status != :notStatus " + 
						" and factroot.context.status in :contextStatuses " +
						" and (" +
						" exists ( select fl0.target from FactLinkImpl fl0 where fl0.type = :type0 and " +
								"fl0.target.quantity.value is not null and fl0.target.destination is null and " +
								"fl0.target.status != :notStatus and fl0.target.context = factroot.context ) )" +
						" order by factroot.context.appointment.date desc " , result );
		
		assertEquals( 1, builder.getAdditionalParams().size() );
		
		AtomicExpression expr = ClassHelper.cast( factQuery.getExpression(), AtomicExpression.class );
		assertEquals( expr.getTypeLink(), builder.getAdditionalParams().get( "type0" ) );
		
	}
		
	@Test
	public void testBuildQueryDef3() {
		OrExpression sourceExpr = ExpressionFactory.createOrExpression();
		
		AtomicExpression subexpr1 = buildQueryElement( TypeFactory.createTextualType(), 
															"Condition0", 
															buildTextualFactValue( "Parametro0" ), 
															ComparisonOperator.EQUALS );
		AtomicExpression subexpr2 =  buildQueryElement( TypeFactory.createQuantitativeType(), 
															"Condition1", 
															buildQuantitativeFactValue( new Double( 10 ) ), 
															ComparisonOperator.GREATER_THAN );
		ExpressionLinkFactory.createExpressionLink( sourceExpr, subexpr1 );
		ExpressionLinkFactory.createExpressionLink( sourceExpr, subexpr2 );
		factQuery.setExpression( sourceExpr );
		
		builder = new ExaminationFactQueryConstructor();
		
		String result = builder.buildQueryDef( factQuery );
		
		assertNotNull( result );
		assertEquals( " select distinct ( factroot ) " +
						" from FactImpl factroot " +
						" join factroot.context.appointment.patient.after aa " +
						" where factroot.type = factroot.context.type.type " +
						" and aa.id = :pid " + 
						" and factroot.context.appointment.agenda in :agendas " +
						" and factroot.destination is null " +
						" and factroot.status != :notStatus " + 
						" and factroot.context.status in :contextStatuses " +
						" and (" +
						" exists ( select fl0.target from FactLinkImpl fl0 where fl0.type = :type0 and " +
								"fl0.target.text = :value0 and fl0.target.destination is null and " +
								"fl0.target.status != :notStatus and fl0.target.context = factroot.context ) " +
						" or " +
						" exists ( select fl1.target from FactLinkImpl fl1 where fl1.type = :type1 and " +
								"fl1.target.quantity.value > :value1 and fl1.target.destination is null and " +
								"fl1.target.status != :notStatus and fl1.target.context = factroot.context ) )" +
						" order by factroot.context.appointment.date desc " , result );
		
		assertEquals( 4, builder.getAdditionalParams().size() );
		
		assertEquals( subexpr1.getTypeLink(), builder.getAdditionalParams().get( "type0" ) );
		assertEquals( "Parametro0", builder.getAdditionalParams().get( "value0" ) );
		
		assertEquals( subexpr2.getTypeLink(), builder.getAdditionalParams().get( "type1" ) );
		assertEquals( new Double( 10 ), builder.getAdditionalParams().get( "value1" ) );
		
	}
	
	@Test
	public void testBuildQueryDef4() {
		AndExpression sourceExpr = ExpressionFactory.createAndExpression();
		
		AtomicExpression subexpr1 = buildQueryElement( TypeFactory.createTextualType(), 
																"Condition0", 
																buildTextualFactValue( "Parametro0" ), 
																ComparisonOperator.EQUALS );
		AtomicExpression subexpr2 = buildQueryElement( TypeFactory.createTextualType(), 
																"Condition1", 
																buildTextualFactValue( "Parametro1" ), 
																ComparisonOperator.EQUALS );
		AtomicExpression subexpr3 = buildQueryElement( TypeFactory.createQuantitativeType(), 
																"Condition2", 
																buildQuantitativeFactValue( new Double( 15 ) ), 
																ComparisonOperator.LESS_THAN );
		ExpressionLinkFactory.createExpressionLink( sourceExpr, subexpr1 );
		ExpressionLinkFactory.createExpressionLink( sourceExpr, subexpr2 );
		ExpressionLinkFactory.createExpressionLink( sourceExpr, subexpr3 );
		factQuery.setExpression( sourceExpr );
		
		builder = new ExaminationFactQueryConstructor();
		
		String result = builder.buildQueryDef( factQuery );
		
		assertNotNull( result );
		assertEquals( " select distinct ( factroot ) " +
						" from FactImpl factroot " +
						" join factroot.context.appointment.patient.after aa " +
						" where factroot.type = factroot.context.type.type " +
						" and aa.id = :pid " + 
						" and factroot.context.appointment.agenda in :agendas " +
						" and factroot.destination is null " +
						" and factroot.status != :notStatus " + 
						" and factroot.context.status in :contextStatuses " +
						" and (" +
						" exists ( select fl0.target from FactLinkImpl fl0 where fl0.type = :type0 and " +
								"fl0.target.text = :value0 and fl0.target.destination is null and " +
								"fl0.target.status != :notStatus and fl0.target.context = factroot.context ) " +
						" and " +
						" exists ( select fl1.target from FactLinkImpl fl1 where fl1.type = :type1 and " +
								"fl1.target.text = :value1 and fl1.target.destination is null and " +
								"fl1.target.status != :notStatus and fl1.target.context = factroot.context ) " +
						" and " +
						" exists ( select fl2.target from FactLinkImpl fl2 where fl2.type = :type2 and " +
								"fl2.target.quantity.value < :value2 and fl2.target.destination is null and " +
								"fl2.target.status != :notStatus and fl2.target.context = factroot.context ) )" +
						" order by factroot.context.appointment.date desc ", result );
		
		assertEquals( 6, builder.getAdditionalParams().size() );
		
		assertEquals( subexpr1.getTypeLink(), 
							builder.getAdditionalParams().get( "type0" ) );
		
		assertEquals( "Parametro0", builder.getAdditionalParams().get( "value0" ) );
		
		assertEquals( subexpr2.getTypeLink(), 
							builder.getAdditionalParams().get( "type1" ) );

		assertEquals( "Parametro1", builder.getAdditionalParams().get( "value1" ) );
		
		assertEquals( subexpr3.getTypeLink(), 
							builder.getAdditionalParams().get( "type2" ) );

		assertEquals( new Double( 15 ), builder.getAdditionalParams().get( "value2" ) );
	}
	
	
	@Test
	public void testBuildQueryDef5() {
		builder = new ExaminationFactQueryConstructor();
		
		String result = builder.buildQueryDef( factQuery );
		
		assertNotNull( result );
		assertEquals( " select distinct ( factroot ) " +
						" from FactImpl factroot " +
						" join factroot.context.appointment.patient.after aa " +
						" where factroot.type = factroot.context.type.type " +
						" and aa.id = :pid " +
						" and factroot.context.appointment.agenda in :agendas " +
						" and factroot.destination is null " +
						" and factroot.status != :notStatus " + 
						" and factroot.context.status in :contextStatuses " +
						" order by factroot.context.appointment.date desc " , result );
		
	}
	
	//
	// private methods
	//
	
	private AtomicExpression buildQueryElement( Type type, String typeLinkName, FactValue parameter, ComparisonOperator operator ){
		TypeLink typeLink = TypeLinkFactory.createLink();
		typeLink.setName( typeLinkName );
		typeLink.assignTarget( type );
		
		AtomicExpression queryComponent = ExpressionFactory.createAtomicExpression();
		queryComponent.setTypeLink( typeLink );
		queryComponent.setOperator( operator );
		queryComponent.setParameter( parameter );
		
		return queryComponent;
	}
	
	private FactValue buildTextualFactValue( String value ){
		TextualFactValue factValue = new TextualFactValue( UUID.randomUUID().toString() );
		factValue.setText( value );
		
		return factValue;
	}
	
	private FactValue buildQuantitativeFactValue( Double value ){
		QuantitativeFactValue factValue = new QuantitativeFactValue( UUID.randomUUID().toString() );
		Quantity quantity = new Quantity();
		quantity.setValue( value );
		
		factValue.setQuantity( quantity );
		return factValue;
	}
	
}
