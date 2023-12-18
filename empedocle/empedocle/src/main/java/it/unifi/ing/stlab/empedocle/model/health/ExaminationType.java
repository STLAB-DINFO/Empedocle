package it.unifi.ing.stlab.empedocle.model.health;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.reflection.model.types.Type;

@Entity
@Table(name = "examination_types")
public class ExaminationType implements Persistable {

	private PersistableImpl persistable;
	
	private String name;
	private String description;
	private Type type;
	private Integer timeToLive;
	private Set<ViewerUse> viewers;
	private Set<Authorization> authorizations;
	
	public ExaminationType( String uuid ) {
		persistable = new PersistableImpl( uuid );
		viewers = new HashSet<ViewerUse>();
		authorizations = new HashSet<Authorization>();
		
	}
	protected ExaminationType() {
		persistable = new PersistableImpl();
		viewers = new HashSet<ViewerUse>();
		authorizations = new HashSet<Authorization>();

	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="exam_type", 
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@ManyToOne
	@JoinColumn( name = "type_id" )
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	
	@Lob
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@Column( name = "ttl" )
	public Integer getTimeToLive() {
		return timeToLive;
	}
	public void setTimeToLive(Integer timeToLive) {
		this.timeToLive = timeToLive;
	}
	

	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval=true)
	@JoinColumn(name="exam_type_id", nullable=false)
	public Set<ViewerUse> getViewers() {
		return viewers;
	}
	protected void setViewers(Set<ViewerUse> examinationTypeViewerQualifications) {
		this.viewers = examinationTypeViewerQualifications;
	}
	public Set<ViewerUse> listViewers() {
		return Collections.unmodifiableSet( viewers );
	}
	public void addViewerUse(ViewerUse viewerUse ){
		if(viewerUse == null) 	return;
		
		viewers.add(viewerUse);
	}
	public void removeViewerUse(ViewerUse viewerUse ){
		if(viewerUse == null)
			return;
		
		viewers.remove(viewerUse);
	}
	
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval=true)
	@JoinColumn(name="authorization_id", nullable=false)
	public Set<Authorization> getAuthorizations() {
		return authorizations;
	}
	protected void setAuthorizations(Set<Authorization> authorizations) {
		this.authorizations = authorizations;
	}
	public Set<Authorization> listAuthorizations() {
		return Collections.unmodifiableSet( authorizations );
	}
	public void addAuthorization( Authorization authorization ){
		if(authorization == null) 	return;
		
		authorizations.add(authorization);
	}
	public void removeAuthorization( Authorization authorization ){
		if(authorization == null)
			return;
		
		authorizations.remove(authorization);
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
