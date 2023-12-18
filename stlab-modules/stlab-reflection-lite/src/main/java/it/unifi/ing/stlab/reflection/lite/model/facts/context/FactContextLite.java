package it.unifi.ing.stlab.reflection.lite.model.facts.context;

import it.unifi.ing.stlab.reflection.model.facts.FactContext;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class FactContextLite extends FactContext {

	private byte[] content;
	
	public FactContextLite( String uuid ) {
		super( uuid );
	}
	protected FactContextLite() {
		super();
	}
	
	
	@Lob
	@Basic( fetch=FetchType.LAZY )
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	
}
