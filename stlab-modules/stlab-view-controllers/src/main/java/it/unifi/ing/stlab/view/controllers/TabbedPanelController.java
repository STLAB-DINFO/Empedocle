package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.view.model.links.Tab;
import it.unifi.ing.stlab.view.model.widgets.container.TabbedPanel;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Named
@ConversationScoped
public class TabbedPanelController extends ContainerController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final Map<TabbedPanel, String> panelMap = new HashMap<TabbedPanel, String>();
	
	public String getRenderedTab(TabbedPanel tabbedPanel){
		String result = panelMap.get(tabbedPanel);
		if( result == null || result.isEmpty()) {
			result = (ClassHelper.cast( tabbedPanel.getByPriority( 0l ) , Tab.class)).getLabel();
			panelMap.put( tabbedPanel, result );
		}
		
		return result;
	}
	
	
	public void activateTab(TabbedPanel tabbedPanel, String tab){
		if( tab == null )
			throw new IllegalArgumentException("null non è un parametro accettabile");
			
		panelMap.put(tabbedPanel, tab );
	}
	
}
