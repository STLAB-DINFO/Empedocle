package it.unifi.ing.stlab.empedocle.actions.health;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import it.unifi.ing.stlab.empedocle.actions.health.examination.types.ExaminationTypeFilter;
import it.unifi.ing.stlab.empedocle.actions.health.examination.types.ExaminationTypeList;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationTypeDao;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationTypeQueryBuilder;
import it.unifi.ing.stlab.test.FieldUtils;

import org.junit.Before;
import org.junit.Test;

public class ExaminationTypeListTest {

	protected ExaminationTypeList examTypeList;
	protected ExaminationTypeDao examTypeDao;
	protected ExaminationTypeFilter examTypeFilter;

	@Before
	public void setUp() {
		examTypeFilter = new ExaminationTypeFilter();
		examTypeList = new ExaminationTypeList();

		examTypeDao = mock( ExaminationTypeDao.class );

		when( examTypeDao.count( (ExaminationTypeQueryBuilder) anyObject() ) )
				.thenReturn( new Integer( 510 ) );

		FieldUtils.assignField( examTypeList, "examinationTypeDao", examTypeDao );
		FieldUtils.assignField( examTypeList, "examinationTypeFilter", examTypeFilter );

		examTypeList.init();
	}

	@Test
	public void testGetPageCount() {
		assertEquals( new Integer( 51 ), examTypeList.getPageCount() );
	}

	@Test
	public void testGetResultList() {
		examTypeList.getResultList();

		verify( examTypeDao ).find( (ExaminationTypeQueryBuilder) anyObject(), anyInt(), anyInt() );
	}
}
