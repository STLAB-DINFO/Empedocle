package it.unifi.ing.stlab.reflection.actions.wrappers;

import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import org.richfaces.model.TreeNodeImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TypeTreeNode extends TreeNodeImpl {

	private final TypeLink link;
	private boolean expanded;

    public TypeTreeNode() {
        this(null, null);
    }

    public TypeTreeNode(Boolean leaf, TypeLink link ) {
        super(leaf);
        this.link = link;
        expanded = true;
    }

    public TypeLink getLink() {
		return link;
    }
    
    public boolean isReference() {
    	return link != null && ( link.getTarget() == null || !link.getTarget().getAnonymous() );
    }
    
    public String getLabel() {
    	if ( link == null ) return "root";
    	
    	return link.getName();
    }

	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
	public TypeTreeNode find( String uuid ) {
		if ( uuid == null && getLink() == null ) return this;
		if ( uuid == null && getLink() != null ) return null;
		if ( getLink() != null && uuid.equals( getLink().getUuid() )) return this;
		
		Iterator<Object> it = getChildrenKeysIterator();
		while ( it.hasNext() ) {
			TypeTreeNode child = (TypeTreeNode)getChild( it.next() );
			
			TypeTreeNode result = child.find( uuid );
			if ( result != null ) {
				return result;
			}
		}
		
		return null;
	}
	
	public boolean contains( TypeTreeNode node ) {
		if ( node == null ) return false;
		
		Iterator<Object> it = getChildrenKeysIterator();
		while ( it.hasNext() ) {
			
			TypeTreeNode child = (TypeTreeNode)getChild( it.next() );
			if ( child.equals( node )) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public boolean deepContains( TypeTreeNode node )  {
		if ( node == null ) return false;
		if ( node.equals( this )) return true;

		
		Iterator<Object> it = getChildrenKeysIterator();
		while ( it.hasNext() ) {
			
			TypeTreeNode child = (TypeTreeNode)getChild( it.next() );
			boolean result = child.deepContains( node );
			if ( result == true ) {
				return true;
			}
		}
		
		return false;
	}
	
	public TypeTreeNode parentOf( TypeTreeNode node ) {
		if ( node == null ) return null;
		if ( contains( node )) return this;
		
		Iterator<Object> it = getChildrenKeysIterator();
		while ( it.hasNext()) {
			TypeTreeNode child = (TypeTreeNode)getChild( it.next() );
			TypeTreeNode result = child.parentOf( node );
			if ( result != null ) {
				return result;
			}
		}
		
		return null;
	}

	public void insertAfter( TypeTreeNode position, TypeTreeNode value ) {
		if ( value == null ) return;
		if ( !contains( position )) throw new IllegalArgumentException( position + " is not child of " + this );

		List<TypeTreeNode> children = new ArrayList<TypeTreeNode>();
		
		Iterator<Object> it = getChildrenKeysIterator();
		while ( it.hasNext()) {
			Object key = it.next();
			TypeTreeNode child = (TypeTreeNode)getChild( key );
			children.add( child );
			if ( child.equals( position )) {
				children.add( value );
			}
		}
		
		while ( getChildrenKeysIterator().hasNext() ) {
			removeChild( getChildrenKeysIterator().next() );
		}
		

		for ( TypeTreeNode child : children ) {
			addChild( child.getLink().getUuid(), child );
		}
	}
	
	public void adjustChildrenPriority() {

		long priority = 0;
		
		Iterator<Object> it = getChildrenKeysIterator();
		while ( it.hasNext()) {
			TypeTreeNode child = (TypeTreeNode)getChild( it.next() );
			if ( child.getLink() != null ) {
				child.getLink().setPriority( priority );
			}
			priority++;
		}
	}
    
}
