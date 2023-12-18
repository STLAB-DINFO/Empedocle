package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.factory;

import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.excel.POIDataExporter;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.DataExporter;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.config.BaseExcelConfig;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.config.CardiologyExcelConfig;

public class DataExporterFactory {

	public static DataExporter createDataExporter() {
		return new POIDataExporter(new BaseExcelConfig());
	}

}
