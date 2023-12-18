package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.config;

import static it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.excel.POITools.addContent;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor.StatisticsDataExtractorVisitor;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor.StatisticsPathGeneratorVisitor;
import it.unifi.ing.stlab.empedocle.actions.util.DateUtils;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.patients.model.Patient;

import java.util.Arrays;

import org.apache.poi.ss.usermodel.Row;

public class BaseExcelConfig extends ExcelConfig {

	/**
	 * Destinazione standard dei file generati
	 */
	private final String defaultDestination = "DA_CONFIGURARE";

	/**
	 * Informazioni addizionali relative al paziente da includere nel report
	 */
	private final String[] patientData = { "Codice Fiscale", "Cognome", "Nome",
			"Sesso", "Data di nascita", "Luogo di nascita", "Residenza",
			"Telefono" };

	/**
	 * Informazioni addizionali relative alla visita da includere nel report
	 */
	private final String[] examinationInfo = { "Data", "Agenda",
			"Stato Visita" };
	
	/**
	 * Istanzia il visitor specifico per estrarre i dati richiesti dalle Visite
	 * @param basePath		path iniziale, se null si usa ""
	 * @return				CardiologyStatisticsDataExtractorVisitor
	 */
	public StatisticsDataExtractorVisitor getDataExtractor(String basePath) {
		return new StatisticsDataExtractorVisitor(basePath);
	}
	
	/**
	 * Istanzia il visitor specifico per estrarre i path completi dei type della Visita
	 * @param basePath		path iniziale, se null si usa ""
	 * @return				CardiologyStatisticsPathGeneratorVisitor
	 */
	public StatisticsPathGeneratorVisitor getPathGenerator(String basePath) {
		return new StatisticsPathGeneratorVisitor(basePath);
	}

	/**
	 * Informazioni addizionali da includere nel report
	 */
	public String[] defaultColumns() {
		String[] result = Arrays.copyOf(examinationInfo, patientData.length + examinationInfo.length);
		System.arraycopy(patientData, 0, result, examinationInfo.length, patientData.length);
		return result;
	}
	
	public void writeDefaultColumns(Examination e, Patient p, Row row) {
		if(e != null) {
			addContent(DateUtils.getString(e.getAppointment().getDate(), getDateFormat()), row, 0);
			addContent(e.getAppointment().getAgenda().getCode(), row, 1);
			addContent(e.getStatus().getTranslatedName(), row, 2);
		}
		
		if(p != null) {
			addContent(p.getTaxCode(), row, 3);
			addContent(p.getSurname(), row, 4);
			addContent(p.getName(), row, 5);
			addContent( ( p.getSex() != null ? p.getSex().getName() : "" ), row, 6);
			addContent( ( p.getBirthDate() != null ? DateUtils.getString(p.getBirthDate(), getDateFormat() ) : "" ), row, 7);
			addContent(p.getBirthPlace(), row, 8);
			addContent(p.getResidence() != null ?p.getResidence().getPlace() : null, row, 9);
			addContent(p.getHomePhone(), row, 10);
		}
	}

	public String getDefaultDestination() {
		return defaultDestination;
	}
	

}
