package it.unifi.ing.stlab.reflection.model.types.links;

import it.unifi.ing.stlab.entities.implementation.composite.CompositeLinkImpl;
import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.composite.CompositeLink;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Entity
@Table( name = "type_links" )
public class TypeLink 
	implements CompositeLink<Type,TypeLink>, Persistable {

	private PersistableImpl persistable;
	private CompositeLinkImpl<Type,TypeLink> compositeLink;
	private String name;
	private Integer min;
	private Integer max;
	private FactValue defaultValue;

	
	public TypeLink( String uuid ) {
		persistable = new PersistableImpl( uuid );
		compositeLink = new CompositeLinkImpl<Type,TypeLink>();
		compositeLink.setDelegator( this );
	}
	protected TypeLink() {
		persistable = new PersistableImpl();
		compositeLink = new CompositeLinkImpl<Type,TypeLink>();
		compositeLink.setDelegator( this );
	}
	
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="type_link", 
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
	
	
	// Name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Column( name = "min_value" )
	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	
	
	@Column( name = "max_value" )
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	
	
	@ManyToOne( fetch=FetchType.LAZY, cascade = { CascadeType.PERSIST } )
	@JoinColumn( name="source_id")
	public Type getSource() {
		return compositeLink.getSource();
	}
	protected void setSource(Type source) {
		compositeLink.setSource(source);
	}
	public void assignSource(Type newSource) {
		if ( !isValidSource( newSource )) 
			throw new IllegalArgumentException();
		
		compositeLink.assignSource(newSource);
	}

	private boolean isValidSource( Type newSource ) {
		if ( newSource == null ) return true;
		if ( ClassHelper.instanceOf( newSource, CompositeType.class )) return true;
		
		return false;
	}
	
	@ManyToOne( fetch=FetchType.LAZY, cascade = { CascadeType.PERSIST } )
	@JoinColumn( name="target_id")
	public Type getTarget() {
		return compositeLink.getTarget();
	}
	protected void setTarget(Type target) {
		compositeLink.setTarget(target);
	}
	public void assignTarget(Type newTarget) {
		compositeLink.assignTarget(newTarget);
	}

	
	// Priority
	public Long getPriority() {
		return compositeLink.getPriority();
	}
	public void setPriority(Long priority) {
		compositeLink.setPriority(priority);
	}
	
	@ManyToOne( fetch=FetchType.LAZY, cascade = { CascadeType.ALL } )
	@JoinColumn( name="default_value")
	public FactValue getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(FactValue defaultValue) {
		this.defaultValue = defaultValue;
	}

	
	//
	// Methods
	//
	public void delete() {
		compositeLink.delete();
	}
	
	public int hashCode() {
		return persistable.hashCode();
	}
	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}
}
