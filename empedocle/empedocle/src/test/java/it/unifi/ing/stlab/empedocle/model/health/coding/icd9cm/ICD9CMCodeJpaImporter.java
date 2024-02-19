package it.unifi.ing.stlab.empedocle.model.health.coding.icd9cm;

import it.unifi.ing.stlab.empedocle.factory.health.coding.icd9cm.ICD9CMCodeFactory;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.test.JpaTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ICD9CMCodeJpaImporter extends JpaTest {
	
	private static final String path = "/Users/fulvio/Desktop/RACE/Codifiche/ICD9-CM-ITA07/import-empedocle/icd9cm07ita-diagnosi.csv";
//	private static final String path = "/Users/fulvio/Desktop/RACE/Codifiche/ICD9-CM-ITA07/import-empedocle/icd9cm07ita-diagnosi-serieV.csv";
//	private static final String path = "/Users/fulvio/Desktop/RACE/Codifiche/ICD9-CM-ITA07/import-empedocle/icd9cm07ita-diagnosi-serieE.csv";

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
		Scanner sc = new Scanner( new File(path));
		
		sc.nextLine(); //skip head
		
		ICD9CMTopicCode topic_code = null;
		ICD9CMSubTopicCode subtopic_code = null;
		ICD9CMCategoryCode category_code = null;
		ICD9CMSubCategoryCode subcategory_code = null;
		ICD9CMSubClassificationCode subclassification_code = null;
		
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
//			System.out.println(line);

			Scanner scl = new Scanner(line);
			scl.useDelimiter("#");
			
			scl.next().trim();
			String description = scl.next().trim();
			int level = Integer.parseInt( scl.next().trim() );
			String code = scl.next().trim();

			scl.close();
			
			ICD9CMCode code_to_persist = null;
			
			switch ( level ) {
			case 1:
				topic_code = ICD9CMCodeFactory.createTopicCode();
				code_to_persist = topic_code;
				break;
			case 2:
				subtopic_code = ICD9CMCodeFactory.createSubTopicCode();
				subtopic_code.setParent(topic_code);
				subtopic_code.setRoot(topic_code);
				code_to_persist = subtopic_code;
				break;
			case 3:
				category_code = ICD9CMCodeFactory.createCategoryCode();
				category_code.setParent(subtopic_code);
				category_code.setRoot(topic_code);
				code_to_persist = category_code;
				break;
			case 4:
				subcategory_code = ICD9CMCodeFactory.createSubCategoryCode();
				subcategory_code.setParent(category_code);
				subcategory_code.setRoot(topic_code);
				code_to_persist = subcategory_code;
				break;
			case 5:
				subclassification_code = ICD9CMCodeFactory.createSubClassificationCode();
				subclassification_code.setParent(subcategory_code);
				subclassification_code.setRoot(topic_code);
				code_to_persist = subclassification_code;
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
					"; parent: " + (code_to_persist.getParent() != null ? code_to_persist.getParent().getCode() : "")  +
					"; root: " + (code_to_persist.getRoot() != null ? code_to_persist.getRoot().getCode() : ""));
			
			Phenomenon ph = PhenomenonFactory.createPhenomenon();
			ph.setName( code + " - " + description );
			entityManager.persist(ph);

			code_to_persist.setPhenomenon(ph);
			entityManager.persist( code_to_persist );
		}	
		sc.close();
	}


	private boolean check(ICD9CMCode code_to_check) {
		
		if(code_to_check.getDescription().length() > 255)
			return false;
		
		if(code_to_check instanceof ICD9CMTopicCode)
			return true;
		
		String code = code_to_check.getCode();
		String p_code = code_to_check.getParent().getCode();
		if( code_to_check instanceof ICD9CMSubTopicCode ) {
			String[] split_code = code.split( "-" );
			String[] split_p_code = p_code.split( "-" );

            return split_code[0].compareTo(split_p_code[0]) >= 0
                    && split_code[1].compareTo(split_p_code[1]) <= 0;
		} else if( code_to_check instanceof ICD9CMCategoryCode ) {
			String[] split_p_code = p_code.split( "-" );

            return code.compareTo(split_p_code[0]) >= 0
                    && code.compareTo(split_p_code[1]) <= 0;
		} else if( code_to_check instanceof ICD9CMSubCategoryCode ) {
			code = code.substring(0, code.length() - 2);

            return code.compareTo(p_code) == 0;
		} else if( code_to_check instanceof ICD9CMSubClassificationCode ) {
			code = code.substring(0, code.length() - 1);
            return code.compareTo(p_code) == 0;
		} else
			return false;
	}
}
