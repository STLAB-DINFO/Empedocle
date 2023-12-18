package it.unifi.ing.stlab.empedocle.actions.health.examination;

import it.unifi.ing.stlab.empedocle.view.controllers.cardiology.FolderNumberGenerator;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class FolderNumberGeneratorBean implements FolderNumberGenerator {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public synchronized void generateFolderNumber(Fact f) {
		
		if(ClassHelper.instanceOf(f, QuantitativeFact.class)) {
		
			QuantitativeFact folderNumber = ClassHelper.cast(f, QuantitativeFact.class);
		
			String maxQuery = "SELECT MAX(f.quantity.value) " +
								"FROM FactImpl f " +				// usando Fact e non QuantitativeFact evito che nella query ci sia 'from_class'
								"WHERE f.type = :type " +			// type_id
								"AND f.status != :notStatus " +		// status
								"AND f.destination is null " +		// dest_id
								"AND f.quantity.value is not null";	// qt_value
			
			// usare indice su typeid, status, dest_id, qt_value ed eliminare indice sul solo type_id
			
//			select max(fact0_.qt_value) as col_0_0_ from facts fact0_ where fact0_.type_id=306
//			and fact0_.status<>'REFUSED' and (fact0_.dest_id is null) and (fact0_.qt_value is not null);

			Double result = (Double) entityManager.createQuery(maxQuery)
								.setParameter("type", f.getType())
								.setParameter("notStatus", FactStatus.REFUSED)
								.getSingleResult();
		
			if(result != null) {
				updateFolderNumber(folderNumber, result + 1);
			}
			else{
				updateFolderNumber(folderNumber, 1D);
			}
		
		}
		
	}
	
	private void updateFolderNumber(QuantitativeFact folderNumber, Double value) {
		folderNumber.getQuantity().setValue( value );
		entityManager.merge(folderNumber);
		entityManager.flush();
	}


}
