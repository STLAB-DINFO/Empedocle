package it.unifi.ing.stlab.empedocle.dao.staff;

import java.util.List;

import javax.ejb.Local;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.empedocle.model.Staff;

@Local
public interface StaffDao {

	public int count( QueryBuilder builder );

	public List<Staff> find( QueryBuilder builder, int offset, int limit );
	public Staff findById( Long id );
	public Staff findByUserId( Long id );
	
	public Staff fetchById( Long id );
	public Staff fetchByUsername( String username );

	public void save( Staff staff );
	public void update( Staff staff );
	public void delete( Long id );

	public Boolean checkForeignKeyRestrictions( Staff s );
}
