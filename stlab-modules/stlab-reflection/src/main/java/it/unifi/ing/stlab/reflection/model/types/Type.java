package it.unifi.ing.stlab.reflection.model.types;

import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.composite.CompositeEntityImpl;
import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.implementation.timed.TimedEntityImpl;
import it.unifi.ing.stlab.entities.model.composite.CompositeEntity;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.entities.model.timed.TimedEntity;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table( name="types" )
@Inheritance( strategy=InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( 
	name= "from_class", 
	discriminatorType=DiscriminatorType.STRING )
public abstract class Type 
	implements CompositeEntity<Type, TypeLink>,
				Persistable, TimedEntity<TimeRange, Time> {

	private PersistableImpl persistable;
	private TimedEntityImpl<TimeRange, Time> timedEntity;
	private CompositeEntityImpl<Type, TypeLink> compositeEntity;
	private String name;
	private String description;
	private Boolean readOnly;
	private Boolean anonymous;
	
	private Boolean recurrent;
	
	
	public Type( String uuid ) {
		persistable = new PersistableImpl( uuid );
		timedEntity = new TimedEntityImpl<TimeRange, Time>();
		compositeEntity = new CompositeEntityImpl<Type, TypeLink>();
		compositeEntity.setDelegator( this );
	}
	public Type() {
		persistable = new PersistableImpl();
		timedEntity = new TimedEntityImpl<TimeRange, Time>();
		compositeEntity = new CompositeEntityImpl<Type, TypeLink>();

		compositeEntity.setDelegator( this );
	}
	
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="type", 
		allocationSize = 50)
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

	
	public Boolean getRecurrent() {
		return recurrent;
	}
	public void setRecurrent(Boolean recurrent) {
		this.recurrent = recurrent;
	}
	
	@ManyToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST } )
	@JoinTable(
		name = "type_ancestors",
	    joinColumns = { @JoinColumn( name = "type_id", referencedColumnName="id" ) },
	    inverseJoinColumns = { @JoinColumn( name = "ancestor_type_id", referencedColumnName = "id") } )
	protected Set<Type> getAncestors() {
		return compositeEntity.getAncestors();
	}
	protected void setAncestors(Set<Type> ancestors) {
		compositeEntity.setAncestors(ancestors);
	}
	public Set<Type> listAncestors() {
		return compositeEntity.listAncestors();
	}

	
	@ManyToMany( mappedBy = "ancestors", fetch=FetchType.LAZY, cascade = { CascadeType.PERSIST } )
	protected Set<Type> getDescendents() {
		return compositeEntity.getDescendents();
	}
	protected void setDescendents(Set<Type> descendents) {
		compositeEntity.setDescendents(descendents);
	}
	public Set<Type> listDescendents() {
		return compositeEntity.listDescendents();
	}

	
	@OneToMany( mappedBy="target", fetch=FetchType.LAZY,  cascade = { CascadeType.PERSIST } )
	protected Set<TypeLink> getParents() {
		return compositeEntity.getParents();
	}
	protected void setParents(Set<TypeLink> parents) {
		compositeEntity.setParents(parents);
	}
	public Set<TypeLink> listParents() {
		return compositeEntity.listParents();
	}

	
	@OneToMany( mappedBy="source", fetch=FetchType.LAZY, cascade = { CascadeType.PERSIST } )
	protected Set<TypeLink> getChildren() {
		return compositeEntity.getChildren();
	}
	protected void setChildren(Set<TypeLink> children) {
		compositeEntity.setChildren(children);
	}
	public Set<TypeLink> listChildren() {
		return compositeEntity.listChildren();
	}
	public List<TypeLink> listChildrenOrdered() {
		return compositeEntity.listChildrenOrdered();
	}
	@Override
	public void refreshChildrenOrdered() {
		compositeEntity.refreshChildrenOrdered();
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
	
	
	public void init() {
		compositeEntity.init();
	}
	public void delete() {
		if ( !isDeletable() ) throw new UnsupportedOperationException();
		
		Set<TypeLink> children = new HashSet<TypeLink>();
		children.addAll( listChildren() );
		
		for ( TypeLink link : children ) {
			Type child = link.getTarget();
			
			link.delete();
			
			if ( child != null && child.listParents().size() == 0 && child.getAnonymous() ) {
				child.delete();
			}
		} 
		GarbageCollector.getInstance().garbage( this );
	}

	@Transient
	private boolean isDeletable() {
		return getParents().size() <= 1;
	}
	
	
	public boolean isValidReference(TimedEntity<?, ?> timedEntity) {
		return timedEntity.isValidReference(timedEntity);
	}
	public boolean isValidAt(Time time) {
		return timedEntity.isValidAt(time);
	}

	public abstract void accept( TypeVisitor visitor );
}
