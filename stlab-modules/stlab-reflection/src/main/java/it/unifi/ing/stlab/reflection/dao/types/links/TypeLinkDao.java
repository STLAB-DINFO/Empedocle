package it.unifi.ing.stlab.reflection.dao.types.links;

import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TypeLinkDao {

	List<TypeLink> findBySuggestion(String suggestion, int limit);

	TypeLink findById(Long id);
	TypeLink findByUuid(String uuid);
	TypeLink findByName(String name);
	TypeLink findChildrenByName(Type type, String name);
}
