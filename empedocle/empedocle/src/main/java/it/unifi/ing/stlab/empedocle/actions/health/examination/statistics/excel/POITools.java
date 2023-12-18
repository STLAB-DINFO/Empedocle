package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.excel;

import it.unifi.ing.stlab.reflection.model.types.Type;

import java.util.Collection;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POITools {
	
	public static Row createRow(Sheet sheet, Integer rowNumber) {
		return sheet.createRow(rowNumber);
	}
	
	public static void initSheet(Sheet sheet, Collection<String> columnNames) {
		generateColumnNames(sheet, columnNames);
	}

	public static Workbook initWorkbook(String nomeTipoVisita, Collection<String> columnNames) throws Exception {
		// crea il workbook con un foglio
		Workbook wb = new XSSFWorkbook();
		
		// crea i nomi delle colonne
		generateColumnNames(wb.createSheet("Statistiche"), columnNames);
		
		return wb;
	}

	private static void generateColumnNames(Sheet sheet, Collection<String> columnNames) {
		Row row = sheet.createRow(0);
		int count = 0;
		for(String s : columnNames) {
			addContent(s, row, count);
			count++;
		}

	}

	public static void addContent(String data, Row row, Integer column) {
		Cell cell = row.createCell(column);
		if(data != null) {
			if(isNumeric(data)) {
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Double.parseDouble(data));
			}
			else {
				//FIXME settare come stile il wrap della testo nella cella
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(data);
			}
		}
		
	}
	
	// FIXME estrarre in altra classe utilità ?
	public static boolean isNumeric(String str) {
		// match a number with optional '-' and decimal
		return str.matches("-?\\d+(\\.\\d+)?");
	}
	
	public static String buildSheetName(Type t) {
		String name = t.getId().toString() + " " + t.getName();
		if(name.length() > 31) {
			return name.substring(0, 31);
		}
		else {
			return name;
		}
	}
	
//	public static String buildSheetName(ExaminationType t) {
//		String name = t.getId().toString() + " " + t.getName();
//		if(name.length() > 31) {
//			return name.substring(0, 31);
//		}
//		else {
//			return name;
//		}
//	}

}
