package it.unifi.ing.stlab.factquery.model;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.factquery.model.expression.Expression;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table( name = "fact_queries" )
public class FactQuery implements Persistable {

	private Expression expression;
	
	private String name;
	private String description;
	private Integer limit;
	private Integer offset;
	private Boolean fetch;
	
	private PersistableImpl persistable;
	
	protected FactQuery(){
		persistable = new PersistableImpl();
	}
	public FactQuery(String uuid){
		persistable = new PersistableImpl( uuid );
	}

	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="fact_query", 
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
		persistable.setUuid( uuid );
	}
	
	
	@ManyToOne( fetch=FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE } )
	@JoinColumn( name="expression_id" )
	public Expression getExpression() {
		return expression;
	}
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	
	@Column( name="name", unique=true )
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
	
	
	@Column( name="result_limit" )
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	
	@Column( name="result_offset" )
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
	
	@Column( name="query_fetch" )
	public Boolean getFetch() {
		return fetch;
	}
	public void setFetch(Boolean fetch) {
		this.fetch = fetch;
	}
	
	
}
