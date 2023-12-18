package it.unifi.ing.stlab.view.actions.wrappers;

import it.unifi.ing.stlab.view.actions.visitor.IsViewerLeafVisitor;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.ViewerLink;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewerBean {

	private Viewer viewer;
	private ViewerTreeNode treeRoot;
	
	public ViewerBean() {
		super();
	}
	public ViewerBean( Viewer viewer) {
		if ( viewer == null ) throw new IllegalArgumentException( "type is null" );
		
		this.viewer = viewer;
	}

	public Viewer getViewer() {
		return viewer;
	}
	
	public ViewerTreeNode getTreeRoot() {
		if ( treeRoot == null ) {
			initTreeRoot();
		}
		return treeRoot;
	}
	private void initTreeRoot() {
		ViewerTreeNode root = new ViewerTreeNode( false, null );
		root.addChild("root", createViewerTreeNode( null, getViewer() ));
		
		treeRoot = root;
	}
	
	private ViewerTreeNode createViewerTreeNode( ViewerLink link, Viewer viewer ) {
		IsViewerLeafVisitor v = new IsViewerLeafVisitor();
		viewer.accept(v);
		
		Boolean leaf = v.isLeaf();
		
		ViewerTreeNode result = new ViewerTreeNode( leaf, link );

		if ( !leaf ) {
			for ( ViewerLink child : getOrderedViewerLinks( viewer )) {
				result.addChild( child.getUuid(), createViewerTreeNode( child, child.getTarget() ));
			}
		}
		return result;
	}
	
	private List<ViewerLink> getOrderedViewerLinks( Viewer viewer ) {
		List<ViewerLink> result = new ArrayList<ViewerLink>();
		result.addAll( viewer.listChildren() );
		
		Collections.sort( result, new Comparator<ViewerLink>() {
			@Override
			public int compare(ViewerLink l1, ViewerLink l2) {
				Long p1 = l1.getPriority() != null ? l1.getPriority() : new Long( 0 );
				Long p2 = l2.getPriority() != null ? l2.getPriority() : new Long( 0 );
				
				int res = p1.compareTo( p2 );
				
				if ( res == 0 ) {
					String name1 = l1.getUuid() != null ? l1.getUuid() : "";
					String name2 = l2.getUuid() != null ? l2.getUuid() : "";
					
					res = name1.compareTo( name2 );
				}
				return res;
			}
		} );
		
		return result;
	}

	public boolean isReference() {
		return viewer == null || !viewer.getAnonymous();
	}
}
