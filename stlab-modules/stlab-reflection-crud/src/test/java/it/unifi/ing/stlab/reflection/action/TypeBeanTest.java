package it.unifi.ing.stlab.reflection.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import it.unifi.ing.stlab.reflection.actions.wrappers.TypeBean;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

public class TypeBeanTest {

	private EntityManager entityManager;
	
	@Before
	public void setUp() {
		entityManager = mock( EntityManager.class );
	}

	@Test
	public void testConstructorNoEntityManager() {
		new TypeBean( null, TypeFactory.createTextualType() );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testConstructorNoType() {
		new TypeBean( entityManager, null );
	}
	
	@Test
	public void testTextual() {
		TypeBean typeBean1 = new TypeBean( entityManager, TypeFactory.createTextualType() );
		assertTrue( typeBean1.isTextual() );
		assertNotNull( typeBean1.getTextualType() );
		
		TypeBean typeBean2 = new TypeBean( entityManager, TypeFactory.createEnumeratedType() );
		assertFalse( typeBean2.isTextual() );
		assertNull( typeBean2.getTextualType() );

		TypeBean typeBean3 = new TypeBean( entityManager, TypeFactory.createQueriedType() );
		assertFalse( typeBean3.isTextual() );
		assertNull( typeBean3.getTextualType() );

		TypeBean typeBean4 = new TypeBean( entityManager, TypeFactory.createQuantitativeType() );
		assertFalse( typeBean4.isTextual() );
		assertNull( typeBean4.getTextualType() );
	
		TypeBean typeBean5 = new TypeBean( entityManager, TypeFactory.createCompositeType() );
		assertFalse( typeBean5.isTextual() );
		assertNull( typeBean5.getTextualType() );
	}

	
	@Test
	public void testEnumerated() {
		TypeBean typeBean1 = new TypeBean( entityManager, TypeFactory.createTextualType() );
		assertFalse( typeBean1.isEnumerated() );
		assertNull( typeBean1.getEnumeratedType() );
		assertNotNull( typeBean1.getPhenomena() );
		
		TypeBean typeBean2 = new TypeBean( entityManager, TypeFactory.createEnumeratedType() );
		assertTrue( typeBean2.isEnumerated() );
		assertNotNull( typeBean2.getEnumeratedType() );
		assertNotNull( typeBean2.getPhenomena() );

		TypeBean typeBean3 = new TypeBean( entityManager, TypeFactory.createQueriedType() );
		assertFalse( typeBean3.isEnumerated() );
		assertNull( typeBean3.getEnumeratedType() );
		assertNotNull( typeBean3.getPhenomena() );

		TypeBean typeBean4 = new TypeBean( entityManager, TypeFactory.createQuantitativeType() );
		assertFalse( typeBean4.isEnumerated() );
		assertNull( typeBean4.getEnumeratedType() );
		assertNotNull( typeBean4.getPhenomena() );
	
		TypeBean typeBean5 = new TypeBean( entityManager, TypeFactory.createCompositeType() );
		assertFalse( typeBean5.isEnumerated() );
		assertNull( typeBean5.getEnumeratedType() );
		assertNotNull( typeBean5.getPhenomena() );
	}

	
	@Test
	public void testQueried() {
		TypeBean typeBean1 = new TypeBean( entityManager, TypeFactory.createTextualType() );
		assertFalse( typeBean1.isQueried() );
		assertNull( typeBean1.getQueriedType() );
		
		TypeBean typeBean2 = new TypeBean( entityManager, TypeFactory.createEnumeratedType() );
		assertFalse( typeBean2.isQueried() );
		assertNull( typeBean2.getQueriedType() );

		TypeBean typeBean3 = new TypeBean( entityManager, TypeFactory.createQueriedType() );
		assertTrue( typeBean3.isQueried() );
		assertNotNull( typeBean3.getQueriedType() );

		TypeBean typeBean4 = new TypeBean( entityManager, TypeFactory.createQuantitativeType() );
		assertFalse( typeBean4.isQueried() );
		assertNull( typeBean4.getQueriedType() );
	
		TypeBean typeBean5 = new TypeBean( entityManager, TypeFactory.createCompositeType() );
		assertFalse( typeBean5.isQueried() );
		assertNull( typeBean5.getQueriedType() );
	}
	
	@Test
	public void testQuantitative() {
		TypeBean typeBean1 = new TypeBean( entityManager, TypeFactory.createTextualType() );
		assertFalse( typeBean1.isQuantitative() );
		assertNull( typeBean1.getQuantitativeType() );
		assertNotNull( typeBean1.getUnits() );
		
		TypeBean typeBean2 = new TypeBean( entityManager, TypeFactory.createEnumeratedType() );
		assertFalse( typeBean2.isQuantitative() );
		assertNull( typeBean2.getQuantitativeType() );
		assertNotNull( typeBean2.getUnits() );

		TypeBean typeBean3 = new TypeBean( entityManager, TypeFactory.createQueriedType() );
		assertFalse( typeBean3.isQuantitative() );
		assertNull( typeBean3.getQuantitativeType() );
		assertNotNull( typeBean3.getUnits() );

		TypeBean typeBean4 = new TypeBean( entityManager, TypeFactory.createQuantitativeType() );
		assertTrue( typeBean4.isQuantitative() );
		assertNotNull( typeBean4.getQuantitativeType() );
		assertNotNull( typeBean4.getUnits() );
	
		TypeBean typeBean5 = new TypeBean( entityManager, TypeFactory.createCompositeType() );
		assertFalse( typeBean5.isQuantitative() );
		assertNull( typeBean5.getQuantitativeType() );
		assertNotNull( typeBean5.getUnits() );
	}

	@Test
	public void testComposite() {
		TypeBean typeBean1 = new TypeBean( entityManager, TypeFactory.createTextualType() );
		assertFalse( typeBean1.isComposite() );
		assertNull( typeBean1.getCompositeType() );
		assertNull( typeBean1.getTreeRoot() );
		
		TypeBean typeBean2 = new TypeBean( entityManager, TypeFactory.createEnumeratedType() );
		assertFalse( typeBean2.isComposite() );
		assertNull( typeBean2.getCompositeType() );
		assertNull( typeBean2.getTreeRoot() );

		TypeBean typeBean3 = new TypeBean( entityManager, TypeFactory.createQueriedType() );
		assertFalse( typeBean3.isComposite() );
		assertNull( typeBean3.getCompositeType() );
		assertNull( typeBean3.getTreeRoot() );

		TypeBean typeBean4 = new TypeBean( entityManager, TypeFactory.createQuantitativeType() );
		assertFalse( typeBean4.isComposite() );
		assertNull( typeBean4.getCompositeType() );
		assertNull( typeBean4.getTreeRoot() );
	
		TypeBean typeBean5 = new TypeBean( entityManager, TypeFactory.createCompositeType() );
		assertTrue( typeBean5.isComposite() );
		assertNotNull( typeBean5.getCompositeType() );
		assertNotNull( typeBean5.getTreeRoot() );
	}
	
}

