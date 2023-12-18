package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.config;

import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor.StatisticsDataExtractorVisitor;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor.StatisticsPathGeneratorVisitor;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor.cardiology.CardiologyStatisticsDataExtractorVisitor;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor.cardiology.CardiologyStatisticsPathGeneratorVisitor;

public class CardiologyExcelConfig extends BaseExcelConfig {
	
	@Override
	public StatisticsDataExtractorVisitor getDataExtractor(String basePath) {
		return new CardiologyStatisticsDataExtractorVisitor(basePath);
	}
	
	@Override
	public StatisticsPathGeneratorVisitor getPathGenerator(String basePath) {
		return new CardiologyStatisticsPathGeneratorVisitor(basePath);
	}

}
