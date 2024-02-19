package it.unifi.ing.stlab.reflection.dao.types;

import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QualitativeType;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

@Local
public interface PhenomenonDao {
	
	List<Phenomenon> findByFact(QualitativeFact ql, Date date);
	List<Phenomenon> findByType(QualitativeType qlt, Date date);
	List<Phenomenon> findBySuggestion(String suggestion, QualitativeType qlt, Date date, int limit);
	Phenomenon findByName(QualitativeFact ql, String name, Date date);
	
	Phenomenon findByUuid(String uuid);
	
}
