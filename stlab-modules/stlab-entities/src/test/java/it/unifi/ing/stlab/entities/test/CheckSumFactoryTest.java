package it.unifi.ing.stlab.entities.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.utils.CheckSumFactory;

import org.junit.Test;

public class CheckSumFactoryTest {

	@Test
	public void testCheckSum1() {
		String cs1 = CheckSumFactory.createCheckSum( "Hello world!" );
		String cs2 = CheckSumFactory.createCheckSum( " Hello world!" );
		String cs3 = CheckSumFactory.createCheckSum( "Hello world! " );
		String cs4 = CheckSumFactory.createCheckSum( "HELLO WORLD!" );
		String cs5 = CheckSumFactory.createCheckSum( "hello world!" );

		assertTrue( cs1.equals( cs2 ));
		assertTrue( cs1.equals( cs3 ));
		assertTrue( cs1.equals( cs4 ));
		assertTrue( cs1.equals( cs5 ));
	}

	@Test
	public void testCheckSum2() {
		String cs1 = CheckSumFactory.createCheckSum( "Hello" );
		String cs2 = CheckSumFactory.createCheckSum( " World" );
		
		assertFalse( cs1.equals( cs2 ));
	}

	@Test
	public void testCheckSum3() {
		String cs1 = CheckSumFactory.createCheckSum( "         " );
		String cs2 = CheckSumFactory.createCheckSum( "" );
		String cs3 = CheckSumFactory.createCheckSum( (String) null );

		assertTrue( cs1.equals( cs2 ));
		assertFalse( cs1.equals( cs3 ));
	}
	
	@Test
	public void testCheckSum4() {
		String cs1 = CheckSumFactory.createCheckSum( "Hello World" );
		String cs2 = CheckSumFactory.createCheckSum( "", "Hello World" );
		
		assertFalse( cs1.equals( cs2 ));
	}

	@Test
	public void testCheckSum5() {
		String cs1 = CheckSumFactory.createCheckSum( "1" );
		String cs2 = CheckSumFactory.createCheckSum( new Integer( 1 ));
		String cs3 = CheckSumFactory.createCheckSum( new Long( 2 ) );
		
		assertFalse( cs1.equals( cs2 ));
		assertFalse( cs1.equals( cs3 ));
		assertFalse( cs2.equals( cs3 ));
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testCheckSumFail() {
		CheckSumFactory.createCheckSum();
	}
	
}
