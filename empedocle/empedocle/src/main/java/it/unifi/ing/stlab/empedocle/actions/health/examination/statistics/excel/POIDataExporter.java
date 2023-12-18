package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.excel;

import static it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.excel.POITools.addContent;
import static it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.excel.POITools.buildSheetName;
import static it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.excel.POITools.createRow;
import static it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.excel.POITools.initSheet;
import it.unifi.ing.stlab.commons.util.FileUtils;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.config.BaseExcelConfig;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.datamodel.FactDataPath;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor.StatisticsDataExtractorVisitor;
import it.unifi.ing.stlab.empedocle.actions.util.DateUtils;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.Type;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POIDataExporter extends ExcelDataExporter {
	
	private Workbook workbook;
	private Map<String, Map<String, Integer>> structures;
	private Map<String, Integer> currentRowNumbers;
	
	public POIDataExporter(BaseExcelConfig config) {
		super(config);
		
		// unico file, ma un foglio per ogni tipo visita
		workbook = new XSSFWorkbook();
		// analogamente, ogni tipovisita ha la sua struttura
		structures = new HashMap<String, Map<String,Integer>>();
		// tiene traccia del numero di riga a cui si era arrivati per ogni foglio
		currentRowNumbers = new HashMap<String, Integer>();
		
	}

	// nota1: si presuppone che questi siano già fact root di visite ok per l'export, ovvero che non siano accettate o prenotate
	@Override
	public void export(List<Fact> roots) throws Exception {
		for(Fact rootFact : roots) {
			
			// se è un 'nuovo' tipo visita, crea e inizializza foglio e struttura
//			ExaminationType examinationType = ClassHelper.cast(rootFact.getContext(), Examination.class).getType();
			Type rootType = rootFact.getType();
			
			if(!structures.containsKey( rootFact.getType().getName() )) {

				structures.put( rootType.getName() , generateColumnNames(rootFact.getType()) );
				
				initSheet(
						workbook.createSheet( buildSheetName(rootType) ),
						structures.get( rootType.getName() ).keySet()
						);
				
				currentRowNumbers.put(rootType.getName(), config.getDataStartRow());
				
			}
			
			// crea una nuova riga per la visita
			Row row = createRow(
						workbook.getSheet( buildSheetName(rootType) ),
						currentRowNumbers.get( rootType.getName()  ) );
			
			// scrive le righe di default contenenti le info Paziente e Examination
			Examination e = ClassHelper.cast(rootFact.getContext(), Examination.class);
			getConfig().writeDefaultColumns(e, e.getAppointment().getPatient(), row);
			
			for(FactLink fl : rootFact.listChildrenOrdered()) {
				if(fl.getTarget() != null ){
					StatisticsDataExtractorVisitor v = getConfig().getDataExtractor("");
					fl.getTarget().accept(v);
					
					for(FactDataPath fdp : v.getResults()) {
						Integer columnIndex = structures.get(rootType.getName()).get(fdp.getPath());
						
						if(columnIndex != null) {
							addContent(fdp.getData(), row, columnIndex);
						}
					}
				}
				
			}
			
			// incrementa il conteggio righe per il foglio correntemente usato
			currentRowNumbers.put(rootType.getName(), currentRowNumbers.get(rootType.getName()) + 1);
			
		}
		
	}

	@Override
	public void toFile(String fileDestination) throws Exception {
		FileOutputStream out = new FileOutputStream(
				createFile(fileDestination));
		workbook.write(out);
		out.flush();
		out.close();

	}

	@Override
	public void toOutputStream(OutputStream os) throws Exception {
		workbook.write(os);
		
	}
	
	@Override
	public String getFileName() {
		return "statistiche_" +
				DateUtils.getString( Calendar.getInstance().getTime(), config.getDateFormat() )
				+ ".xlsx";
		
	}
	
	private BaseExcelConfig getConfig() {
		return (BaseExcelConfig)config;
	}
	
	private String createFile(String fileDestination) throws IOException {
		String path;
		
		if (fileDestination == null || "".equals(fileDestination)) {
			
			path = getConfig().getDefaultDestination();
			
		} else {

			path = fileDestination;
		}
		
		String fileWithPath = path + getFileName();
		
		FileUtils.createFileAndDirectories(fileWithPath);
		
		return fileWithPath;
	}

}
