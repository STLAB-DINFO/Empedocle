package it.unifi.ing.stlab.reflection.impl.model.facts.links;

import it.unifi.ing.stlab.entities.implementation.compact.CompactLinkImpl;
import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.compact.CompactLink;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table( name = "fact_links" )
@DiscriminatorColumn( 
		name= "from_class", 
		discriminatorType=DiscriminatorType.STRING )
public abstract class FactLinkImpl 
	implements FactLink,
				CompactLink<FactImpl,FactLinkImpl>, Persistable {
	
	private PersistableImpl persistable;
	private CompactLinkImpl<FactImpl,FactLinkImpl> delegate;
	
	private TypeLink type;

	public FactLinkImpl( String uuid ) {
		persistable = new PersistableImpl( uuid );
	}
	protected FactLinkImpl() {
		persistable = new PersistableImpl();
	}
	
	@Transient
	protected CompactLinkImpl<FactImpl, FactLinkImpl> getDelegate() {
		return delegate;
	}
	protected void setDelegate(CompactLinkImpl<FactImpl, FactLinkImpl> delegate) {
		this.delegate = delegate;
	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="fact_link", 
		allocationSize = 50 )
	@GeneratedValue(strategy=GenerationType.TABLE, generator="table_gen")	
	public Long getId() {
		return persistable.getId();
	}
	public void setId(Long id) {
		persistable.setId(id);
	}

	
	
	// UUID
	public String getUuid() {
		return persistable.getUuid();
	}
	public void setUuid(String uuid) {
		persistable.setUuid(uuid);
	}

	
	// Priority
	public Long getPriority() {
		return delegate.getPriority();
	}
	public void setPriority(Long priority) {
		delegate.setPriority(priority);
	}
	
	@Override
	@ManyToOne
	@JoinColumn( name = "source_id" )
	public FactImpl getSource() {
		return delegate.getSource();
	}
	protected void setSource(FactImpl source) {
		delegate.setSource(source);
	}
	public void assignSource(FactImpl newSource) {
		delegate.assignSource(newSource);
	}
	

	@Override
	@ManyToOne
	@JoinColumn( name = "target_id" )
	public FactImpl getTarget() {
		return delegate.getTarget();
	}
	protected void setTarget(FactImpl target) {
		delegate.setTarget(target);
	}
	public void assignTarget(FactImpl newTarget) {
		delegate.assignTarget(newTarget);
	}
	
	@ManyToOne
	@JoinColumn( name = "type_id" )
	public TypeLink getType() {
		return type;
	}
	public void setType(TypeLink linkType) {
		this.type = linkType;
	}
	
	//
	// Methods
	//
	public void delete() {
		delegate.delete();
	}

	public int hashCode() {
		return persistable.hashCode();
	}
	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}

}
