package it.unifi.ing.stlab.reflection.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.reflection.actions.wrappers.UnitUseBean;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitUseFactory;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

public class UnitUseBeanTest {

	protected EntityManager entityManager;
	protected Unit u1;
	protected Unit u2;
	protected UnitUse uu;
	protected UnitUseBean uuBean;
	
	@Before
	public void setUp() {
		u1 = UnitFactory.createUnit();
		u2 = UnitFactory.createUnit();
		
		List<Unit> resultList = new ArrayList<Unit>();
		resultList.add( u2 );
		
		Query query = mock( Query.class );
		when( query.getResultList() ).thenReturn( resultList );
		when( query.setParameter( anyString(), anyObject() )).thenReturn( query );
		
		entityManager = mock( EntityManager.class );
		when( entityManager.createQuery( anyString() )).thenReturn( query );
		
		uu = UnitUseFactory.createUnitUse();
		uu.setUnit( u1 );
		
		uuBean = new UnitUseBean( entityManager, uu );
	}
	
	@Test
	public void testConstructorNoEntityManager() {
		new UnitUseBean( null, uu );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testConstructorNoUnitUse() {
		new UnitUseBean( entityManager, null );
	}

	@Test
	public void testDigits() {
		uu.setDigits( new Integer( 1 ));
		assertEquals( new Integer( 1 ), uuBean.getDigits() );

		uu.setDigits( new Integer( 2 ));
		assertEquals( new Integer( 2 ), uuBean.getDigits() );
	
		uuBean.setDigits( new Integer( 3 ));
		assertEquals( new Integer( 3 ), uu.getDigits() );

		uuBean.setDigits( new Integer( 4 ));
		assertEquals( new Integer( 4 ), uu.getDigits() );
	}

	@Test
	public void testDecimals() {
		uu.setDecimals( new Integer( 1 ));
		assertEquals( new Integer( 1 ), uuBean.getDecimals() );

		uu.setDecimals( new Integer( 2 ));
		assertEquals( new Integer( 2 ), uuBean.getDecimals() );
	
		uuBean.setDecimals( new Integer( 3 ));
		assertEquals( new Integer( 3 ), uu.getDecimals() );

		uuBean.setDecimals( new Integer( 4 ));
		assertEquals( new Integer( 4 ), uu.getDecimals() );
	}
	
	@Test
	public void testGetUnitUuid() {
		assertEquals( u1.getUuid(), uuBean.getUnitUuid() );
	
		uu.setUnit( u2 );
		assertEquals( u2.getUuid(), uuBean.getUnitUuid() );
	}
	
	@Test
	public void testSetUnitUuid() {
		uuBean.setUnitUuid( u2.getUuid() );
		assertEquals( u2, uuBean.getUnitUse().getUnit() );
		
		uuBean.setUnitUuid( null );
		assertNull( uuBean.getUnitUse().getUnit() );
	}

}

