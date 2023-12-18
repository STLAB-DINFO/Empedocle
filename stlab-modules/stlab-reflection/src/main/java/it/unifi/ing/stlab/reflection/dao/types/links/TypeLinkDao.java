package it.unifi.ing.stlab.reflection.dao.types.links;

import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import java.util.List;

import javax.ejb.Local;

@Local
public interface TypeLinkDao {

	public List<TypeLink> findBySuggestion( String suggestion, int limit);

	public TypeLink findById( Long id );
	public TypeLink findByUuid( String uuid );
	public TypeLink findByName( String name );
	public TypeLink findChildrenByName( Type type, String name );
}
