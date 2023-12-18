package it.unifi.ing.stlab.view.model;

import it.unifi.ing.stlab.entities.implementation.composite.CompositeEntityImpl;
import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.composite.CompositeEntity;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.view.model.links.ViewerLink;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table( name = "viewers" )
@Inheritance( strategy=InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( 
	name= "from_class", 
	discriminatorType=DiscriminatorType.STRING )
public abstract class Viewer 
		implements Persistable, CompositeEntity<Viewer, ViewerLink>{
	  
	private String name;
	private String description;
	private Type type;
	private String css;
	private Boolean readOnly;
	private Boolean anonymous;
	
	private PersistableImpl persistable;
	private CompositeEntityImpl<Viewer, ViewerLink> compositeEntity;
	
	public Viewer(String uuid) {
		super();
		persistable = new PersistableImpl( uuid );
		compositeEntity = new CompositeEntityImpl<Viewer, ViewerLink>();
		compositeEntity.setDelegator(this);
	}
	protected Viewer() {
		persistable = new PersistableImpl();
		compositeEntity = new CompositeEntityImpl<Viewer, ViewerLink>();
		compositeEntity.setDelegator(this);
	}

	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="viewer", 
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
	
	// Nome
	@Column( unique = true )
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	@Lob
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne //( fetch=FetchType.LAZY )
	@JoinColumn( name = "type_id" )
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	@Lob
	public String getCss() {
		return css;
	}
	public void setCss(String css){
		this.css = css;
	}
	
	
	@Column( name = "read_only" )
	public Boolean getReadOnly() {
		return readOnly;
	}
	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	
	// Anonymous
	public Boolean getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(Boolean anonymous) {
		this.anonymous = anonymous;
	}
	
	
	@ManyToMany( fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinTable(
		name = "viewer_ancestors",
	    joinColumns = { @JoinColumn( name = "view_id", referencedColumnName="id" ) },
	    inverseJoinColumns = { @JoinColumn( name = "ancestor_view_id", referencedColumnName = "id") } )
	protected Set<Viewer> getAncestors() {
		return compositeEntity.getAncestors();
	}
	protected void setAncestors(Set<Viewer> ancestors) {
		compositeEntity.setAncestors(ancestors);
	}
	public Set<Viewer> listAncestors() {
		return compositeEntity.listAncestors();
	}
	
	
	@ManyToMany( mappedBy = "ancestors", fetch = FetchType.LAZY )
	protected Set<Viewer> getDescendents() {
		return compositeEntity.getDescendents();
	}
	protected void setDescendents(Set<Viewer> descendents) {
		compositeEntity.setDescendents(descendents);
	}
	public Set<Viewer> listDescendents() {
		return compositeEntity.listDescendents();
	}

	
	@OneToMany( mappedBy="target", fetch = FetchType.LAZY )
	protected Set<ViewerLink> getParents() {
		return compositeEntity.getParents();
	}
	protected void setParents(Set<ViewerLink> parents) {
		compositeEntity.setParents(parents);
	}
	public Set<ViewerLink> listParents() {
		return compositeEntity.listParents();
	}

	
	@OneToMany( mappedBy="source", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE} )
	protected Set<ViewerLink> getChildren() {
		return compositeEntity.getChildren();
	}
	protected void setChildren(Set<ViewerLink> children) {
		compositeEntity.setChildren(children);
	}
	public Set<ViewerLink> listChildren() {
		return compositeEntity.listChildren();
	}
	public List<ViewerLink> listChildrenOrdered() {
		return compositeEntity.listChildrenOrdered();
	}
	@Override
	public void refreshChildrenOrdered() {
		compositeEntity.refreshChildrenOrdered();
	}
	
	@Transient
	public ViewerLink getByPriority(Long priority){
		if(priority == null || priority < 0)
			throw new IllegalArgumentException(priority + "non è accettabile per il metodo getByPriority");
		
		// commentato perchè non aveva molto senso comparare la priorità col size
		// (e.g. ci possono essere due elementi di priorità 5 e 6 
		// e se faccio getByPriority(6) non mi deve dare errore)
//		if(priority > listChildren().size())
//			throw new IllegalArgumentException("non ci sono "+ priority + " sottoviste");
		
		for(ViewerLink sv: listChildren()){
			if(sv.getPriority().equals(priority))
				return sv;
		}
		
		throw new RuntimeException("elemento di priorità " + priority + " non trovato");
	}
	
	public boolean isValidSubViewer(ViewerLink s){
		return false;
	}
	
	@Override
	public int hashCode() {
		return persistable.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}

	@Override
	public void init() {
		compositeEntity.init();
	}
	@Override
	public void delete() {
		compositeEntity.delete();
	}
	
	@Transient
	public abstract String getXhtml();
	
	public abstract void accept(ViewerVisitor v);
}
