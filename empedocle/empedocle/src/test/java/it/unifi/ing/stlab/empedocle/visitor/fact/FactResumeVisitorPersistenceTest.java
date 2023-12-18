package it.unifi.ing.stlab.empedocle.visitor.fact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import it.unifi.ing.stlab.empedocle.visitor.fact.tools.EmptyFactVisitor;
import it.unifi.ing.stlab.entities.utils.FieldHelper;
import it.unifi.ing.stlab.reflection.impl.dao.FactDao;
import it.unifi.ing.stlab.reflection.impl.dao.FactDaoBean;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.visitor.fact.FactCopyVisitor;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.test.JpaTest;

//XXX check
@Ignore
public class FactResumeVisitorPersistenceTest extends JpaTest {
	
	private FactResumeVisitor visitor;
	private Fact source;
	private Fact destination;
	
	private FactDao factDao;
	
	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "test_mysql" );
	}
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		factDao = new FactDaoBean();
		FieldHelper.write(factDao, "entityManager", entityManager);
		
		entityManager.clear();
		entityManager.getTransaction().begin();
		
		source = factDao.findByContextId( new Long(181897), new Long(568) );
		factDao.fetchById( ((FactImpl)source).getId() );
		
		destination = createEmptyCopy(source);
		assertEquals(((FactImpl)source).listDescendents().size(), ((FactImpl)destination).listDescendents().size());
		
	}
	
	@Test
	public void testVisitComposite() {
		visitor = new FactResumeVisitor(destination);
		source.accept(visitor);
		
		List<? extends FactLink> fattoriDiRischio = destination.listChildrenOrdered().get(0).getTarget().listChildrenOrdered().get(2).getTarget().listChildrenOrdered();
		
		Fact diabete = fattoriDiRischio.get(0).getTarget().listChildrenOrdered().get(0).getTarget();
		assertEquals("Si", ((QualitativeFact)diabete).getPhenomenon().getName());
		
		QuantitativeFact diabete_daQuanto = (QuantitativeFact)fattoriDiRischio.get(0).getTarget().listChildrenOrdered().get(1).getTarget();
		assertNotNull(diabete_daQuanto.getQuantity().getUnit());
		assertEquals(111, diabete_daQuanto.getQuantity().getValue().intValue());
		
		QualitativeFact insulino = (QualitativeFact)fattoriDiRischio.get(0).getTarget().listChildrenOrdered().get(2).getTarget();
		assertEquals("No", insulino.getPhenomenon().getName());
		
		QualitativeFact iperteso = (QualitativeFact)fattoriDiRischio.get(1).getTarget().listChildrenOrdered().get(0).getTarget();
		assertEquals("No", iperteso.getPhenomenon().getName());
		
		QualitativeFact fumo = (QualitativeFact)fattoriDiRischio.get(2).getTarget();
		assertEquals("Si", fumo.getPhenomenon().getName());
		
		QuantitativeFact iperteso_daQuanto = (QuantitativeFact)fattoriDiRischio.get(1).getTarget().listChildrenOrdered().get(1).getTarget();
		assertNotNull(iperteso_daQuanto.getQuantity().getUnit());
		assertEquals(123, iperteso_daQuanto.getQuantity().getValue().intValue());
		
		QualitativeFact allergico = (QualitativeFact)fattoriDiRischio.get(8).getTarget().listChildrenOrdered().get(0).getTarget();
		assertEquals("Si", allergico.getPhenomenon().getName());
		
		TextualFact acosa = (TextualFact)fattoriDiRischio.get(8).getTarget().listChildrenOrdered().get(1).getTarget();
		assertEquals("a tutto", acosa.getText());

		entityManager.getTransaction().commit();
		
	}
	
	private Fact createEmptyCopy(Fact src) {
		FactCopyVisitor cv = new FactCopyVisitor();
		src.accept(cv);
		Fact result = cv.getResult();
		
		EmptyFactVisitor ev = new EmptyFactVisitor();
		result.accept(ev);
		
		for(FactLink fl : result.listChildren()) {
			assertTrue(fl.getTarget().isEmpty());
		}
		
		return result;
		
	}

}
