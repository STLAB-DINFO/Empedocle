package it.unifi.ing.stlab.empedocle.model.health.coding.atc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import it.unifi.ing.stlab.empedocle.factory.health.coding.atc.ATCCodeFactory;

import org.junit.Test;

public class ATCCodeTest {

	@Test
	public void testParentAnatomicalCodeNull() {
		ATCAnatomicalMainGroupCode code = ATCCodeFactory.createAnatomicalMainGroupCode();
		assertNull(code.getParent());

		code.setParent(code);
		assertNull(code.getParent());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTherapeuticCodeIllegalParent() {
		ATCTherapeuticMainGroupCode code = ATCCodeFactory.createTherapeuticMainGroupCode();
		assertNull(code.getParent());

		code.setParent(code);
	}
	
	@Test
	public void testParentTherapeuticCode() {
		ATCTherapeuticMainGroupCode code = ATCCodeFactory.createTherapeuticMainGroupCode();
		assertNull(code.getParent());

		code.setParent( ATCCodeFactory.createAnatomicalMainGroupCode() );
		assertNotNull(code.getParent());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPharmacologicalCodeIllegalParent() {
		ATCPharmacologicalSubGroupCode code = ATCCodeFactory.createPharmacologicalSubGroupCode();
		assertNull(code.getParent());

		code.setParent(code);
	}
	
	@Test
	public void testParentPharmacologicalCode() {
		ATCPharmacologicalSubGroupCode code = ATCCodeFactory.createPharmacologicalSubGroupCode();
		assertNull(code.getParent());

		code.setParent( ATCCodeFactory.createTherapeuticMainGroupCode() );
		assertNotNull(code.getParent());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testChemicalSubGroupCodeIllegalParent() {
		ATCChemicalSubGroupCode code = ATCCodeFactory.createChemicalSubGroupCode();
		assertNull(code.getParent());

		code.setParent(code);
	}
	
	@Test
	public void testParentChemicalSubGroupCode() {
		ATCChemicalSubGroupCode code = ATCCodeFactory.createChemicalSubGroupCode();
		assertNull(code.getParent());

		code.setParent( ATCCodeFactory.createPharmacologicalSubGroupCode() );
		assertNotNull(code.getParent());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testChemicalSubstanceCodeIllegalParent() {
		ATCChemicalSubstanceCode code = ATCCodeFactory.createChemicalSubstanceCode();
		assertNull(code.getParent());

		code.setParent(code);
	}
	
	@Test
	public void testParentChemicalSubstanceCode() {
		ATCChemicalSubstanceCode code = ATCCodeFactory.createChemicalSubstanceCode();
		assertNull(code.getParent());

		code.setParent( ATCCodeFactory.createChemicalSubGroupCode() );
		assertNotNull(code.getParent());
	}
}
