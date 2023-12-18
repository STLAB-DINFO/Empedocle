package it.unifi.ing.stlab.factquery.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.factquery.model.FactQuery;
import it.unifi.ing.stlab.factquery.model.expression.AndExpression;
import it.unifi.ing.stlab.factquery.model.expression.AtomicExpression;
import it.unifi.ing.stlab.factquery.model.expression.ComparisonOperator;
import it.unifi.ing.stlab.factquery.model.expression.CompositeExpression;
import it.unifi.ing.stlab.factquery.model.expression.OrExpression;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.model.facts.values.QuantitativeFactValue;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

public class FactQueryBuilderTest {

	protected EntityManager entityManager;
	protected Query query;

	@Before
	public void setUp() {
		query = mock( Query.class ); 
		entityManager = mock( EntityManager.class );
		when( entityManager.createQuery( anyString() ) ).thenReturn( query );
		
	}
	
	@Test
	public void testBuildFactPanel1() {
		List<TypeLink> typeLinkResult = new ArrayList<TypeLink>();
		typeLinkResult.add( TypeLinkFactory.createLink() );
		when( query.getResultList() ).thenReturn( typeLinkResult );
		when( query.setParameter( anyString(), anyObject() ) ).thenReturn( query );
		
		FactQuery result = FactQueryBuilder.createFactQuery( entityManager )
											.get();
		
		assertNull( result.getExpression() );
		assertEquals( new Integer( 1 ), result.getLimit() );
		assertEquals( new Integer( 0 ), result.getOffset() );
		assertEquals( false, result.getFetch() );

	}
	
	@Test
	public void testBuildFactPanel2() {
		List<TypeLink> typeLinkResult = new ArrayList<TypeLink>();
		typeLinkResult.add( TypeLinkFactory.createLink() );
		when( query.getResultList() ).thenReturn( typeLinkResult );
		when( query.setParameter( anyString(), anyObject() ) ).thenReturn( query );
		
		FactQuery result = FactQueryBuilder.createFactQuery( entityManager )
											.selectTxt( "Medico Curante", ComparisonOperator.EQUALS, "medico" )
											.get();
		
		assertNotNull( result.getExpression() );
		assertTrue( ClassHelper.instanceOf( result.getExpression(), AtomicExpression.class ) );
		
		AtomicExpression expr = ClassHelper.cast( result.getExpression(), AtomicExpression.class );
		
		assertEquals( typeLinkResult.get( 0 ), expr.getTypeLink() );
		assertEquals( ComparisonOperator.EQUALS, expr.getOperator() );
		assertEquals( "medico", expr.getParameter().getValue() );

	}
	
	@Test
	public void testBuildFactPanel3() {
		List<TypeLink> typeLinkResult = new ArrayList<TypeLink>();
		typeLinkResult.add( TypeLinkFactory.createLink() );
		
		List<TypeLink> typeLinkResult2 = new ArrayList<TypeLink>();
		typeLinkResult2.add( TypeLinkFactory.createLink() );
		
		when( query.getResultList() ).thenReturn( typeLinkResult, typeLinkResult2 );
		when( query.setParameter( anyString(), anyObject() ) ).thenReturn( query );
		
		FactQuery result = FactQueryBuilder.createFactQuery( entityManager )
											.selectTxt( "Medico Curante", ComparisonOperator.EQUALS, "medico" )
											.and()
											.selectQnt( "Anni", ComparisonOperator.GREATER_THAN, new Double( 30 ) )
											.get();
		
		assertNotNull( result.getExpression() );
		assertTrue( ClassHelper.instanceOf( result.getExpression(), CompositeExpression.class ) );
		
		CompositeExpression expr = ClassHelper.cast( result.getExpression(), CompositeExpression.class );
		AtomicExpression subexpr1 = ClassHelper.cast( expr.listChildrenOrdered().get( 0 ).getTarget(), AtomicExpression.class );
		AtomicExpression subexpr2 = ClassHelper.cast( expr.listChildrenOrdered().get( 1 ).getTarget(), AtomicExpression.class );
		
		assertEquals( typeLinkResult.get( 0 ), subexpr1.getTypeLink() );
		assertEquals( ComparisonOperator.EQUALS, subexpr1.getOperator() );
		assertEquals( "medico", subexpr1.getParameter().getValue() );
		assertEquals( typeLinkResult2.get( 0 ), subexpr2.getTypeLink() );
		assertEquals( ComparisonOperator.GREATER_THAN, subexpr2.getOperator() );
		assertEquals( new Double( 30 ), ClassHelper.cast( subexpr2.getParameter(), QuantitativeFactValue.class ).getQuantity().getValue() );

	}
	
