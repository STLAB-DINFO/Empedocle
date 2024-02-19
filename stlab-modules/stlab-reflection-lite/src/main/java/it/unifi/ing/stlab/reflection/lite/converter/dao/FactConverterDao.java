package it.unifi.ing.stlab.reflection.lite.converter.dao;

import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.Type;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class FactConverterDao {

	private final EntityManager entityManager;
	
	public FactConverterDao(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	public Type findType( String uuid ) {
		return entityManager.createQuery( 
			"select t " +
			" from Type t " +
			" where t.uuid = :uuid", Type.class )
			.setParameter( "uuid", uuid )
			.getSingleResult();
	}
	
	public Phenomenon findPhenomenon( String uuid ) {
		try {
		return entityManager.createQuery( 
			"select p " +
			" from Phenomenon p " +
			" where p.uuid = :uuid", Phenomenon.class )
			.setParameter( "uuid", uuid ).getSingleResult();
		
		} catch(NoResultException nre) {
			System.out.println( uuid );
			return null;
		}
	}
}
