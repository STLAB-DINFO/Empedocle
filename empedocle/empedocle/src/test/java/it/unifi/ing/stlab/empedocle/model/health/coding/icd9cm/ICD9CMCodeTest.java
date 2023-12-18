package it.unifi.ing.stlab.empedocle.model.health.coding.icd9cm;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import it.unifi.ing.stlab.empedocle.factory.health.coding.icd9cm.ICD9CMCodeFactory;

import org.junit.Test;

public class ICD9CMCodeTest {

	@Test
	public void testParentTopicCodeNull() {
		ICD9CMTopicCode code = ICD9CMCodeFactory.createTopicCode();
		assertNull(code.getParent());

		code.setParent(code);
		assertNull(code.getParent());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSubTopicCodeIllegalParent() {
		ICD9CMSubTopicCode code = ICD9CMCodeFactory.createSubTopicCode();
		assertNull(code.getParent());

		code.setParent(code);
	}
	
	@Test
	public void testParentSubTopicCode() {
		ICD9CMSubTopicCode code = ICD9CMCodeFactory.createSubTopicCode();
		assertNull(code.getParent());

		code.setParent( ICD9CMCodeFactory.createTopicCode() );
		assertNotNull(code.getParent());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCategoryCodeIllegalParent() {
		ICD9CMCategoryCode code = ICD9CMCodeFactory.createCategoryCode();
		assertNull(code.getParent());

		code.setParent(code);
	}
	
	@Test
	public void testParentCategoryCode() {
		ICD9CMCategoryCode code = ICD9CMCodeFactory.createCategoryCode();
		assertNull(code.getParent());

		code.setParent( ICD9CMCodeFactory.createSubTopicCode() );
		assertNotNull(code.getParent());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSubCategoryCodeIllegalParent() {
		ICD9CMSubCategoryCode code = ICD9CMCodeFactory.createSubCategoryCode();
		assertNull(code.getParent());

		code.setParent(code);
	}
	
	@Test
	public void testParentSubCategoryCode() {
		ICD9CMSubCategoryCode code = ICD9CMCodeFactory.createSubCategoryCode();
		assertNull(code.getParent());

		code.setParent( ICD9CMCodeFactory.createCategoryCode() );
		assertNotNull(code.getParent());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSubClassificationCodeIllegalParent() {
		ICD9CMSubClassificationCode code = ICD9CMCodeFactory.createSubClassificationCode();
		assertNull(code.getParent());

		code.setParent(code);
	}
	
	@Test
	public void testParentSubClassificationCode() {
		ICD9CMSubClassificationCode code = ICD9CMCodeFactory.createSubClassificationCode();
		assertNull(code.getParent());

		code.setParent( ICD9CMCodeFactory.createSubCategoryCode() );
		assertNotNull(code.getParent());
	}
	
	@Test
	public void testRootCode() {
		ICD9CMSubClassificationCode code = ICD9CMCodeFactory.createSubClassificationCode();
		assertNull(code.getRoot());

		code.setRoot( ICD9CMCodeFactory.createTopicCode() );
		assertNotNull(code.getRoot());
	}
	
}
