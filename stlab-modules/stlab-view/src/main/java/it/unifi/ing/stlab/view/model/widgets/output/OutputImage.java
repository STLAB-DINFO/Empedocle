package it.unifi.ing.stlab.view.model.widgets.output;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.widgets.ViewerOutput;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "IMAGE" )
public class OutputImage extends ViewerOutput {

	private String imagePath;
	
	public OutputImage(String uuid){
		super(uuid);
	}
	protected OutputImage(){
		super();
	}
	
	@Column( name = "image_path")
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	@Transient
	@Override
	public String getXhtml() {
		return "../component/outputImage.xhtml";
	}

	@Override
	public void accept(ViewerVisitor v){
		v.visitOutputImage(this);
	}
}
