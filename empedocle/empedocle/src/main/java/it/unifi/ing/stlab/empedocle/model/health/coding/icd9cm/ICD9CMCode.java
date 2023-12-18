package it.unifi.ing.stlab.empedocle.model.health.coding.icd9cm;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "icd_9_cm")
@Inheritance( strategy=InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( 
	name= "from_class", 
	discriminatorType=DiscriminatorType.STRING )
public abstract class ICD9CMCode implements Persistable {

	private PersistableImpl persistable;
	private String code;
	private String description;
	
	private Phenomenon phenomenon;
	protected ICD9CMCode parent;
	protected ICD9CMTopicCode root;
	
	public ICD9CMCode( String uuid ) {
		persistable = new PersistableImpl( uuid );
	}
	protected ICD9CMCode() {
		persistable = new PersistableImpl();
	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="icd_9_cm", 
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
	protected void setUuid(String uuid) {
		persistable.setUuid(uuid);
	}
	
	@Column( unique = true )
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Lob
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@ManyToOne( cascade = { CascadeType.ALL } )
	@JoinColumn( name = "phen_id" )
	public Phenomenon getPhenomenon() {
		return phenomenon;
	}
	public void setPhenomenon(Phenomenon phenomenon) {
		this.phenomenon = phenomenon;
	}
	
	@ManyToOne
	@JoinColumn( name = "parent_id" )
	public ICD9CMCode getParent() {
		return parent;
	}
	public abstract void setParent(ICD9CMCode parent);
	
	@ManyToOne
	@JoinColumn( name = "root_id" )
	public ICD9CMTopicCode getRoot() {
		return root;
	}
	public void setRoot( ICD9CMTopicCode code ) {
		this.root = code;
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
