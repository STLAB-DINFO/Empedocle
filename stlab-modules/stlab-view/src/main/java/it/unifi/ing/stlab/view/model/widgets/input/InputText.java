package it.unifi.ing.stlab.view.model.widgets.input;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.widgets.ViewerInput;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("INPUTTEXT")
public class InputText extends ViewerInput {

	private Integer inputLength;
	
	public InputText(String uuid) {
		super(uuid);
	}
	protected InputText() {
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
		return "../component/inputText.xhtml";
	}
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitInputText(this);
	}
}