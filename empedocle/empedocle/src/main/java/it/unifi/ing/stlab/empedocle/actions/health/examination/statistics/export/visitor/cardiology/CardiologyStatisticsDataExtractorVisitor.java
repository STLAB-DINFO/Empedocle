package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor.cardiology;

import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor.StatisticsDataExtractorVisitor;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;

public class CardiologyStatisticsDataExtractorVisitor extends
		StatisticsDataExtractorVisitor {

	public CardiologyStatisticsDataExtractorVisitor(String basePath) {
		super(basePath);
	}

	@Override
	protected StatisticsDataExtractorVisitor getInstance(String basePath) {
		return new CardiologyStatisticsDataExtractorVisitor(basePath);
	}
	
	@Override
	public void visitTextual(TextualFact fact) {
		// esporto solo se si tratta di conclusioni
		if( fact.getType().getName() != null && 
				fact.getType().getName().contains( "Conclusioni" ) ) {
			super.visitTextual( fact );
		}
	}
}
