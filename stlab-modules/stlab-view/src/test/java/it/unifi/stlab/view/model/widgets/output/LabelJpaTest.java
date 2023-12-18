package it.unifi.stlab.view.model.widgets.output;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.widgets.output.Label;

import java.util.UUID;

import org.junit.Test;

public class LabelJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		Label label = new Label(uuid);
		label.setName("nome");
		label.setValue("value");
		entityManager.persist(label);
		
	}
	
	@Test
	public void testLabel(){
		Label label = (Label)entityManager
								.createQuery("select l from Label l where l.uuid = :uuid")
								.setParameter("uuid", uuid) 
								.getSingleResult();
		
		assertNotNull(label);
		assertEquals("nome", label.getName());
		assertEquals("value", label.getValue());
		
	}

}
