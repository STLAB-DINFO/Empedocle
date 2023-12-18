package it.unifi.ing.stlab.view.model.links;

import it.unifi.ing.stlab.entities.implementation.composite.CompositeLinkImpl;
import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.composite.CompositeLink;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.view.model.Viewer;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
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
@Table( name = "viewer_links" )
@DiscriminatorColumn( 
		name= "from_class", 
		discriminatorType=DiscriminatorType.STRING )
public abstract class ViewerLink 
				implements Persistable,CompositeLink<Viewer, ViewerLink> {

	private TypeSelector selector;
	
	private PersistableImpl persistable;
	private CompositeLinkImpl<Viewer, ViewerLink> compositeLink;
	
	public ViewerLink(String uuid) {
		super();
		persistable = new PersistableImpl( uuid );
		compositeLink = new CompositeLinkImpl<Viewer, ViewerLink>();
		compositeLink.setDelegator(this);
	}
	protected ViewerLink() {
		super();
		persistable = new PersistableImpl();
		compositeLink = new CompositeLinkImpl<Viewer, ViewerLink>();
		compositeLink.setDelegator(this);
	}

	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="viewer_link", 
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
	
	@ManyToOne( fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE} )
	@JoinColumn(name="selector")
	public TypeSelector getSelector() {
		return selector;
	}
	public void setSelector(TypeSelector selector) {
		this.selector = selector;
	}

	@Override
	public int hashCode() {
		return persistable.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}
	
	
	@ManyToOne( fetch=FetchType.LAZY )
	@JoinColumn( name="source_id")
	public Viewer getSource() {
		return compositeLink.getSource();
	}
	protected void setSource(Viewer source) {
		compositeLink.setSource(source);
	}
	public void assignSource(Viewer newSource) {
		//FIXME è corretto assegnare automaticamente la priorità qui?
		setPriority(new Long(newSource.listChildren().size()));
		compositeLink.assignSource(newSource);
	}
	
	@ManyToOne( fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn( name="target_id")
	public Viewer getTarget() {
		return compositeLink.getTarget();
	}
	protected void setTarget(Viewer target) {
		compositeLink.setTarget(target);
	}
	public void assignTarget(Viewer newTarget) {
		compositeLink.assignTarget(newTarget);
	}
	
	
	@Override
	public void delete() {
		compositeLink.delete();
	}
	@Override
	public Long getPriority() {
		return compositeLink.getPriority();
	}
	@Override
	public void setPriority(Long priority) {
		compositeLink.setPriority(priority);
	}

}
