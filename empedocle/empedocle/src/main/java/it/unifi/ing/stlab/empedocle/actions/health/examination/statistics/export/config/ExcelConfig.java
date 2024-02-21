package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.config;

import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor.StatisticsPathGeneratorVisitor;

public abstract class ExcelConfig {
	
	/**
	 * First data row of the sheet. Row 0 is reserved for the header
	 */
	private final int dataStartRow = 1;
	
	/**
	 * Format for dates to be entered in the sheet
	 */
	private final String dateFormat = "dd/MM/yyyy";
	
	public abstract String[] defaultColumns();
	public abstract StatisticsPathGeneratorVisitor getPathGenerator(String basePath);
	//public abstract StatisticsDataExtractorVisitor getDataExtractor(String basePath);

	public int getDataStartRow() {
		return dataStartRow;
	}

	public String getDateFormat() {
		return dateFormat;
	}

}
