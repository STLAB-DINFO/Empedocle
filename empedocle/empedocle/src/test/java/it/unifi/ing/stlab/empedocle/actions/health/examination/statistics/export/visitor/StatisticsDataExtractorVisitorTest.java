package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

public class StatisticsDataExtractorVisitorTest extends BaseStatisticsTest {
	
	
	@Test
	public void testTXTNull() {
		StatisticsDataExtractorVisitor v = new StatisticsDataExtractorVisitor("");
		textualFact.setText(null);
		textualFact.accept(v);
		
		assertEquals(0, v.getResults().size());
	}
	
	@Test
	public void testTXT() {
		StatisticsDataExtractorVisitor v = new StatisticsDataExtractorVisitor("");
		textualFact.accept(v);
		
		assertEquals(1, v.getResults().size());
		assertEquals("text", v.getResults().get(0).getData());
		assertEquals("Textual-type", v.getResults().get(0).getPath());
	}
	
	@Test
	public void testQLT() {
		StatisticsDataExtractorVisitor v = new StatisticsDataExtractorVisitor("");
		qltFact.accept(v);
		
		assertEquals(1, v.getResults().size());
		assertEquals("phen", v.getResults().get(0).getData());
		assertEquals("Enum-Type", v.getResults().get(0).getPath());
	}
	
	@Test
	public void testQLTNull() {
		StatisticsDataExtractorVisitor v = new StatisticsDataExtractorVisitor("");
		qltFact.setPhenomenon(null);
		qltFact.accept(v);
		
		assertEquals(0, v.getResults().size());
	}
	
	@Test
	public void testQNT() {
		StatisticsDataExtractorVisitor v = new StatisticsDataExtractorVisitor("");
		qntFact.accept(v);
		
		assertEquals(1, v.getResults().size());
		assertEquals("10", v.getResults().get(0).getData());
		assertEquals("Quantitative-Type", v.getResults().get(0).getPath());
	}
	
	@Test
	public void testQNTNull() {
		StatisticsDataExtractorVisitor v = new StatisticsDataExtractorVisitor("");
		Quantity old = qntFact.getQuantity();
		qntFact.setQuantity(null);
		qntFact.accept(v);
		
		assertEquals(0, v.getResults().size());
		
		old.setValue(null);
		qntFact.setQuantity(old);
		qntFact.accept(v);
		
		assertEquals(0, v.getResults().size());
	}
	
	@Test
	public void testTEMPORAL() {
		StatisticsDataExtractorVisitor v = new StatisticsDataExtractorVisitor("");
		Date now = Calendar.getInstance().getTime();
		tempFact.setDate(now);
		tempFact.accept(v);
		
		SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
		sdf.setTimeZone( TimeZone.getDefault() );
		
		assertEquals(1, v.getResults().size());
		assertEquals(sdf.format(now), v.getResults().get(0).getData());
		assertEquals("Temporal-Type", v.getResults().get(0).getPath());
	}
	
	@Test
	public void testTEMPORALNull() {
		StatisticsDataExtractorVisitor v = new StatisticsDataExtractorVisitor("");
		tempFact.setDate(null);
		tempFact.accept(v);
		
		assertEquals(0, v.getResults().size());
	}
	
	@Test
	public void testCMPSimple() {
		StatisticsDataExtractorVisitor v = new StatisticsDataExtractorVisitor("");
		simpleCompositeFact.accept(v);
		
		assertEquals(1, v.getResults().size());
		assertEquals("text", v.getResults().get(0).getData());
		assertEquals("Composite-type/TextualInsideSimple", v.getResults().get(0).getPath());
	}
	
	@Test
	public void testCMPComplex() {
		StatisticsDataExtractorVisitor v = new StatisticsDataExtractorVisitor("");
		complexCompositeFact.accept(v);
		
		assertEquals(3, v.getResults().size());
		assertEquals("Complex-composite-type/TextualInsideComplex", v.getResults().get(0).getPath());
		assertEquals("Complex-composite-type/TextualInsideComplex-2", v.getResults().get(1).getPath());
		assertEquals("Complex-composite-type/SimpleInsideComplex/TextualInsideSimple", v.getResults().get(2).getPath());
	}
	

}