	@Test
	public void testBuildFactPanel4() {
		List<TypeLink> typeLinkResult = new ArrayList<TypeLink>();
		typeLinkResult.add( TypeLinkFactory.createLink() );
		
		List<TypeLink> typeLinkResult2 = new ArrayList<TypeLink>();
		typeLinkResult2.add( TypeLinkFactory.createLink() );
		
		List<TypeLink> typeLinkResult3 = new ArrayList<TypeLink>();
		typeLinkResult3.add( TypeLinkFactory.createLink() );
		
		when( query.getResultList() ).thenReturn( typeLinkResult, typeLinkResult2, typeLinkResult3 );
		when( query.setParameter( anyString(), anyObject() ) ).thenReturn( query );
		
		FactQuery result = FactQueryBuilder.createFactQuery( entityManager )
											.selectTxt( "Medico Curante", ComparisonOperator.EQUALS, "medico" )
											.and()
											.selectQnt( "Anni", ComparisonOperator.GREATER_THAN, new Double( 30 ) )
											.and()
											.selectQnt( "Mesi", ComparisonOperator.GREATER_THAN, new Double( 3 ) )
											.get();
		
		assertNotNull( result.getExpression() );
		assertTrue( ClassHelper.instanceOf( result.getExpression(), CompositeExpression.class ) );
		
		CompositeExpression expr = ClassHelper.cast( result.getExpression(), CompositeExpression.class );
		AtomicExpression subexpr1 = ClassHelper.cast( expr.listChildrenOrdered().get( 0 ).getTarget(), AtomicExpression.class );
		AtomicExpression subexpr2 = ClassHelper.cast( expr.listChildrenOrdered().get( 1 ).getTarget(), AtomicExpression.class );
		AtomicExpression subexpr3 = ClassHelper.cast( expr.listChildrenOrdered().get( 2 ).getTarget(), AtomicExpression.class );
		
		assertEquals( typeLinkResult.get( 0 ), subexpr1.getTypeLink() );
		assertEquals( ComparisonOperator.EQUALS, subexpr1.getOperator() );
		assertEquals( "medico", subexpr1.getParameter().getValue() );
		assertEquals( typeLinkResult2.get( 0 ), subexpr2.getTypeLink() );
		assertEquals( ComparisonOperator.GREATER_THAN, subexpr2.getOperator() );
		assertEquals( new Double( 30 ), ClassHelper.cast( subexpr2.getParameter(), QuantitativeFactValue.class ).getQuantity().getValue() );
		assertEquals( typeLinkResult3.get( 0 ), subexpr3.getTypeLink() );
		assertEquals( ComparisonOperator.GREATER_THAN, subexpr3.getOperator() );
		assertEquals( new Double( 3 ), ClassHelper.cast( subexpr3.getParameter(), QuantitativeFactValue.class ).getQuantity().getValue() );

	}
	
