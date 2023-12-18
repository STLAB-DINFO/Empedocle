package it.unifi.ing.stlab.reflection.dao.types;

import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import java.util.List;

import javax.ejb.Local;

@Local
public interface TypeDao {

	public int count( TypeQueryBuilder builder );

	public Type findById( Long id );
	public Type findByUuid( String uuid );
	public Type findByName( String name );
	public Type findByExaminationType( Long examinationTypeId );

	public Type fetchById( Long id );
	public Type fetchByIdWithFullHierarchy( Long id );
	public QuantitativeType fetchWithUnitUses( Long id );

	public List<Type> find( TypeQueryBuilder builder, int offset, int limit );
	public List<Type> findBySuggestion( String suggestion, int limit );

	public void save( Type t );
	public void delete( Long id );
	
	public void assignToLink( TypeLink link, Type type );

	public FactValue fetchFactValue( TypeLink tl );

	public boolean checkForeignKeyRestrictions( Long id );
}
