package it.unifi.ing.stlab.empedocle.model.health;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.users.model.Qualification;
import it.unifi.ing.stlab.view.model.Viewer;

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
@Table(name="viewer_uses")
public class ViewerUse implements Persistable {

	private ExaminationTypeContext context;
	private Qualification qualification;
	private Viewer viewer;
	
	private PersistableImpl persistable;
	
	public ViewerUse(String uuid){
		persistable = new PersistableImpl(uuid);
	}
	
	protected ViewerUse(){
		persistable = new PersistableImpl();
	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="viewer_use", 
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
	
	
	@Enumerated(EnumType.STRING)
	public ExaminationTypeContext getContext() {
		return context;
	}
	public void setContext(ExaminationTypeContext context) {
		this.context = context;
	}
	
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn(name="qualification_id", nullable=false)
	public Qualification getQualification() {
		return qualification;
	}
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}
	 
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn(name="viewer_id", nullable=false)
	public Viewer getViewer() {
		return viewer;
	}
	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}
	
	// HashCode & Equals
	public int hashCode() {
		return persistable.hashCode();
	}
	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}
	
}
