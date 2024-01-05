package it.unifi.ing.stlab.empedocle.dao.staff;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.empedocle.model.Staff;

import javax.ejb.Local;
import java.util.List;

@Local
public interface StaffDao {

	int count(QueryBuilder builder);

	List<Staff> find(QueryBuilder builder, int offset, int limit);
	Staff findById(Long id);
	Staff findByUserId(Long id);
	
	Staff fetchById(Long id);
	Staff fetchByUsername(String username);

	void save(Staff staff);
	void update(Staff staff);
	void delete(Long id);

	Boolean checkForeignKeyRestrictions(Staff s);
}
