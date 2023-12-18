package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.excel;

import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.DataExporter;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.config.ExcelConfig;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor.StatisticsPathGeneratorVisitor;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public abstract class ExcelDataExporter implements DataExporter {
	
	protected final ExcelConfig config;
	
	public ExcelDataExporter(ExcelConfig config) {
		this.config = config;
	}
	
	protected Map<String, Integer> generateColumnNames(Type rootType) {
		List<String> paths = new ArrayList<String>();
		
		for(TypeLink tl : rootType.listChildrenOrdered()) {
			StatisticsPathGeneratorVisitor pg = config.getPathGenerator("");
			tl.getTarget().accept(pg);
			paths.addAll(pg.getPaths());
		}
		
		Map<String, Integer> structure = new LinkedHashMap<String, Integer>(paths.size());
		Integer position = 0;
		
		// crea colonne dati addizionali paziente e visita
		for(String s : config.defaultColumns()) {
			structure.put(s, position);
			position ++;
		}
		
		// crea colonne osservazioni
		for(String s : paths) {
			structure.put(s, position);
			position ++;
		}

		return structure;
	}

}
