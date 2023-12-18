package it.unifi.ing.stlab.view.actions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.view.dao.ViewerDao;

public class ViewerListTest {

	protected ViewerList viewerList;
	protected ViewerDao viewerDao;
	protected ViewerFilter viewerFilter;

	@Before
	public void setUp() {
		viewerList = new ViewerList();
		viewerFilter = new ViewerFilter();

		viewerDao = mock( ViewerDao.class );
		when( viewerDao.count( (QueryBuilder) anyObject() ) ).thenReturn( new Integer( 510 ) );

		FieldUtils.assignField( viewerList, "viewerDao", viewerDao );
		FieldUtils.assignField( viewerList, "viewerFilter", viewerFilter );

		viewerList.init();
	}

	@Test
	public void testGetPageCount() {
		assertEquals( new Integer( 11 ), viewerList.getPageCount() );
	}

	@Test
	public void testGetResultList() {
		viewerList.getResultList();

		verify( viewerDao ).find( (QueryBuilder) anyObject(), anyInt(), anyInt() );
	}

}
