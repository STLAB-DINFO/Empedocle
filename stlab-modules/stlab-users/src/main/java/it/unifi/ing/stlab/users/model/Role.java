package it.unifi.ing.stlab.users.model;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table( name = "roles" )
public class Role implements Persistable {
	
	private PersistableImpl persistable;
	private String name;

	public Role( String uuid ) {
		persistable = new PersistableImpl( uuid );
	}
	protected Role() {
		persistable = new PersistableImpl();
	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="role", 
		allocationSize = 1 )
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
	public void setUuid(String uuid) {
		persistable.setUuid(uuid);
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
	public int hashCode() {
		return persistable.hashCode();
	}
	
	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}
}