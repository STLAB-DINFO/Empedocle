package it.unifi.ing.stlab.view.model.widgets.input;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.widgets.ViewerInput;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("TEXTAREA")
public class TextArea extends ViewerInput {

	private Integer inputLength;

	public TextArea(String uuid) {
		super(uuid);
	}
	protected TextArea() {
		super();
	}
	
	@Column( name = "input_length")
	public Integer getInputLength() {
		return inputLength;
	}
	public void setInputLength(Integer inputLength) {
		this.inputLength = inputLength;
	}
	
	@Transient
	@Override
	public String getXhtml() {
		return "../component/textArea.xhtml";
	}
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitTextArea(this);
	}
	
}