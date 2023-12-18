package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StatisticsPathGeneratorTest extends BaseStatisticsTest {
	
	@Test
	public void testTXT() {
		StatisticsPathGeneratorVisitor v = new StatisticsPathGeneratorVisitor("");
		textualType.accept(v);
		
		assertEquals(1, v.getPaths().size());
		assertEquals("Textual-type", v.getPaths().get(0));
	}
	
	@Test
	public void testCMPSimple() {
		StatisticsPathGeneratorVisitor v = new StatisticsPathGeneratorVisitor("");
		simpleCompositeType.accept(v);
		
		assertEquals(2, v.getPaths().size());
		assertEquals("Composite-type/TextualInsideSimple", v.getPaths().get(0));
		assertEquals("Composite-type/TextualInsideSimple-2", v.getPaths().get(1));
	}
	
	@Test
	public void testCMPComplex() {
		StatisticsPathGeneratorVisitor v = new StatisticsPathGeneratorVisitor("");
		complexCompositeType.accept(v);
		
		assertEquals(5, v.getPaths().size());
		assertEquals("Complex-composite-type/TextualInsideComplex", v.getPaths().get(0));
		assertEquals("Complex-composite-type/TextualInsideComplex-2", v.getPaths().get(1));
		assertEquals("Complex-composite-type/TextualInsideComplex-3", v.getPaths().get(2));
		assertEquals("Complex-composite-type/SimpleInsideComplex/TextualInsideSimple", v.getPaths().get(3));
		assertEquals("Complex-composite-type/SimpleInsideComplex/TextualInsideSimple-2", v.getPaths().get(4));
	}
	

}
