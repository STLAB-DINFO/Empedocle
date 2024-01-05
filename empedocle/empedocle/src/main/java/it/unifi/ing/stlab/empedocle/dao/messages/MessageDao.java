package it.unifi.ing.stlab.empedocle.dao.messages;

import it.unifi.ing.stlab.empedocle.model.messages.Message;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MessageDao {

	List<Message> find(MessageQueryBuilder builder, int offset, int limit);
	Message findById(Long id);
	
	int count(MessageQueryBuilder builder);
	int countByPatientId(Long id);

	void update(Message target);
	void save(Message target);
}
