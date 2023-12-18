package it.unifi.ing.stlab.empedocle.model.health;

import it.unifi.ing.stlab.reflection.model.facts.FactContext;
import it.unifi.ing.stlab.users.model.User;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
@DiscriminatorValue( "EX" )
public class Examination extends FactContext {

	private Appointment appointment;
	private ExaminationType type;
	private ExaminationStatus status;
	private Date lastUpdate;
	private Boolean wasDone;
	private User author;

	public Examination(String uuid) {
		super(uuid);
	}
	protected Examination() {
		super();
	}

	@ManyToOne( fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
	@JoinColumn( name = "appointment_id" )
	public Appointment getAppointment() {
		return appointment;
	}
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "exam_type_id" )
	public ExaminationType getType() {
		return type;
	}
	public void setType(ExaminationType type) {
		this.type = type;
	}
	
	@Enumerated( EnumType.STRING )
	public ExaminationStatus getStatus() {
		return status;
	}
	public void setStatus(ExaminationStatus status) {
		this.status = status;
	}
	
	@Column( name = "last_update" )
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	@Column( name = "was_done" )
	public Boolean getWasDone() {
		return wasDone;
	}
	public void setWasDone(Boolean wasDone) {
		this.wasDone = wasDone;
	}
	
	@ManyToOne
	@JoinColumn( name = "author_id" )
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}	
}