	@Test
	public void testBuildFactPanel5() {
		List<TypeLink> typeLinkResult = new ArrayList<TypeLink>();
		typeLinkResult.add( TypeLinkFactory.createLink() );
		
		List<TypeLink> typeLinkResult2 = new ArrayList<TypeLink>();
		typeLinkResult2.add( TypeLinkFactory.createLink() );
		
		List<TypeLink> typeLinkResult3 = new ArrayList<TypeLink>();
		typeLinkResult3.add( TypeLinkFactory.createLink() );
		
		when( query.getResultList() ).thenReturn( typeLinkResult, typeLinkResult2, typeLinkResult3 );
		when( query.setParameter( anyString(), anyObject() ) ).thenReturn( query );
		
		FactQuery result = FactQueryBuilder.createFactQuery( entityManager )
											.selectTxt( "Medico Curante", ComparisonOperator.EQUALS, "medico" )
											.and()
											.selectQnt( "Anni", ComparisonOperator.GREATER_THAN, new Double( 30 ) )
											.or()
											.selectQnt( "Mesi", ComparisonOperator.GREATER_EQUALS, new Double( 3 ) )
											.get();
		
		assertNotNull( result.getExpression() );
		assertTrue( ClassHelper.instanceOf( result.getExpression(), AndExpression.class ) );
		AndExpression expr = ClassHelper.cast( result.getExpression(), AndExpression.class );
		
		assertTrue( ClassHelper.instanceOf( expr.listChildrenOrdered().get( 0 ).getTarget(), AtomicExpression.class ) );
		assertTrue( ClassHelper.instanceOf( expr.listChildrenOrdered().get( 1 ).getTarget(), OrExpression.class ) );
		AtomicExpression subexpr1 = ClassHelper.cast( expr.listChildrenOrdered().get( 0 ).getTarget(), AtomicExpression.class );
		OrExpression subexpr2 = ClassHelper.cast( expr.listChildrenOrdered().get( 1 ).getTarget(), OrExpression.class );
		
		assertTrue( ClassHelper.instanceOf( subexpr2.listChildrenOrdered().get( 0 ).getTarget(), AtomicExpression.class ) );
		assertTrue( ClassHelper.instanceOf( subexpr2.listChildrenOrdered().get( 1 ).getTarget(), AtomicExpression.class ) );
		AtomicExpression subsubexpr1 = ClassHelper.cast( subexpr2.listChildrenOrdered().get( 0 ).getTarget(), AtomicExpression.class );
		AtomicExpression subsubexpr2 = ClassHelper.cast( subexpr2.listChildrenOrdered().get( 1 ).getTarget(), AtomicExpression.class );
		
		assertEquals( typeLinkResult.get( 0 ), subexpr1.getTypeLink() );
		assertEquals( ComparisonOperator.EQUALS, subexpr1.getOperator() );
		assertEquals( "medico", subexpr1.getParameter().getValue() );
		assertEquals( typeLinkResult2.get( 0 ), subsubexpr1.getTypeLink() );
		assertEquals( ComparisonOperator.GREATER_THAN, subsubexpr1.getOperator() );
		assertEquals( new Double( 30 ), ClassHelper.cast( subsubexpr1.getParameter(), QuantitativeFactValue.class ).getQuantity().getValue() );
		assertEquals( typeLinkResult3.get( 0 ), subsubexpr2.getTypeLink() );
		assertEquals( ComparisonOperator.GREATER_EQUALS, subsubexpr2.getOperator() );
		assertEquals( new Double( 3 ), ClassHelper.cast( subsubexpr2.getParameter(), QuantitativeFactValue.class ).getQuantity().getValue() );

	}
	
	@Test( expected=RuntimeException.class )
	public void testBuildException() {
		List<TypeLink> typeLinkResult = new ArrayList<TypeLink>();
		typeLinkResult.add( TypeLinkFactory.createLink() );
		when( query.getResultList() ).thenReturn( typeLinkResult );
		when( query.setParameter( anyString(), anyObject() ) ).thenReturn( query );
		
		FactQueryBuilder.createFactQuery( entityManager )
							.selectTxt( "Medico Curante", ComparisonOperator.EQUALS, "medico" )
							.and()
							.get();
	}
	
}
