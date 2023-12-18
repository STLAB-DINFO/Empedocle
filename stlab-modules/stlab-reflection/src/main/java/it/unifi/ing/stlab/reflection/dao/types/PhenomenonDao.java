package it.unifi.ing.stlab.reflection.dao.types;

import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QualitativeType;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

@Local
public interface PhenomenonDao {
	
	public List<Phenomenon> findByFact(QualitativeFact ql, Date date);
	public List<Phenomenon> findByType(QualitativeType qlt, Date date);
	public List<Phenomenon> findBySuggestion(String suggestion, QualitativeType qlt, Date date, int limit);
	public Phenomenon findByName(QualitativeFact ql, String name, Date date);
	
	public Phenomenon findByUuid(String uuid);
	
}
