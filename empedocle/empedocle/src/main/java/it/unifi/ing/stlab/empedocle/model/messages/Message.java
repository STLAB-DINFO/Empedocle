package it.unifi.ing.stlab.empedocle.model.messages;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.patients.model.Patient;

@Entity
@Table(name = "messages")
public class Message implements Persistable {
	
	private PersistableImpl persistable;
	private Date date;
	private String sender;
	private String subject;
	private String body;
	private MessageLevel level;
	private Boolean isRead;
	
	private Patient patient;


	public Message( String uuid ) {
		persistable = new PersistableImpl( uuid );
	}
	protected Message() {
		persistable = new PersistableImpl();
	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="message", 
		allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.TABLE, generator="table_gen")	
	public Long getId() {
		return persistable.getId();
	}
	protected void setId(Long id) {
		persistable.setId(id);
	}
	
	public String getUuid() {
		return persistable.getUuid();
	}
	public void setUuid(String uuid) {
		persistable.setUuid(uuid);
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Lob
	public String getBody() {
		return body;
	}
	public void setBody( String body ) {
		this.body = body;
	}
	
	@Enumerated( EnumType.STRING )	
	public MessageLevel getLevel() {
		return level;
	}
	public void setLevel(MessageLevel level) {
		this.level = level;
	}
	
	@Column( name = "`read`" )
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	
	@ManyToOne
	@JoinColumn( name = "patient_id" )
	public Patient getPatient() {
		return patient;
	}
	public void setPatient( Patient patient ) {
		this.patient = patient;
	}
	
	@PrePersist
	public void prePersist() {
		if ( isRead == null )
			isRead = false;
	}
	
	//
	// HashCode & Equals
	//
	public int hashCode() {
		return persistable.hashCode();
	}
	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}
}