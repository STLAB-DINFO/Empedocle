package it.unifi.ing.stlab.reflection.dsl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.Unit;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.junit.Before;
import org.junit.Test;

public class TypeParserTest {

	
	protected EntityManager entityManager;
	protected Query query;

	@Before
	public void setUp() {
		entityManager = mock( EntityManager.class );
		query = mock( Query.class );
		when( entityManager.createQuery( anyString() )).thenReturn( query );
		when( query.setParameter( anyString(), anyString() )).thenReturn( query );
		when( query.setFlushMode( FlushModeType.COMMIT ) ).thenReturn( query );
	}
	
	@Test
	public void testTextual() throws Exception {
		TextualType type = (TextualType)parser( "tx" ).rule();
		
		assertNotNull( type );
	}
	
	@Test
	public void testEnumerated() throws Exception {
		EnumeratedType type = (EnumeratedType)parser( "ql { \"ph1\", \"ph2\" } " ).rule();
		
		assertNotNull( type );
		
		List<Phenomenon> phenomena = sortPhenomena( type.listPhenomena() );
		assertEquals( 2, phenomena.size() );
		assertNull( phenomena.get( 0 ).getPosition() );
		assertNull( phenomena.get( 1 ).getPosition() );
	}
	
	@Test
	public void testEnumeratedWithPosition() throws Exception {
		EnumeratedType type = (EnumeratedType)parser( "ql ordered { \"ph2\", \"ph1\" } " ).rule();
		
		assertNotNull( type );
		
		List<Phenomenon> phenomena = sortPhenomena( type.listPhenomena() );
		assertEquals( 2, type.listPhenomena().size() );
		assertNotNull( phenomena.get( 0 ).getPosition() );
		assertEquals( 0, phenomena.get( 0 ).getPosition().intValue() );
		assertNotNull( phenomena.get( 1 ).getPosition() );
		assertEquals( 1, phenomena.get( 1 ).getPosition().intValue() );
		
	}

	@Test
	public void testQueried() throws Exception {
		QueriedType type = (QueriedType)parser( "st { \"select... from...\" } " ).rule();
		
		assertNotNull( type );
		assertEquals( "select... from...", type.getQueryDef() );
	}

	@Test
	public void testQuantitative() throws Exception {
		List<Unit> rs = new ArrayList<Unit>();
		rs.add( UnitFactory.createUnit() );
		when( query.getResultList() ).thenReturn( rs );
		
		QuantitativeType type = (QuantitativeType)parser( "qt { \"metro\"(3,2), \"centimetro\"(3,0) } " ).rule();
		
		assertNotNull( type );
		assertEquals( 2, type.listUnits().size() );
	}
	
	@Test
	public void testComposite1() throws Exception {
		CompositeType type = (CompositeType)parser( "ct { \"r\" : tx  }").rule(); 
		assertNotNull( type );
		assertEquals( 1, type.listChildren().size() );
	}

	@Test
	public void testComposite2() throws Exception {
		List<Type> rs = new ArrayList<Type>();
		rs.add( TypeFactory.createTextualType() );
		when( query.getResultList() ).thenReturn( rs );

		CompositeType type = (CompositeType)parser( "ct { \"r\" : \"hello\"  }").rule(); 
		assertNotNull( type );
		assertEquals( 1, type.listChildren().size() );
	}
	
	@Test
	public void testCompositePriority() throws Exception {
		List<Type> rs = new ArrayList<Type>();
		rs.add( TypeFactory.createTextualType() );
		when( query.getResultList() ).thenReturn( rs );

		CompositeType type = (CompositeType)parser( "ct { \"r\" : \"hello\", \"s\" : \"world\" }").rule(); 
		assertEquals( new Long(0), type.listChildrenOrdered().get(0).getPriority() );
		assertEquals( new Long(1), type.listChildrenOrdered().get(1).getPriority() );
	}	
	
	@Test
	public void testAnonymous() throws Exception {
		List<Type> rs = new ArrayList<Type>();
		TextualType t = TypeFactory.createTextualType();
		t.setAnonymous( false );
		rs.add( t );
		when( query.getResultList() ).thenReturn( rs );

		CompositeType type = (CompositeType)parser( "ct { \"r\" : tx, \"s\" : tx, \"q\" : \"hello\" }").rule(); 
		assertFalse( type.getAnonymous() );
		assertTrue( type.listChildrenOrdered().get(0).getTarget().getAnonymous() );
		assertTrue( type.listChildrenOrdered().get(1).getTarget().getAnonymous() );
		assertFalse( type.listChildrenOrdered().get(2).getTarget().getAnonymous() );
	}			
	
	private TypeParser parser( String expr ) throws Exception {
		TypeLexer lex = new TypeLexer(new ANTLRInputStream( new ByteArrayInputStream( expr.getBytes() )));
		CommonTokenStream tokens = new CommonTokenStream(lex);

		TypeParser parser = new TypeParser(tokens);
		parser.setEntityManager( entityManager );
		
		return parser;
	}
	
	private List<Phenomenon> sortPhenomena(Set<Phenomenon> phenomena) {
		List<Phenomenon> result = new LinkedList<Phenomenon>( phenomena );
		Collections.sort(result, new Comparator<Phenomenon>() {

			@Override
			public int compare(Phenomenon o1, Phenomenon o2) {
				if(o1.getPosition() != null && o2.getPosition() != null) {
					return o1.getPosition().compareTo(o2.getPosition());
				}
				else {
					return o1.getName().compareTo(o2.getName());
				}
			}
		});
		
		return result;
	}
	
}
