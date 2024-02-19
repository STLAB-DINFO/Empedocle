package it.unifi.ing.stlab.empedocle.model.health.coding.atc;

import it.unifi.ing.stlab.empedocle.factory.health.coding.atc.ATCCodeFactory;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.test.JpaTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

public class ATCCodeJpaImporter extends JpaTest {
	
	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "test_reflection" );
	}

	
	@Test
	public void test() throws FileNotFoundException {
		entityManager.getTransaction().begin();
        insertData();
        entityManager.getTransaction().commit();
	}


	private void insertData() throws FileNotFoundException {
		URL url = Thread.currentThread().getContextClassLoader().getResource("atc/atc.csv");
		Scanner sc = new Scanner( new File( url.getPath() ) );
		
		sc.nextLine(); //skip head
		
		ATCAnatomicalMainGroupCode anatomical_code = null;
		ATCTherapeuticMainGroupCode therapeutic_code = null;
		ATCPharmacologicalSubGroupCode pharmacological_code = null;
		ATCChemicalSubGroupCode chemicalgroup_code = null;
		ATCChemicalSubstanceCode chemicalsubstance_code = null;
		
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
//			System.out.println(line);

			Scanner scl = new Scanner(line);
			scl.useDelimiter("#");
			
			String code = scl.next().trim();
			String description = scl.next().trim();
			int level = Integer.parseInt( scl.next().trim() );

			scl.close();
			
			ATCCode code_to_persist = null;
			
			switch ( level ) {
			case 1:
				anatomical_code = ATCCodeFactory.createAnatomicalMainGroupCode();
				code_to_persist = anatomical_code;
				break;
			case 2:
				therapeutic_code = ATCCodeFactory.createTherapeuticMainGroupCode();
				therapeutic_code.setParent(anatomical_code);
//				therapeutic_code.setRoot(anatomical_code);
				code_to_persist = therapeutic_code;
				break;
			case 3:
				pharmacological_code = ATCCodeFactory.createPharmacologicalSubGroupCode();
				pharmacological_code.setParent(therapeutic_code);
//				pharmacological_code.setRoot(anatomical_code);
				code_to_persist = pharmacological_code;
				break;
			case 4:
				chemicalgroup_code = ATCCodeFactory.createChemicalSubGroupCode();
				chemicalgroup_code.setParent(pharmacological_code);
//				chemicalgroup_code.setRoot(anatomical_code);
				code_to_persist = chemicalgroup_code;
				break;
			case 5:
				chemicalsubstance_code = ATCCodeFactory.createChemicalSubstanceCode();
				chemicalsubstance_code.setParent(chemicalgroup_code);
//				chemicalsubstance_code.setRoot(anatomical_code);
				code_to_persist = chemicalsubstance_code;
				break;
			default:
				break;
			}
			
			code_to_persist.setCode(code);
			code_to_persist.setDescription(description);
			
			if(!check(code_to_persist))
				throw new RuntimeException("error in check");
			
			System.out.println(
					"code: " + code_to_persist.getCode() + 
					"; parent: " + (code_to_persist.getParent() != null ? code_to_persist.getParent().getCode() : ""));
//					"; root: " + (code_to_persist.getRoot() != null ? code_to_persist.getRoot().getCode() : ""));
			
			Phenomenon ph = PhenomenonFactory.createPhenomenon();
			ph.setName( code + " - " + description );
			entityManager.persist(ph);

			code_to_persist.setPhenomenon(ph);
			entityManager.persist( code_to_persist );
		}	
		sc.close();
	}


	private boolean check( ATCCode code_to_check ) {
		
		if( code_to_check.getDescription().length() > 255 )
			return false;
		
		if( code_to_check instanceof ATCAnatomicalMainGroupCode )
			return true;
		
		String code = code_to_check.getCode();
		String p_code = code_to_check.getParent().getCode();
		
		if( code_to_check instanceof ATCTherapeuticMainGroupCode )
			return p_code.equals( code.substring(0, code.length() - 2) ) ? true : false;
		
		else if( code_to_check instanceof ATCPharmacologicalSubGroupCode )
			return p_code.equals( code.substring(0, code.length() - 1)) ? true : false;

		else if( code_to_check instanceof ATCChemicalSubGroupCode )
			return p_code.equals( code.substring(0, code.length() - 1)) ? true : false;

		else if( code_to_check instanceof ATCChemicalSubstanceCode )
			return p_code.equals( code.substring(0, code.length() - 2)) ? true : false;

		else
			return false;
	}
}
