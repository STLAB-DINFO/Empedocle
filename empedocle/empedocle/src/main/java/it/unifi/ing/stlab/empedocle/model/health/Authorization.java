package it.unifi.ing.stlab.empedocle.model.health;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.users.model.Qualification;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="authorizations")
public class Authorization implements Persistable {

	private Qualification qualification;
	private ExaminationOperation examOperation;
	
	private PersistableImpl persistable;
	
	protected Authorization(){
		persistable = new PersistableImpl();
	}
	public Authorization(String uuid){
		persistable = new PersistableImpl(uuid);
	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="authorization", 
		allocationSize = 50 )
	@GeneratedValue(strategy=GenerationType.TABLE, generator="table_gen" )
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
	
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn(name="qualification_id", nullable=false)
	public Qualification getQualification() {
		return qualification;
	}
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}
	
	
	@Enumerated(EnumType.STRING)
	public ExaminationOperation getExamOperation() {
		return examOperation;
	}
	public void setExamOperation(ExaminationOperation examOperation) {
		this.examOperation = examOperation;
	}
	
	
	// HashCode & Equals
	public int hashCode() {
		return persistable.hashCode();
	}
	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}
	
}
