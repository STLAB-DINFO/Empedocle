package it.unifi.ing.stlab.empedocle.dao.messages;

import it.unifi.ing.stlab.empedocle.model.messages.Message;

import java.util.List;

import javax.ejb.Local;

@Local
public interface MessageDao {

	public List<Message> find( MessageQueryBuilder builder, int offset, int limit );
	public Message findById( Long id );
	
	public int count( MessageQueryBuilder builder );
	public int countByPatientId( Long id );

	public void update( Message target );
	public void save(Message target);
}
