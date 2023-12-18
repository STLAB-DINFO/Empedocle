package it.unifi.ing.stlab.empedocle.dao.health;

import it.unifi.ing.stlab.empedocle.model.health.Service;

import javax.ejb.Local;

@Local
public interface ServiceDao {
	
	public Service findById( Long id );
	public Service find( String code, String description, String agendaCode );

}
