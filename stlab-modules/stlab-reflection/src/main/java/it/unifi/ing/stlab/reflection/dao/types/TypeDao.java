package it.unifi.ing.stlab.reflection.dao.types;

import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TypeDao {

	int count(TypeQueryBuilder builder);

	Type findById(Long id);
	Type findByUuid(String uuid);
	Type findByName(String name);
	Type findByExaminationType(Long examinationTypeId);

	Type fetchById(Long id);
	Type fetchByIdWithFullHierarchy(Long id);
	QuantitativeType fetchWithUnitUses(Long id);

	List<Type> find(TypeQueryBuilder builder, int offset, int limit);
	List<Type> findBySuggestion(String suggestion, int limit);

	void save(Type t);
	void delete(Long id);
	
	void assignToLink(TypeLink link, Type type);

	FactValue fetchFactValue(TypeLink tl);

	boolean checkForeignKeyRestrictions(Long id);
}
