package it.unifi.ing.stlab.factquery.model.expression;

import it.unifi.ing.stlab.entities.implementation.composite.CompositeLinkImpl;
import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.composite.CompositeLink;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.entities.utils.ClassHelper;

import javax.persistence.CascadeType;
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
@Table( name = "expression_links" )
public class ExpressionLink 
		implements CompositeLink<Expression, ExpressionLink>, Persistable {

	private PersistableImpl persistable;
	private CompositeLinkImpl<Expression, ExpressionLink> compositeLink;
	
	public ExpressionLink(String uuid) {
		persistable = new PersistableImpl( uuid );
		compositeLink = new CompositeLinkImpl<Expression, ExpressionLink>();
		compositeLink.setDelegator( this );
	}
	protected ExpressionLink() {
		persistable = new PersistableImpl();
		compositeLink = new CompositeLinkImpl<Expression, ExpressionLink>();
		compositeLink.setDelegator( this );
	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="expression_link", 
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

	
	@ManyToOne( fetch=FetchType.LAZY, cascade = { CascadeType.PERSIST } )
	@JoinColumn( name="source_id")
	public Expression getSource() {
		return compositeLink.getSource();
	}
	protected void setSource(Expression source) {
		compositeLink.setSource(source);
	}
	public void assignSource(Expression newSource) {
		if ( !isValidSource( newSource ) ) 
			throw new IllegalArgumentException();
		
		compositeLink.assignSource( newSource );
		
	}
	
	private boolean isValidSource(Expression newSource) {
		if ( newSource == null ) return true;
		if ( ClassHelper.instanceOf( newSource, CompositeExpression.class ) ) return true;
		
		return false;
	}

	
	@ManyToOne( fetch=FetchType.LAZY, cascade = { CascadeType.PERSIST } )
	@JoinColumn( name="target_id")
	public Expression getTarget() {
		return compositeLink.getTarget();
	}
	protected void setTarget(Expression target) {
		compositeLink.setTarget( target );
	}
	public void assignTarget(Expression newTarget) {
		compositeLink.assignTarget( newTarget );
	}
	

	// Priority
	public Long getPriority() {
		return compositeLink.getPriority();
	}
	public void setPriority(Long priority) {
		compositeLink.setPriority( priority );
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
