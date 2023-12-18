package it.unifi.ing.stlab.factquery.model.expression;

import it.unifi.ing.stlab.entities.implementation.composite.CompositeEntityImpl;
import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.composite.CompositeEntity;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table( name="expressions" )
@Inheritance( strategy=InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( 
	name= "from_class", 
	discriminatorType=DiscriminatorType.STRING )
public abstract class Expression 
		implements CompositeEntity<Expression, ExpressionLink>, Persistable {

	private PersistableImpl persistable;
	private CompositeEntityImpl<Expression, ExpressionLink> compositeEntity;
	
	
	public Expression(String uuid) {
		persistable = new PersistableImpl( uuid );
		compositeEntity = new CompositeEntityImpl<Expression, ExpressionLink>();
		compositeEntity.setDelegator( this );
	}
	protected Expression() {
		persistable = new PersistableImpl();
		compositeEntity = new CompositeEntityImpl<Expression, ExpressionLink>();
		compositeEntity.setDelegator( this );
	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="expression", 
		allocationSize = 10 )
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

	
	@ManyToMany( fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST } )
	@JoinTable(
		name = "expression_ancestors",
	    joinColumns = { @JoinColumn( name = "expression_id", referencedColumnName="id" ) },
	    inverseJoinColumns = { @JoinColumn( name = "ancestor_expression_id", referencedColumnName = "id") } )
	protected Set<Expression> getAncestors() {
		return compositeEntity.getAncestors();
	}
	protected void setAncestors(Set<Expression> ancestors) {
		compositeEntity.setAncestors(ancestors);
	}
	public Set<Expression> listAncestors() {
		return compositeEntity.listAncestors();
	}
	
	
	@ManyToMany( mappedBy = "ancestors", fetch=FetchType.LAZY, cascade = { CascadeType.PERSIST } )
	protected Set<Expression> getDescendents() {
		return compositeEntity.getDescendents();
	}
	protected void setDescendents(Set<Expression> descendents) {
		compositeEntity.setDescendents(descendents);
	}
	public Set<Expression> listDescendents() {
		return compositeEntity.listDescendents();
	}

	
	@OneToMany( mappedBy="target", fetch=FetchType.LAZY,  cascade = { CascadeType.PERSIST } )
	protected Set<ExpressionLink> getParents() {
		return compositeEntity.getParents();
	}
	protected void setParents(Set<ExpressionLink> parents) {
		compositeEntity.setParents(parents);
	}
	public Set<ExpressionLink> listParents() {
		return compositeEntity.listParents();
	}
	

	@OneToMany( mappedBy="source", fetch=FetchType.LAZY, cascade = { CascadeType.PERSIST } )
	protected Set<ExpressionLink> getChildren() {
		return compositeEntity.getChildren();
	}
	protected void setChildren(Set<ExpressionLink> children) {
		compositeEntity.setChildren(children);
	}
	public Set<ExpressionLink> listChildren() {
		return compositeEntity.listChildren();
	}
	public List<ExpressionLink> listChildrenOrdered() {
		return compositeEntity.listChildrenOrdered();
	}
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
		compositeEntity.delete();
	}

	public abstract void accept(ExpressionVisitor visitor);
}
