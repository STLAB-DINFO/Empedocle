package it.unifi.ing.stlab.empedocle.model;

import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;
import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "agendas")
public class Agenda implements Persistable {
	
	private PersistableImpl persistable;
	private String code;
	private String description;
	private ExaminationType examinationType;
	
	public Agenda( String uuid ) {
		persistable = new PersistableImpl( uuid );
	}
	protected Agenda() {
		persistable = new PersistableImpl();
	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="agenda", 
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

	
	@Column( unique = true )
	public String getCode() {
		return code;
	}
	public void setCode(String codice) {
		this.code = codice;
	}
	

	@Lob
	public String getDescription() {
		return description;
	}
	public void setDescription(String descrizione) {
		this.description = descrizione;
	}
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "exam_type_id" )
	public ExaminationType getExaminationType() {
		return examinationType;
	}
	public void setExaminationType(ExaminationType examinationType) {
		this.examinationType = examinationType;
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
	
	@Override
	public String toString(){
		if(this.getCode() == null && this.getDescription() == null)
			return null;
		else 
			return this.getCode() + " " + this.getDescription();
	}
}
