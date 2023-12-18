package it.unifi.ing.stlab.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.commons.util.NumberFormatUtils;

import org.junit.Test;

public class NumberFormatUtilsTest {
	
	@Test
	public void testIsNumeric() {
		NumberFormatUtils utils = new NumberFormatUtils();
		
		assertFalse(utils.isNumeric(null));
		
		String s = "123.123";
		assertTrue(utils.isNumeric(s));
		
		s = "ggg";
		assertFalse(utils.isNumeric(s));
		
		s = "123ggg";
		assertFalse(utils.isNumeric(s));
		
		s = "-123";
		assertTrue(utils.isNumeric(s));
		
		s = "+123";
		assertTrue(utils.isNumeric(s));
		
		s = "-+123";
		assertFalse(utils.isNumeric(s));
		
		s = "123+";
		assertFalse(utils.isNumeric(s));
		
		s = "123,123";
		assertFalse(utils.isNumeric(s));
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testFormatDoubleNull() {
		NumberFormatUtils utils = new NumberFormatUtils();
		
		String doubleNumber = null;
		utils.formatDouble(doubleNumber, 2);
	}
	
	@Test(expected = NumberFormatException.class)
	public void testFormatDoubleEmpty() {
		NumberFormatUtils utils = new NumberFormatUtils();
		
		String doubleNumber = "";
		utils.formatDouble(doubleNumber, 2);
	}
	
	@Test
	public void testFormatDouble() {
		NumberFormatUtils utils = new NumberFormatUtils();
		
		String doubleNumber = "1212.1";
		Double result = utils.formatDouble(doubleNumber, 2);
		assertEquals(new Double(1212.1), result);
		
		doubleNumber = "1212.1294";
		result = utils.formatDouble(doubleNumber, 2);
		assertEquals(new Double(1212.12), result);
		
		doubleNumber = "123456789.9999999";
		result = utils.formatDouble(doubleNumber, 2);
		assertEquals(new Double(123456789.99), result);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFormatDecimalsNull() {
		NumberFormatUtils utils = new NumberFormatUtils();
		
		Double number = null;
		utils.formatDecimals(number, 2, 3);
	}
	
	@Test
	public void testFormatDecimals() {
		NumberFormatUtils utils = new NumberFormatUtils();
		
		Double number = new Double(1234.987654321);
		String result = utils.formatDecimals(number, 0, 2);
		assertEquals("1234.98", result);
		result = utils.formatDecimals(number, 0, 10);
		assertEquals("1234.987654321", result);
		
		number = new Double(1234.9);
		result = utils.formatDecimals(number, 2, 4);
		assertEquals("1234.90", result);
		result = utils.formatDecimals(number, 1, 4);
		assertEquals("1234.9", result);
		result = utils.formatDecimals(number, 0, 4);
		assertEquals("1234.9", result);
		result = utils.formatDecimals(number, 0, 0);
		assertEquals("1234", result);
		
		number = new Double(1234);
		result = utils.formatDecimals(number, 2, 2);
		assertEquals("1234.00", result);
		result = utils.formatDecimals(number, 0, 2);
		assertEquals("1234", result);
		
	}
	
	@Test
	public void testFormatDecimalsString() {
		NumberFormatUtils utils = new NumberFormatUtils();
		
		String s = "123";
		String result = utils.formatDecimals(s, 2, 3);
		assertEquals("123.00", result);
		
		s = "123.321";
		result = utils.formatDecimals(s, 2, 2);
		assertEquals("123.32", result);
		
		s = "erefe";
		result = utils.formatDecimals(s, 2, 3);
		assertNull(result);
		
	}

}
