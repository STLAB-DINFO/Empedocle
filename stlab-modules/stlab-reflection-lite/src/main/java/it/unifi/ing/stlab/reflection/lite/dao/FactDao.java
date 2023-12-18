package it.unifi.ing.stlab.reflection.lite.dao;

import it.unifi.ing.stlab.reflection.lite.model.facts.context.FactContextLite;
import it.unifi.ing.stlab.reflection.model.facts.Fact;

import javax.ejb.Local;

@Local
public interface FactDao {

	public Fact findByContext( FactContextLite context );
	public void save( Fact fact );
	
}
