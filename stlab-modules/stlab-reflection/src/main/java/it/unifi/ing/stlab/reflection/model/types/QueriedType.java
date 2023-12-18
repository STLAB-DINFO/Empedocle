package it.unifi.ing.stlab.reflection.model.types;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "QQL" )
public class QueriedType extends QualitativeType {

	private String queryDef;

	public QueriedType(String uuid) {
		super(uuid);
	}
	protected QueriedType() {
		super();
	}

	@Column( name = "query_def" )
	public String getQueryDef() {
		return queryDef;
	}
	public void setQueryDef(String queryDef) {
		this.queryDef = queryDef;
	}
	
	@Override
	public void accept(TypeVisitor visitor) {
		visitor.visitQueriedType( this );
	}
}
