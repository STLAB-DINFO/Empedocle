package it.unifi.ing.stlab.reflection.impl.model.facts;

import it.unifi.ing.stlab.entities.implementation.compact.CompactEntityImpl;
import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.implementation.timed.TimedEntityImpl;
import it.unifi.ing.stlab.entities.model.compact.CompactEntity;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.entities.model.timed.TimedEntity;
import it.unifi.ing.stlab.reflection.impl.model.facts.actions.FactAction;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactLinkImpl;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactContext;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table( name = "facts" )
@Inheritance( strategy=InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( 
	name= "from_class", 
	discriminatorType=DiscriminatorType.STRING )
public abstract class FactImpl 
	implements Fact, CompactEntity<FactImpl, FactLinkImpl, FactAction>,
				Persistable, TimedEntity<TimeRange, Time> {

	private PersistableImpl persistable;
	private TimedEntityImpl<TimeRange, Time> timedEntity;
	private CompactEntityImpl<FactImpl, FactLinkImpl, FactAction> compactEntity;
	private Type type;
	private FactStatus status;
	private FactContext context;
	private List<FactLinkImpl> childrenOrdered;
	
	
	public FactImpl( String uuid ) {
		persistable = new PersistableImpl( uuid );
		timedEntity = new TimedEntityImpl<TimeRange, Time>();
		compactEntity = new CompactEntityImpl<FactImpl, FactLinkImpl, FactAction>();
		compactEntity.setDelegator( this );
		refreshChildrenOrdered();
	}
	protected FactImpl() {
		persistable = new PersistableImpl();
		timedEntity = new TimedEntityImpl<TimeRange, Time>();
		compactEntity = new CompactEntityImpl<FactImpl, FactLinkImpl, FactAction>();
		compactEntity.setDelegator( this );
		refreshChildrenOrdered();
	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="fact", 
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

	
	@Embedded
	public TimeRange getTimeRange() {
		return timedEntity.getTimeRange();
	}
	public void setTimeRange(TimeRange timeRange) {
		timedEntity.setTimeRange(timeRange);
	}

	
	@ManyToOne( fetch=FetchType.LAZY )
	@JoinColumn( name = "type_id" )
	public Type getType() {
		return type;
	}
	protected void setType(Type type) {
		this.type = type;
	}
	public abstract void assignType( Type type );
	

	@ManyToOne( fetch=FetchType.LAZY )
	@JoinColumn( name = "context_id" )
	public FactContext getContext() {
		return context;
	}
	public void setContext(FactContext context) {
		this.context = context;
	}
	
	@Enumerated( EnumType.STRING )
	public FactStatus getStatus() {
		return status;
	}
	public void setStatus(FactStatus status) {
		this.status = status;
	}
	
	@ManyToMany( fetch=FetchType.LAZY )
	@JoinTable(
		name = "fact_before",
	    joinColumns = { @JoinColumn( name = "fact_id", referencedColumnName="id" ) },
	    inverseJoinColumns = { @JoinColumn( name = "before_fact_id", referencedColumnName = "id") } )
	protected Set<FactImpl> getBefore() {
		return compactEntity.getBefore();
	}
	protected void setBefore(Set<FactImpl> before) {
		compactEntity.setBefore(before);
	}
	public Set<FactImpl> listBefore() {
		return compactEntity.listBefore();
	}

	
	@ManyToMany( mappedBy = "before", fetch=FetchType.LAZY )
	protected Set<FactImpl> getAfter() {
		return compactEntity.getAfter();
	}
	protected void setAfter(Set<FactImpl> after) {
		compactEntity.setAfter(after);
	}
	public Set<FactImpl> listAfter() {
		return compactEntity.listAfter();
	}

	
	@ManyToOne( fetch=FetchType.LAZY, cascade = { CascadeType.REMOVE } )
	@JoinColumn( name = "origin_id" )
	public FactAction getOrigin() {
		return compactEntity.getOrigin();
	}
	protected void setOrigin(FactAction origin) {
		compactEntity.setOrigin(origin);
	}

	
	@ManyToOne( fetch=FetchType.LAZY, cascade = { CascadeType.REMOVE } )
	@JoinColumn( name = "dest_id" )
	public FactAction getDestination() {
		return compactEntity.getDestination();
	}
	protected void setDestination(FactAction destination) {
		compactEntity.setDestination(destination);
	}

	
	@ManyToMany( fetch=FetchType.LAZY )
	@JoinTable(
		name = "fact_ancestors",
	    joinColumns = { @JoinColumn( name = "fact_id", referencedColumnName="id" ) },
	    inverseJoinColumns = { @JoinColumn( name = "ancestor_fact_id", referencedColumnName = "id") } )
	protected Set<FactImpl> getAncestors() {
		return compactEntity.getAncestors();
	}
	protected void setAncestors(Set<FactImpl> ancestors) {
		compactEntity.setAncestors(ancestors);
	}
	public Set<FactImpl> listAncestors() {
		return compactEntity.listAncestors();
	}

	
	@ManyToMany( mappedBy = "ancestors", fetch=FetchType.LAZY )
	protected Set<FactImpl> getDescendents() {
		return compactEntity.getDescendents();
	}
	protected void setDescendents(Set<FactImpl> descendents) {
		compactEntity.setDescendents(descendents);
	}
	public Set<FactImpl> listDescendents() {
		return compactEntity.listDescendents();
	}

	
	@OneToMany( mappedBy="target", fetch=FetchType.LAZY )
	protected Set<FactLinkImpl> getParents() {
		return compactEntity.getParents();
	}
	protected void setParents(Set<FactLinkImpl> parents) {
		compactEntity.setParents(parents);
	}
	public Set<FactLinkImpl> listParents() {
		return compactEntity.listParents();
	}

	
	@OneToMany( mappedBy="source", fetch=FetchType.LAZY, cascade = { CascadeType.REMOVE } )
	protected Set<FactLinkImpl> getChildren() {
		return compactEntity.getChildren();
	}
	protected void setChildren(Set<FactLinkImpl> children) {
		compactEntity.setChildren(children);
	}
	public Set<FactLinkImpl> listChildren() {
		return compactEntity.listChildren();
	}

	/**
	 * Method that returns a list of Active Links, ordered according to the priority
	 * assigned to the TypeLinks (0: higher priority).
	 * In cases where these are equal and there is a priority
	 * on FactLinks, then it is ordered according to that.
	 * The method creates the ordered list only if it has not been previously created and stores it
	 * as an attribute of the class.
	 *
	 * @return List<FactLink> where FactLink implements CompactLink which in turn extends CompositeLink.
	 */
	public List<FactLinkImpl> listChildrenOrdered() {
		if ( childrenOrdered == null ) {
			initChildrenOrdered();
		}
		return childrenOrdered;
	}

	private void initChildrenOrdered() {
		childrenOrdered = new ArrayList<FactLinkImpl>(listActiveLinks());
		
		Collections.sort( childrenOrdered, new Comparator<FactLinkImpl>() {

			@Override
			public int compare(FactLinkImpl arg0, FactLinkImpl arg1) {
				int p0 = priority( arg0 );
				int p1 = priority( arg1 );
				
				if( p0 == p1 && arg0.getPriority() != null && arg1.getPriority() != null )
					return arg0.getPriority().intValue() - arg1.getPriority().intValue();
				
				return p0 - p1;
			}
			private int priority(FactLinkImpl link) {
				if ( link == null || 
					 link.getType() == null ||
					 link.getType().getPriority() == null ) return 0;

				return link.getType().getPriority().intValue();
			}
			
		});
	}
	
	@Override
	public void refreshChildrenOrdered() {
		childrenOrdered = null;
	}
	
	
	// ActiveLinks
	public Set<FactLinkImpl> listActiveLinks() {
		return compactEntity.listActiveLinks();
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

	@Transient
	public boolean isActive() {
		return compactEntity.isActive();
	}

	public void init() {
		compactEntity.init();
	}

	public void delete() {
		compactEntity.delete();
	}

	public int hashCode() {
		return persistable.hashCode();
	}

	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}
	
	public abstract void assignDefaultValue( FactValue defaultValue );
	public abstract void accept( FactVisitor visitor );
	
}
