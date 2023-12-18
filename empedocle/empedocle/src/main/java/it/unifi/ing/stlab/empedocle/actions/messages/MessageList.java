package it.unifi.ing.stlab.empedocle.actions.messages;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import it.unifi.ing.stlab.empedocle.dao.messages.MessageDao;
import it.unifi.ing.stlab.empedocle.model.messages.Message;
import it.unifi.ing.stlab.navigation.Navigator;

@Named
@RequestScoped
public class MessageList extends Navigator {

	//
	// CDI Injections
	//
	@Inject
	protected MessageFilter messageFilter;
	
	//
	// EJB injections
	//
	@Inject
	private MessageDao messageDao;
	
	//
	// Local attributes
	//
	private String selection;
	private Integer itemCount;
	
	public MessageList() {
	}
	
	@PostConstruct
	public void init() {
		setNavigationStatus( messageFilter );

		refreshCurrentPage();
	}

	@Override
	public Integer getItemCount() {
		if ( itemCount == null ) {
			itemCount = messageDao.count( messageFilter );
		}
		return itemCount;
	}
	
	@Produces @RequestScoped @Named( "messageResults" ) 
	public List<Message> getResultList() {
		if ( getItemCount().intValue() == 0 ) 
			return new ArrayList<Message>();
		
		return messageDao.find( messageFilter,  getOffset(),  getLimit() );
	}
	
	public String view( Long id ){
		selection = id.toString();
		return "view";
	}
	
	public String markAsUnread( Long id ){
		Message message = messageDao.findById(id);
		message.setIsRead(false);
		messageDao.update(message);
		
		return "message-list.jsf?faces-redirect=true";
	}
	
	public String getSelection() {
		return selection;
	}
	
	public String getRowStyleClasses() {
		StringBuffer buffer = new StringBuffer();

		boolean first = true;
		for (Message message : getResultList()) {
			if (first) {
				first = false;
			} else {
				buffer.append(", ");
			}

			buffer.append(getRowStyleClass(message));
		}

		return buffer.toString();	
	}
	
	public static String getRowStyleClass(Message message) {
		if (message == null)
			return "";
		
		StringBuffer buffer = new StringBuffer();

		switch (message.getLevel()) {
		case INFO:
			buffer.append("info");
			break;
		case WARNING:
			buffer.append("warning");
			break;			
		case SEVERE:
			buffer.append("error");
			break;			
		default:
			buffer.append("base");
			break;
		}
		
		if(!message.getIsRead())
			buffer.append(" to-read");
		
		return buffer.toString();
	}
}
