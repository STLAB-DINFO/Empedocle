package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export;

import it.unifi.ing.stlab.reflection.model.facts.Fact;

import java.io.OutputStream;
import java.util.List;

public interface DataExporter {
	
	public void export(List<Fact> roots) throws Exception;
	public void toFile(String fileDestination) throws Exception;
	public void toOutputStream(OutputStream os) throws Exception;
	public String getFileName();

}
