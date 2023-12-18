package it.unifi.ing.stlab.users.model;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.implementation.timed.TimedEntityImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.entities.model.timed.TimedEntity;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table( name = "qualifications" )
public class Qualification implements Persistable, TimedEntity<TimeRange,Time> {

	private PersistableImpl persistable;
	private TimedEntityImpl<TimeRange,Time> timedEntity;
    private String name;

    public Qualification( String uuid ) {
		persistable = new PersistableImpl( uuid );
		timedEntity = new TimedEntityImpl<TimeRange, Time>();
	}
    protected Qualification() {
		persistable = new PersistableImpl();
		timedEntity = new TimedEntityImpl<TimeRange, Time>();
	}

	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="qualification", 
		allocationSize = 1)
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
	
	
	@Embedded
	public TimeRange getTimeRange() {
		return timedEntity.getTimeRange();
	}
	public void setTimeRange(TimeRange timeRange) {
		timedEntity.setTimeRange(timeRange);
	}
	
	
	// Name
	@Column( unique = true )
	public String getName() {
    	return name;
    }
    public void setName(String nome) {
    	this.name = nome;
    }
    
    
    //
    // Methods
    //
	public boolean isValidReference(TimedEntity<?, ?> timedEntity) {
		return timedEntity.isValidReference(timedEntity);
	}
	
	public boolean isValidAt(Time time) {
		return timedEntity.isValidAt(time);
	}
	
	public int hashCode() {
		return persistable.hashCode();
	}
	
	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}
	
	
	@Override
	public String toString() {
		return name;
	}
	
	
}