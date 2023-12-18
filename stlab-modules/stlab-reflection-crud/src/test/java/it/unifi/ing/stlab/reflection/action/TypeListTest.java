package it.unifi.ing.stlab.reflection.action;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.reflection.actions.TypeFilter;
import it.unifi.ing.stlab.reflection.actions.TypeList;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.dao.types.TypeQueryBuilder;
import it.unifi.ing.stlab.test.FieldUtils;

import org.junit.Before;
import org.junit.Test;

public class TypeListTest {

	protected TypeFilter typeFilter;
	protected TypeList typeList;
	protected TypeDao typeDao;
	
	@Before
	public void setUp() {
		typeFilter = new TypeFilter();
		typeList = new TypeList();
		typeDao = mock( TypeDao.class );
		when( typeDao.count( (TypeQueryBuilder) anyObject() )).thenReturn( new Integer( 567 ));
	
		FieldUtils.assignField(typeList, "typeFilter", typeFilter );
		FieldUtils.assignField(typeList, "typeDao", typeDao );
		
		typeList.init();
	}
	
	@Test
	public void testGetPageCount() {
		assertEquals( new Integer( 12 ), typeList.getPageCount() );
	}
	
	@Test
	public void testGetResultList() {
		typeList.getResultList();
		
		verify( typeDao ).find( (TypeQueryBuilder)anyObject(), anyInt(), anyInt() );
	}
}
