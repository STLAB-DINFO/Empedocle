package it.unifi.ing.stlab.empedocle.visitor.fact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.empedocle.actions.health.examination.BasicFactTest;
import it.unifi.ing.stlab.empedocle.visitor.fact.tools.EmptyFactVisitor;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.visitor.fact.FactCopyVisitor;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;

import org.junit.Before;
import org.junit.Test;

public class FactResumeVisitorTest extends BasicFactTest {
	
	private FactResumeVisitor visitor;
	private Fact original;
	
	@Override
	@Before
	public void setUp() {
		super.setUp();
		
	}
	
	@Test
	public void testCompositeSimple() {
		original = createEmptyCopy(cmp);
		visitor = new FactResumeVisitor(original);
		cmp.accept(visitor);
		
		assertEquals(2, original.listChildrenOrdered().size());
		assertEquals("testo1", ((TextualFact)original.listChildrenOrdered().get(1).getTarget()).getText());
		assertEquals("testo2", ((TextualFact)original.listChildrenOrdered().get(0).getTarget()).getText());

	}
	
	@Test
	public void testComposite2Lvl() {
		original = createEmptyCopy(root);
		visitor = new FactResumeVisitor(original);
		root.accept(visitor);
		
		assertEquals(4, ((FactImpl)original).listDescendents().size());
		assertEquals("testo1", ((TextualFact)original.listChildrenOrdered().iterator().next().getTarget().listChildrenOrdered().get(1).getTarget()).getText());
		assertEquals("testo2", ((TextualFact)original.listChildrenOrdered().iterator().next().getTarget().listChildrenOrdered().get(0).getTarget()).getText());
		
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
