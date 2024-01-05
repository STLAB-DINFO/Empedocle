package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.visitor.fact.AssignDefaultValueVisitor;
import it.unifi.ing.stlab.reflection.visitor.fact.FactDefaultInitializerVisitor;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.widgets.container.ConditionalPanel;
import it.unifi.ing.stlab.view.model.widgets.input.Combo;
import org.apache.commons.jexl2.*;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ConversationScoped
public class ConditionalPanelController extends ContainerController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private JexlEngine jexlEngine;
	private final Map<ConditionalPanelMapKey, Boolean> panelMap;
	private final Set<Type> register;

	public ConditionalPanelController() {
		panelMap = new HashMap<ConditionalPanelMapKey, Boolean>();
		register = new HashSet<Type>();
		
	}
	
	public boolean checkRendered(ConditionalPanel cp, Fact fact){
		if( cp == null || fact == null ) return false;
		
		initMaps( cp, fact );
		ConditionalPanelMapKey key = key( cp, fact );
		
		if( panelMap.get( key ) ==  null ){
			evaluateSingleComponent( cp, fact );
			evaluateNullValues();
		}
		
		Boolean result = panelMap.get( key );
		return result != null ? result : false;
		
	}
	
	public String render(Type t, Viewer v){
		if( register.contains(t) ) {
			refreshMap( t );
			return "@form";
		} else {
			if( v instanceof Combo )
				return "@none";
			else // i.e. InputList
				return "@form";  
		}
	}
	
	private void initMaps(ConditionalPanel cp, Fact fact) {
		ConditionalPanelMapKey key = key( cp, fact );
		
		if( !panelMap.containsKey( key ) ) {
			for(TypeSelector ts : cp.getSelectors()){
				Type t = ts.getLast().getTypeLink().getTarget();
				register.add( t );
			}
			
			panelMap.put( key, null );
		}
	}
	
	private Boolean evaluateExpression(ConditionalPanel cp, Fact fact){
		StringBuffer sb = new StringBuffer("res0");
		sb.append(" ");
		for(int i=0; i < cp.getOperators().size(); i++){
			sb.append(cp.getOperators().get(i));
			sb.append(" ");
			sb.append("res");
			sb.append(i+1);
			sb.append(" ");
		}
		
		if(jexlEngine == null){
			jexlEngine = new JexlEngine();
			jexlEngine.setSilent(false);
			jexlEngine.setStrict(true);
		}
		
		Expression e = jexlEngine.createExpression(sb.toString());

		JexlContext context = new MapContext();
		
		for ( int j = 0; j < cp.getSelectors().size(); j++ ) {
			Set<FactLink> links = findLinksBySelector( fact, cp.getSelectors().get( j ) );
			if( links == null )
				context.set( "res" + j, false );
			else {
				boolean found = false;
				for( FactLink fl : links ) {
					QualitativeFact qf = ClassHelper.cast( fl.getTarget(), QualitativeFact.class );
					
					if( cp.getPhenomena().get( j ).equals( qf.getPhenomenon() ) ) {
						found = true;
						break;
					}
				}
				context.set( "res" + j, found );
			}
		}
		
		try{
			return (Boolean)e.evaluate(context);
		} catch(JexlException je){
			throw new IllegalArgumentException(je.getMessage());
		}
		
	}

	private void refreshMap(Type t){
		for( ConditionalPanelMapKey ck : panelMap.keySet() ){
			for( TypeSelector ts : ck.getPanel().getSelectors() ){
				if( ts.getLast().getTypeLink().getTarget().equals(t) ){
					panelMap.put(ck, null);
					break;
				}
			}
		}
	}
	
	private void evaluateNullValues() {
		for( ConditionalPanelMapKey ck : panelMap.keySet() ) {
			if( panelMap.get( ck ) == null ) {
				evaluateSingleComponent( ck.getPanel(), ck.getFact() );
			}
				
		}
	}
	
	private void evaluateSingleComponent(ConditionalPanel cp, Fact fact) {
		Boolean result = evaluateExpression( cp, fact );
		panelMap.put( key( cp, fact ), result );
		
		if( cp.isClear() && !result ) {
			if( cp.getClearSelector() != null ) {
				TypeSelector clearSelector = cp.getClearSelector();
				Set<FactLink> factLinks = clearSelector.applyItem( fact );
				if ( !factLinks.isEmpty() && clearSelector.getTypeLink() != null ) {
					for( FactLink fl : factLinks ) {
						fl.getTarget().accept( new AssignDefaultValueVisitor( clearSelector.getTypeLink().getDefaultValue() ) );
						fl.getTarget().accept( new FactDefaultInitializerVisitor() );
					}
				}
			} else {
				List<TypeSelector> clearSelectors = cp.getClearSelectors();
				for( TypeSelector clearSelector : clearSelectors ) {
					Set<FactLink> factLinks = clearSelector.applyItem( fact );
					if ( !factLinks.isEmpty() && clearSelector.getTypeLink() != null ) {
						for( FactLink fl : factLinks ) {
							fl.getTarget().accept( new AssignDefaultValueVisitor( clearSelector.getTypeLink().getDefaultValue() ) );
							fl.getTarget().accept( new FactDefaultInitializerVisitor() );
						}
					}
				}
			}
		}
	}
	
	private ConditionalPanelMapKey key(ConditionalPanel cp, Fact fact){
		return new ConditionalPanelMapKey( cp, fact );
	}
	
	
	class ConditionalPanelMapKey {
		private final ConditionalPanel panel;
		private final Fact fact;
		
		ConditionalPanelMapKey(ConditionalPanel panel, Fact fact) {
			this.panel = panel;
			this.fact = fact;
		}
		
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + fact.hashCode();
			result = prime * result + panel.hashCode();
			
			return result;
		}
		
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (!( obj instanceof ConditionalPanelMapKey )) return false;
			if ( panel == null || fact == null ) return false;
			
			ConditionalPanelMapKey other = (ConditionalPanelMapKey) obj;
			return panel.equals( other.getPanel() ) && fact.equals( other.getFact() );
		}

		public ConditionalPanel getPanel() {
			return panel;
		}
		public Fact getFact() {
			return fact;
		}
		
	}
	
	public Set<FactLink> findLinksBySelector(Fact fact, TypeSelector selector) {
		if ( selector == null ) return null;
		
		return selector.applyItem( fact );
	}
}
