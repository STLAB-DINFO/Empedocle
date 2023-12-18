package it.unifi.ing.stlab.view.actions;

import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.view.model.Viewer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;

// XXX alla chiusura del pop-up viene ri-inizializzato un nuovo fact random.
@Named
@RequestScoped
public class ViewerPreview {
	
	private Fact fact;
	private Viewer viewer;

	@Inject
	private TypeDao typeDao;
	
	@Inject
	private Instance<FactInitializer> initializer;
	
	@Inject
	private ViewerEditDsl viewerEdit;
	
	public boolean hasPreview() {
		return viewerEdit.checkPreview() && viewerEdit.isPreviewEnabled();
	}
	
	//
	// Getters & Setters
	//
	public Fact getFact() {
		if (fact == null)
			initRandomFact();
		return fact;
	}
	
	public Viewer getViewer() {
		if(viewer == null)
			initViewer();
		return viewer;
	}
	
	//
	// Private Methods
	//
	private void initRandomFact() {
		if( !hasPreview() )
			return;
		
		Type type = typeDao.fetchById( viewerEdit.getType().getId() );
		fact = initializer.get().init(type);
	}
	
	private void initViewer() {
		if( !hasPreview() )
			return;
		
		viewer = viewerEdit.getViewer();
		if( viewer != null && viewerEdit.getCss() != null )
			viewer.setCss( viewerEdit.getCss() );
	}

}