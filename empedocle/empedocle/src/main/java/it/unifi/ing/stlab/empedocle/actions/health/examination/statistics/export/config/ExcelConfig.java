package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.config;

import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor.StatisticsDataExtractorVisitor;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor.StatisticsPathGeneratorVisitor;

public abstract class ExcelConfig {
	
	/**
	 * Prima riga dati del foglio. La riga 0 è riservata all'intestazione
	 */
	private final int dataStartRow = 1;
	
	/**
	 * Formato per le date da inserire nel foglio
	 */
	private final String dateFormat = "dd/MM/yyyy";
	
	public abstract String[] defaultColumns();
	public abstract StatisticsPathGeneratorVisitor getPathGenerator(String basePath);
	public abstract StatisticsDataExtractorVisitor getDataExtractor(String basePath);

	public int getDataStartRow() {
		return dataStartRow;
	}

	public String getDateFormat() {
		return dateFormat;
	}

}
