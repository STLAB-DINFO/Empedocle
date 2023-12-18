package it.unifi.ing.stlab.view.model.links;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table( name = "selectors" )
public class TypeSelector implements Persistable{
 
	private TypeLink typeLink;
	private TypeSelector next;
	private TypeSelector root;
	
	private PersistableImpl persistable;
	
	public TypeSelector(String uuid) {
		super();
		persistable = new PersistableImpl(uuid);
		setRoot(this);
	}

	protected TypeSelector() {
		super();
		persistable = new PersistableImpl();
		setRoot(this);
	}

	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="selector", 
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

	@ManyToOne
	@JoinColumn(name="type_link_id")
	public TypeLink getTypeLink() {
		return typeLink;
	}
	public void setTypeLink(TypeLink typeLink) {
		this.typeLink = typeLink;
	}

	@OneToOne( cascade = {CascadeType.PERSIST, CascadeType.REMOVE} )
	@JoinColumn(name="next_id")
	public TypeSelector getNext() {
		return next;
	}
	protected void setNext(TypeSelector next) {
		this.next = next;
	}
	public void assignNext(TypeSelector next) {
		if(next != null) {
			setNext(next);
			next.setRoot(this.getRoot());
		}
		else {
			unlinkNext();
		}
	}
	public void unlinkNext() {
		if(getNext() != null) {
			getNext().setRoot(null);
			setNext(null);
		}
	}
	
	@ManyToOne
	@JoinColumn(name="root_id")
	public TypeSelector getRoot() {
		return root;
	}
	protected void setRoot(TypeSelector root) {
		this.root = root;
	}

	public TypeLink applyType(Type t){
		TypeLink filtered = null;
		
		if( t != null && ClassHelper.instanceOf( t, CompositeType.class )) {
			for(TypeLink link : t.listChildren()){
				if(link.equals(typeLink)){
					filtered = link;
					break;
				}
			}
		}
		
		return next != null & filtered != null ? next.applyType(filtered.getTarget()) : filtered;
	}

	
	
	public Fact apply(Fact fact) {
		if ( fact == null ) return null;
		if ( !ClassHelper.instanceOf( fact, CompositeFact.class )) return null;

		for (FactLink link : fact.listActiveLinks()) {
			if ( link.getType() != null && link.getType().equals( typeLink )) {
				if ( next == null ) {
					return link.getTarget();
				} else {
					return next.apply( link.getTarget() );
				}
			}
		}

		return null;
	}

	public Set <FactLink> applyItem(Fact fact ) {
		if ( fact == null || !ClassHelper.instanceOf( fact, CompositeFact.class )) return new HashSet<FactLink>();
		
		if( next != null ){
			for (FactLink link : fact.listActiveLinks()) {
				if ( link.getType().equals( typeLink )) {
					return next.applyItem( link.getTarget() );
				}
			}
			
			return null;
				
		} else{
			Set<FactLink> result = new HashSet<FactLink>();

			for (FactLink link : fact.listActiveLinks()) {
				if (link.getType().equals( typeLink )) {
					result.add( link );
				}
			}
			return result;
		}
	}

	@Override
	public int hashCode() {
		return persistable.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}
	
	@Transient
	public int getLength(){
		int i = 1;
		TypeSelector next = this;
		while(next.getNext() != null){
			i++;
			next = next.getNext();
		}
		
		return i;
	}
	
	@Transient
	public TypeSelector getLast() {
		TypeSelector last = this;
		while(last.getNext() != null) {
			last = last.getNext();
		}
		
		return last;
	}
	
}