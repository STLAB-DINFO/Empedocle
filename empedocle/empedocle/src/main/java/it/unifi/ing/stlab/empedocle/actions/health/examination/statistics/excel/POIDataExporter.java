package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.excel;

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

public class POIDataExporter {
	/*
	
	private Workbook workbook;
	private Map<String, Map<String, Integer>> structures;
	private Map<String, Integer> currentRowNumbers;
	
	public POIDataExporter(BaseExcelConfig config) {
		super(config);

		// single file, but one sheet for each type of appointment
		workbook = new XSSFWorkbook();
		// similarly, each type of appointment has its own structure
		structures = new HashMap<String, Map<String,Integer>>();
		// keeps track of the row number reached for each sheet
		currentRowNumbers = new HashMap<String, Integer>();
		
	}

	// note1: it is assumed that these are already root facts of appointments ok for export, meaning they are neither accepted nor booked
	@Override
	public void export(List<Fact> roots) throws Exception {
		for(Fact rootFact : roots) {

			// if it's a 'new' type of appointment, create and initialize sheet and structure
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
			
			// create a new row for the appointment
			Row row = createRow(
						workbook.getSheet( buildSheetName(rootType) ),
						currentRowNumbers.get( rootType.getName()  ) );
			
			// write the default rows containing Patient and Examination info
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
			
			// increment the row count for the currently used sheet
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
		return "statistics_" +
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


	 */
}
