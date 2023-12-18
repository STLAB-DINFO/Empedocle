package it.unifi.ing.stlab.view.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.richfaces.component.UITree;
import org.richfaces.event.TreeSelectionChangeEvent;

import it.unifi.ing.stlab.view.actions.wrappers.ViewerBean;
import it.unifi.ing.stlab.view.actions.wrappers.ViewerTreeNode;
import it.unifi.ing.stlab.view.model.links.ViewerLink;

public abstract class ViewerController implements Serializable {

	
	
	
	
	
	//=============================================================
	//					OLD STUFF
	//=============================================================
	
	//FIXME una volta finito questo bean, generare UID 
	private static final long serialVersionUID = 1L;

	//
	// Local attributes
	//
	
	private ViewerLink selectedLink;
	private ViewerBean selectedViewer;
	protected ViewerTreeNode currentSelectedNode = null;
	
	public ViewerLink getSelectedLink() {
		return selectedLink;
	}
	public void setSelectedLink(ViewerLink selectedLink) {
		if ( this.selectedLink != null && !this.selectedLink.equals( selectedLink )) {
			selectedViewer = null;
		}
		
		this.selectedLink = selectedLink;
		
	}


	public ViewerBean getSelectedViewer() {
		if ( selectedViewer == null ) {
			initSelectedViewer();
		}
		return selectedViewer;
	}
	private void initSelectedViewer() {
		if ( selectedLink == null || selectedLink.getTarget() == null ) return;

		selectedViewer = new ViewerBean( selectedLink.getTarget() );
	}
	
	
	public void selectionChanged(TreeSelectionChangeEvent selectionChangeEvent) {
		if(selectionChangeEvent.getNewSelection().size() > 0) {
			List<Object> selection = new ArrayList<Object>( selectionChangeEvent.getNewSelection());
	        Object currentSelectionKey = selection.get(0);
	        UITree tree = (UITree) selectionChangeEvent.getSource();
	 
	        Object storedKey = tree.getRowKey();
	        tree.setRowKey(currentSelectionKey);
	        
	        currentSelectedNode = (ViewerTreeNode) tree.getRowData();
	        
	        selectedLink = ((ViewerTreeNode)tree.getRowData()).getLink();
	        selectedViewer = null;
	        
	        tree.setRowKey(storedKey);
		}
    }
	
}
