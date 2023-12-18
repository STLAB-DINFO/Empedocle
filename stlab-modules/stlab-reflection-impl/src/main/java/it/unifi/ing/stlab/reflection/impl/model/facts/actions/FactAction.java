package it.unifi.ing.stlab.reflection.impl.model.facts.actions;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.implementation.traced.ActionImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table( name="fact_actions" )
@Inheritance( strategy=InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( 
	name= "from_class", 
	discriminatorType=DiscriminatorType.STRING )
public abstract class FactAction 
	implements Action<FactImpl, FactAction, User, Time>, Persistable {

	private PersistableImpl persistable;
	private ActionImpl<FactImpl, FactAction, User, Time> delegate;
	
	public FactAction( String uuid ) {
		persistable = new PersistableImpl( uuid );
	}
	protected FactAction() {
		persistable = new PersistableImpl();
	}
	
	@Transient
	protected ActionImpl<FactImpl, FactAction, User, Time> getDelegate() {
		return delegate;
	}
	protected void setDelegate(ActionImpl<FactImpl, FactAction, User, Time> delegate) {
		this.delegate = delegate;
	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="fact_action", 
		allocationSize = 50 )
	@GeneratedValue(strategy=GenerationType.TABLE, generator="table_gen")	
	public Long getId() {
		return persistable.getId();
	}
	protected void setId(Long id) {
		persistable.setId(id);
	}
	
	// UUID
	public String getUuid() {
		return persistable.getUuid();
	}
	protected void setUuid(String uuid) {
		persistable.setUuid(uuid);
	}

	@ManyToOne
	@JoinColumn( name = "author_id" )
	public User getAuthor() {
		return delegate.getAuthor();
	}
	public void setAuthor(User author) {
		delegate.setAuthor( author );
	}

	
	@AttributeOverrides({
		@AttributeOverride( name="date",column=@Column(name="action_time"))
	 })	
	@Embedded
	public Time getTime() {
		return delegate.getTime();
	}
	public void setTime(Time time) {
		delegate.setTime( time );
	}

	@Transient
	public boolean isTerminal() {
		return delegate.isTerminal();
	}
	public void delete() {
		delegate.delete();
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
