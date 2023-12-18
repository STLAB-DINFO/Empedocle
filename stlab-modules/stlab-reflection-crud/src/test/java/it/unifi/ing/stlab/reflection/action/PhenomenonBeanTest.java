package it.unifi.ing.stlab.reflection.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.reflection.actions.wrappers.PhenomenonBean;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class PhenomenonBeanTest {

	protected Phenomenon ph;
	protected PhenomenonBean phBean;
	
	@Before
	public void setUp() {
		ph = PhenomenonFactory.createPhenomenon();
		phBean = new PhenomenonBean( ph );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testConstructorFail() {
		new PhenomenonBean( null );
	}

	@Test
	public void testName() {
		ph.setName( "Name1" );
		assertEquals( "Name1", phBean.getName() );

		ph.setName( "Name2" );
		assertEquals( "Name2", phBean.getName() );

		phBean.setName( "Name3" );
		assertEquals( "Name3", ph.getName() );

		phBean.setName( "Name4" );
		assertEquals( "Name4", ph.getName() );
	}

	@Test
	public void testLeft() {
		Date d1 = DateHelper.createDate( "2013-07-01" );
		Date d2 = DateHelper.createDate( "2013-07-02" );
		Date d3 = DateHelper.createDate( "2013-07-03" );
		Date d4 = DateHelper.createDate( "2013-07-04" );
	
		assertNull( phBean.getLeft() );
		
		ph.setTimeRange( new TimeRange( new Time( d1 ), new Time( null )));
		assertEquals( d1, phBean.getLeft() );

		ph.setTimeRange( new TimeRange( new Time( d2 ), new Time( null ) ));
		assertEquals( d2, phBean.getLeft() );
	
		phBean.setLeft( d3 );
		assertEquals( d3, ph.getTimeRange().getLeft().getDate());

		phBean.setLeft( d4 );
		assertEquals( d4, ph.getTimeRange().getLeft().getDate());
	}

	@Test
	public void testRight() {
		Date d1 = DateHelper.createDate( "2013-07-01" );
		Date d2 = DateHelper.createDate( "2013-07-02" );
		Date d3 = DateHelper.createDate( "2013-07-03" );
		Date d4 = DateHelper.createDate( "2013-07-04" );
	
		assertNull( phBean.getRight() );
		
		ph.setTimeRange( new TimeRange( new Time( null ) , new Time( d1 ) ));
		assertEquals( d1, phBean.getRight() );

		ph.setTimeRange( new TimeRange( new Time( null ), new Time( d2 ) ));
		assertEquals( d2, phBean.getRight() );
	
		phBean.setRight( d3 );
		assertEquals( d3, ph.getTimeRange().getRight().getDate());

		phBean.setRight( d4 );
		assertEquals( d4, ph.getTimeRange().getRight().getDate());
	}
	
}
