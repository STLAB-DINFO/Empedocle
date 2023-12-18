package it.unifi.ing.stlab.reflection.actions;

import it.unifi.ing.stlab.reflection.actions.wrappers.LinkBean;
import it.unifi.ing.stlab.reflection.actions.wrappers.TypeBean;
import it.unifi.ing.stlab.reflection.actions.wrappers.TypeTreeNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.richfaces.component.UITree;
import org.richfaces.event.TreeSelectionChangeEvent;

public abstract class TypeController implements Serializable {

	private static final long serialVersionUID = 1L;

	//
	// Local attributes
	//
	
	private LinkBean selectedLink;
	
	private TypeBean selectedType;

	protected TypeTreeNode currentSelectedNode = null;
	
	
	public LinkBean getSelectedLink() {
		return selectedLink;
	}
	public void setSelectedLink(LinkBean selectedLink) {
		if ( this.selectedLink != null && !this.selectedLink.equals( selectedLink )) {
			selectedType = null;
		}
		
		this.selectedLink = selectedLink;
		
	}
	
	public void clearSelectedType() {
		selectedType = null;
	}


	public TypeBean getSelectedType() {
		if ( selectedType == null ) {
			initSelectedType();
		}
		return selectedType;
	}
	private void initSelectedType() {
		if ( selectedLink == null ||
				selectedLink.getLink() == null ||
				selectedLink.getLink().getTarget() == null ) return;

		selectedType = new TypeBean( getEntityManager(),  selectedLink.getLink().getTarget() );
	}
	
	
	protected abstract EntityManager getEntityManager();
	
	public void selectionChanged(TreeSelectionChangeEvent selectionChangeEvent) {
		if(selectionChangeEvent.getNewSelection().size() > 0) {
	        List<Object> selection = new ArrayList<Object>( selectionChangeEvent.getNewSelection());
	        Object currentSelectionKey = selection.get(0);
	        UITree tree = (UITree) selectionChangeEvent.getSource();
	 
	        Object storedKey = tree.getRowKey();
	        tree.setRowKey(currentSelectionKey);
	        
	        currentSelectedNode = (TypeTreeNode) tree.getRowData();
	        
	        selectedLink = new LinkBean( ((TypeTreeNode)tree.getRowData()).getLink(), getEntityManager() );
	        selectedType = null;
	        
	        tree.setRowKey(storedKey);
		}
    }

	public Boolean isCurrentLinkSet() {
		return getSelectedLink() != null && getSelectedLink().getLink() != null;
	}
	
}
