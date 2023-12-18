package it.unifi.ing.stlab.view.actions.wrappers;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.container.Box;
import it.unifi.ing.stlab.view.model.widgets.container.ConditionalPanel;
import it.unifi.ing.stlab.view.model.widgets.container.Grid;
import it.unifi.ing.stlab.view.model.widgets.container.Report;
import it.unifi.ing.stlab.view.model.widgets.container.TabbedPanel;
import it.unifi.ing.stlab.view.model.widgets.input.Combo;
import it.unifi.ing.stlab.view.model.widgets.input.FileUpload;
import it.unifi.ing.stlab.view.model.widgets.input.InputList;
import it.unifi.ing.stlab.view.model.widgets.input.InputText;
import it.unifi.ing.stlab.view.model.widgets.input.Suggestion;
import it.unifi.ing.stlab.view.model.widgets.input.TextArea;
import it.unifi.ing.stlab.view.model.widgets.output.Label;
import it.unifi.ing.stlab.view.model.widgets.output.OutputField;
import it.unifi.ing.stlab.view.model.widgets.output.OutputImage;
import it.unifi.ing.stlab.view.model.widgets.output.OutputList;
import it.unifi.ing.stlab.view.model.widgets.output.OutputMeasurementUnit;
import it.unifi.ing.stlab.view.model.widgets.output.OutputPath;
import it.unifi.ing.stlab.view.model.widgets.output.OutputType;
import it.unifi.ing.stlab.view.model.widgets.output.OutputValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.richfaces.model.TreeNodeImpl;

// FIXME codice duplicato con TypeTreeNode
public class ViewerTreeNode extends TreeNodeImpl {

	private ViewerLink link;
	private boolean expanded;

	
/*  in TypeTreeNode:  
	public TypeTreeNode() {
        this(null, null);
    }

    public TypeTreeNode(Boolean leaf, TypeLink link ) {
        super(leaf);
        this.link = link;
        expanded = true;
    }*/

    public ViewerTreeNode() {
        super();
        expanded = true;
    }

    public ViewerTreeNode(boolean leaf, ViewerLink link ) {
        super(leaf);
        this.link = link;
        expanded = true;
    }

    public ViewerLink getLink() {
		return link;
    }
    
    public boolean isReference() {
    	return link != null && ( link.getTarget() == null || !link.getTarget().getAnonymous() );
    }
    
    public String getLabel() {
    	if ( link == null ) return "root";
    	
    	return component( link );
    }

//    private String selector( ViewerLink link ) {
//    	if ( link == null || link.getSelector() == null ) return "";
//
//    	boolean first = true;
//    	TypeSelector current = link.getSelector();
//    	StringBuffer buffer = new StringBuffer();
//    
//    	while ( current != null ) {
//    		if ( !first ) {
//    			buffer.append( " -> " );
//    		} else {
//    			first = false;
//    		}
//    		
//    		if ( current.getTypeLink() != null ) {
//    			buffer.append( current.getTypeLink().getName() );
//    		} else {
//    			buffer.append( "?" );
//    		}
//    		
//    		current = current.getNext();
//    	}
//    
//    	return buffer.toString();
//    }
    
    private String component( ViewerLink link ) {
    	if ( link == null || link.getTarget() == null ) return "";
    	
    	// FIXME delegare a Viewer
    	if ( ClassHelper.instanceOf( link.getTarget(), Box.class )) {
    		return "box";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), ConditionalPanel.class )) {
    		return "conditional-panel";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), Grid.class )) {
    		return "grid";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), Report.class )) {
    		return "report";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), TabbedPanel.class )) {
    		return "tabbed-panel";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), Combo.class )) {
    		return "combo";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), InputList.class )) {
    		return "input-list";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), InputText.class )) {
    		return "input-text";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), Suggestion.class )) {
    		return "suggestion";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), TextArea.class )) {
    		return "text-area";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), FileUpload.class )) {
    		return "file-upload";	
    	} else if ( ClassHelper.instanceOf( link.getTarget(), Label.class )) {
    		return "label";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), OutputField.class )) {
    		return "output-field";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), OutputImage.class )) {
    		return "output-image";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), OutputList.class )) {
    		return "output-list";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), OutputMeasurementUnit.class )) {
    		return "output-unit";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), OutputPath.class )) {
    		return "output-path";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), OutputType.class )) {
    		return "output-type";
    	} else if ( ClassHelper.instanceOf( link.getTarget(), OutputValue.class )) {
    		return "output-value";
    	} else {
    		return "?";
    	}
    	
    }
    
    
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public ViewerTreeNode find( String uuid ) {
		if ( uuid == null && getLink() == null ) return this;
		if ( uuid == null && getLink() != null ) return null;
		if ( getLink() != null && uuid.equals( getLink().getUuid() )) return this;
		
		Iterator<Object> it = getChildrenKeysIterator();
		while ( it.hasNext() ) {
			ViewerTreeNode child = (ViewerTreeNode)getChild( it.next() );
			
			ViewerTreeNode result = child.find( uuid );
			if ( result != null ) {
				return result;
			}
		}
		
		return null;
	}
	
	public boolean contains( ViewerTreeNode node ) {
		if ( node == null ) return false;
		
		Iterator<Object> it = getChildrenKeysIterator();
		while ( it.hasNext() ) {
			
			ViewerTreeNode child = (ViewerTreeNode)getChild( it.next() );
			if ( child.equals( node )) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public boolean deepContains( ViewerTreeNode node )  {
		if ( node == null ) return false;
		if ( node.equals( this )) return true;

		
		Iterator<Object> it = getChildrenKeysIterator();
		while ( it.hasNext() ) {
			
			ViewerTreeNode child = (ViewerTreeNode)getChild( it.next() );
			boolean result = child.deepContains( node );
			if ( result == true ) {
				return true;
			}
		}
		
		return false;
	}
	
	public ViewerTreeNode parentOf( ViewerTreeNode node ) {
		if ( node == null ) return null;
		if ( contains( node )) return this;
		
		Iterator<Object> it = getChildrenKeysIterator();
		while ( it.hasNext()) {
			ViewerTreeNode child = (ViewerTreeNode)getChild( it.next() );
			ViewerTreeNode result = child.parentOf( node );
			if ( result != null ) {
				return result;
			}
		}
		
		return null;
	}

	public void insertAfter( ViewerTreeNode position, ViewerTreeNode value ) {
		if ( value == null ) return;
		if ( !contains( position )) throw new IllegalArgumentException( position + " is not child of " + this );

		List<ViewerTreeNode> children = new ArrayList<ViewerTreeNode>();
		
		Iterator<Object> it = getChildrenKeysIterator();
		while ( it.hasNext()) {
			Object key = it.next();
			ViewerTreeNode child = (ViewerTreeNode)getChild( key );
			children.add( child );
			if ( child.equals( position )) {
				children.add( value );
			}
		}
		
		while ( getChildrenKeysIterator().hasNext() ) {
			removeChild( getChildrenKeysIterator().next() );
		}
		

		for ( ViewerTreeNode child : children ) {
			addChild( child.getLink().getUuid(), child );
		}
	}
	
	public void adjustChildrenPriority() {

		long priority = 0;
		
		Iterator<Object> it = getChildrenKeysIterator();
		while ( it.hasNext()) {
			ViewerTreeNode child = (ViewerTreeNode)getChild( it.next() );
			if ( child.getLink() != null ) {
				child.getLink().setPriority( priority );
			}
			priority++;
		}
	}
    
}