package it.unifi.ing.stlab.empedocle.model.health.drugs;

import it.unifi.ing.stlab.empedocle.factory.health.drugs.DrugIndexFactory;
import it.unifi.ing.stlab.empedocle.model.health.coding.atc.ATCCode;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.test.JpaTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class DrugIndexImporter extends JpaTest {
	
	private final Map<String, ATCCode> atc_cache = new HashMap<>();

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
		URL url = Thread.currentThread().getContextClassLoader().getResource("drug_index/pfn.csv");
		Scanner sc = new Scanner( new File( url.getPath() ) );
		sc.nextLine(); //skip head
		
		while ( sc.hasNextLine() ) {
			DrugIndex drugIndex = DrugIndexFactory.createDrugIndex();
			String line = sc.nextLine();
			
			Scanner scl = new Scanner(line);
			scl.useDelimiter("#");
			
			drugIndex.setPackaging( scl.next().trim() );
			drugIndex.setBrandName( scl.next().trim() );
			scl.next().trim(); // skip active ingredient field
			drugIndex.setDrugClass( DrugClass.valueOf( scl.next().trim() ) );
			
			String notes = scl.next().trim();
			if ( !notes.isEmpty() )
				drugIndex.setAifaNotes( extractAifaNotes( notes ) );
			
			String infos = scl.next().trim();
			if( !infos.isEmpty() ) {
				drugIndex.setUpdateInfos( extractUpdateInfos( infos ) );
			}
			
			drugIndex.setPrescriptionType( PrescriptionType.valueOf( scl.next().trim() ) );
			drugIndex.setDrugType( DrugType.valueOf( scl.next().trim() ) );
			drugIndex.setAtc( findATCCode( scl.next().trim() ) );
			drugIndex.setAic( extractAic( scl.next().trim() ) );
			drugIndex.setPrice( new BigDecimal( scl.next().trim() ) );
			drugIndex.setManufacturer( scl.next().trim() );

			scl.close();
			
			Phenomenon ph = PhenomenonFactory.createPhenomenon();
			ph.setName( drugIndex.getAtc().getDescription() 
					+ " (" + drugIndex.getPackaging() + ")" );
			entityManager.persist( ph );

			drugIndex.setPhenomenon( ph );
			entityManager.persist( drugIndex );
		}	
		sc.close();
	}
	
	private String extractAic( String code ) {
		while( code.length() < 9 ) {
			code = "0" + code;
		}
		
		return code;
	}

//	private <E extends Enum<E>> E getEnumConstant( Class<E> clazz, String note ) {
//		return Enum.valueOf( clazz, note );
//	}

	private ATCCode findATCCode( String code ) {
		if( code == null ) 
			throw new RuntimeException();
		
		if( atc_cache.containsKey( code ) )
			return atc_cache.get( code );
		else {
			ATCCode result = (ATCCode) entityManager
					.createQuery( "select a " + " from ATCCode a " + " where a.code = :code" )
					.setParameter( "code", code ).getSingleResult();
			atc_cache.put( code, result );
			
			return result;
		}
	}
	
	private Set<AifaNote> extractAifaNotes( String notes ) {
		Set<AifaNote> result = new HashSet<AifaNote>();
		String[] split = notes.split( Pattern.quote( "+" ) );
		
		for( int i = 0; i < split.length; i++ ) {
			result.add( AifaNote.valueOf( "N" + split[i].trim() ) );
		}
		
		return result;
	}

	private Set<UpdateInfo> extractUpdateInfos( String infos ) {
		Set<UpdateInfo> result = new HashSet<UpdateInfo>();
		String[] split = infos.split( "," );
		
		for( int i = 0; i < split.length; i++ ) {
			result.add( UpdateInfo.valueOf( split[i].trim() ) );
		}
		
		return result;
	}
}
