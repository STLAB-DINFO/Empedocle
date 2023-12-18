package it.unifi.ing.stlab.empedocle.actions.messages;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.cdi.ViewScoped;
import it.unifi.ing.stlab.empedocle.dao.messages.MessageDao;
import it.unifi.ing.stlab.empedocle.model.messages.Message;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class MessageView implements Serializable {

	private static final long serialVersionUID = 7165687623322352774L;

	//
	// HttpParam injections
	//
	@Inject
	@HttpParam( "id" )
	private String id;

	//
	// EJB injections
	//
	@Inject
	private MessageDao messageDao;

	//
	// Local attributes
	//
	private Message current;

	@PostConstruct
	public void init() {
		current = messageDao.findById( Long.parseLong( id ) );
	}

	public String close() {
		current.setIsRead( true );
		messageDao.update( current );
		return "close";
	}

	public String markAsUnread() {
		current.setIsRead( false );
		messageDao.update( current );
		return "close";
	}

	//
	// get methods
	//
	public String getId() {
		return id;
	}

	public Message getCurrent() {
		return current;
	}

}
