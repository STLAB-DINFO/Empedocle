package it.unifi.ing.stlab.empedocle.model.health.drugs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Collections;

import org.junit.Test;

import it.unifi.ing.stlab.empedocle.factory.health.coding.atc.ATCCodeFactory;
import it.unifi.ing.stlab.empedocle.factory.health.drugs.DrugIndexFactory;
import it.unifi.ing.stlab.empedocle.model.health.coding.atc.ATCAnatomicalMainGroupCode;
import it.unifi.ing.stlab.empedocle.model.health.drugs.DrugClass;
import it.unifi.ing.stlab.empedocle.model.health.drugs.DrugIndex;
import it.unifi.ing.stlab.empedocle.model.health.drugs.DrugType;
import it.unifi.ing.stlab.empedocle.model.health.drugs.PrescriptionType;
import it.unifi.ing.stlab.empedocle.model.health.drugs.UpdateInfo;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.test.PersistenceTest;

public class DrugIndexJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override 
	protected void insertData() throws Exception {
		DrugIndex pfn = DrugIndexFactory.createDrugIndex();
		pfn.setBrandName( "Abba" );
		pfn.setPackaging( "12 Bust 875 mg+125 mg" );
		pfn.setManufacturer( "Fidia Farmaceutici Spa" );
		pfn.setDrugClass( DrugClass.A );
		pfn.setDrugType( DrugType.E );
		pfn.setPrescriptionType( PrescriptionType.RR );
		
		ATCAnatomicalMainGroupCode atc = ATCCodeFactory.createAnatomicalMainGroupCode();
		pfn.setAtc( atc );
		pfn.getAtc().setCode( "J01CR02" );
		pfn.getAtc().setDescription( "amoxicillin and enzyme inhibitor" );

		entityManager.persist( atc );
		
		pfn.setUpdateInfos( Collections.singleton( UpdateInfo.AAPT ));
		pfn.setPrice( new BigDecimal( "8.72" ) );
		
		pfn.setPhenomenon( PhenomenonFactory.createPhenomenon() );
		pfn.getPhenomenon().setName( pfn.getAtc().getDescription() + "(" + pfn.getPackaging() + ")");
		
		entityManager.persist( pfn );

		uuid = pfn.getUuid();
	}
	
	@Test
	public void test() {
		DrugIndex pfn = (DrugIndex)
			entityManager
				.createQuery( 
					"select p " +
					" from DrugIndex p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( pfn );
		
		assertEquals( "Abba", pfn.getBrandName() );
		assertEquals( "12 Bust 875 mg+125 mg", pfn.getPackaging() );
		assertEquals( "Fidia Farmaceutici Spa", pfn.getManufacturer() );
		assertEquals( DrugClass.A, pfn.getDrugClass() );
		assertEquals( DrugType.E, pfn.getDrugType() );
		assertEquals( PrescriptionType.RR, pfn.getPrescriptionType() );
		assertEquals( 1, pfn.getUpdateInfos().size() );
		assertEquals( UpdateInfo.AAPT, pfn.getUpdateInfos().iterator().next() );
		assertEquals( new BigDecimal( "8.72" ), pfn.getPrice());
	}
}
