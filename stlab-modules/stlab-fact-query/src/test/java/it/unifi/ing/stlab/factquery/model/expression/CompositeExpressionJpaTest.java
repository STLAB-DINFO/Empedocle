package it.unifi.ing.stlab.factquery.model.expression;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.factquery.factory.expression.ExpressionFactory;
import it.unifi.ing.stlab.factquery.factory.expression.ExpressionLinkFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.model.facts.values.TextualFactValue;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.test.PersistenceTest;

import java.util.UUID;

import org.junit.Test;

public class CompositeExpressionJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		CompositeExpression expression = ExpressionFactory.createAndExpression();
		Expression target = buildAtomicExpression();
		
		ExpressionLink link = ExpressionLinkFactory.createExpressionLink();
		link.assignSource( expression );
		link.assignTarget( target );
		
		entityManager.persist( expression );
		
		uuid = expression.getUuid();
	}
	
	@Test
	public void testFactQuery(){
		CompositeExpression expr = (CompositeExpression)entityManager
											.createQuery( " select e " +
												" from Expression e " +
												" where e.uuid = :uuid " )
											.setParameter("uuid", uuid)
											.getSingleResult();
			
		assertNotNull( expr );
		assertEquals( 1, expr.listChildren().size() );
		assertEquals( 2, expr.listDescendents().size() );
	}
	
	private Expression buildAtomicExpression() {
		TypeLink typeLink = TypeLinkFactory.createLink();
		typeLink.setName( "link" );
		entityManager.persist( typeLink );
		
		TextualFactValue factValue = new TextualFactValue( UUID.randomUUID().toString() );
		factValue.setText( "testo" );
		
		AtomicExpression expression = ExpressionFactory.createAtomicExpression();
		expression.setTypeLink( typeLink );
		expression.setOperator( ComparisonOperator.EQUALS );
		expression.setParameter( factValue );
		
		return expression;
	}
	
}
