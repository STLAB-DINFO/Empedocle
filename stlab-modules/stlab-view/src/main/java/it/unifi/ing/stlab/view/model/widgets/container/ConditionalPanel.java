package it.unifi.ing.stlab.view.model.widgets.container;

import it.unifi.ing.stlab.commons.util.Operator;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerContainer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("CONDITIONAL")
public class ConditionalPanel extends ViewerContainer {
	
	private List<TypeSelector> selectors;
	private List<Phenomenon> phenomena;
	private List<Operator> operators;
	
	private Boolean clear;
	/**
	 * @deprecated replaced by clearSelectors; only for backward compatibility
	 */
	@Deprecated
	private TypeSelector clearSelector;
	private List<TypeSelector> clearSelectors;
	
	public ConditionalPanel(String uuid) {
		super(uuid);
		selectors = new ArrayList<TypeSelector>();
		phenomena = new ArrayList<Phenomenon>();
		operators =	new ArrayList<Operator>();
		clearSelectors = new ArrayList<TypeSelector>();
	}
	protected ConditionalPanel() {
		super();
	}
	
	@OneToMany( cascade = {CascadeType.PERSIST, CascadeType.REMOVE} )
	@JoinTable
	(	name="conditional_selectors", 
		joinColumns={@JoinColumn(name="conditional_id", referencedColumnName="id")},
		inverseJoinColumns={@JoinColumn(name="selector_id", referencedColumnName="id")})
	public List<TypeSelector> getSelectors() {
		return selectors;
	}
	public void setSelectors(List<TypeSelector> selectors) {
		this.selectors = selectors;
	}
	public void addSelector(TypeSelector selector){
		if(selector == null)
			return;
		
		selectors.add(selector);
	}
	
	@OneToMany( cascade = {CascadeType.PERSIST, CascadeType.REMOVE} )
	@JoinTable
	(	name="conditional_clear_selectors", 
		joinColumns={@JoinColumn(name="conditional_id", referencedColumnName="id")},
		inverseJoinColumns={@JoinColumn(name="selector_id", referencedColumnName="id")})
	public List<TypeSelector> getClearSelectors() {
		return clearSelectors;
	}
	public void setClearSelectors(List<TypeSelector> selectors) {
		this.clearSelectors = selectors;
	}
	public void addClearSelector(TypeSelector selector){
		if(selector == null)
			return;
		
		clearSelectors.add(selector);
	}
	
	@ManyToMany
	@JoinTable
	(	name="conditional_phenomena", 
		joinColumns={@JoinColumn(name="conditional_id", referencedColumnName="id")},
		inverseJoinColumns={@JoinColumn(name="phenomenon_id", referencedColumnName="id")})
	public List<Phenomenon> getPhenomena() {
		return phenomena;
	}
	public void setPhenomena(List<Phenomenon> phenomena) {
		this.phenomena = phenomena;
	}
	public void addFenomeno(Phenomenon fenomeno){
		if(fenomeno == null)
			return;
		
		phenomena.add(fenomeno);
	}
	
	@ElementCollection(targetClass=Operator.class, fetch=FetchType.EAGER)
	@CollectionTable(
	  name="conditional_operators",
	  joinColumns=@JoinColumn(name="id")
	)
	public List<Operator> getOperators() {
		return operators;
	}
	public void setOperators(List<Operator> operatori) {
		this.operators = operatori;
	}
	public void addOperator(String operatore){
		if(operatore.equals("and"))
			operators.add(Operator.and);
		if(operatore.equals("or"))
			operators.add(Operator.or);
	}
	
	@Column( name = "clear" )
	public Boolean isClear() {
		return clear;
	}
	public void setClear(Boolean clear) {
		this.clear = clear;
	}
	
	/**
	 * @deprecated replaced by clearSelectors; only for backward compatibility
	 */
	@Deprecated
	@ManyToOne( fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE} )
	@JoinColumn( name="clear_selector" )
	public TypeSelector getClearSelector() {
		return clearSelector;
	}
	/**
	 * @deprecated replaced by clearSelectors; only for backward compatibility
	 */	
	@Deprecated
	public void setClearSelector(TypeSelector clearSelector) {
		this.clearSelector = clearSelector;
	}
	
	
	@Transient
	public Viewer getTarget() {
		if(listChildren().isEmpty())
			return null;
		
		return getByPriority(0l).getTarget();
	}
	
	@Transient
	public TypeSelector getSelector() {
		if(listChildren().isEmpty())
			return null;
		
		return getByPriority(0l).getSelector();
	}
	
	@Transient
	@Override
	public String getXhtml() {
		return "../component/conditionalPanel.xhtml";
		
	}
	
	@Override
	public boolean isValidSubViewer(ViewerLink sv) {
//		if(v == null)
//			return;
//		if ( listChildren().contains( v )) 
//			return;
		if( sv == null || !ClassHelper.instanceOf( sv, SubViewer.class ))
			return false;
		
		if(listChildren().size() > 0)
			throw new RuntimeException("It is not possible to add more than one subviewer to one conditionalPanel");
		
		return true;
	}
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitConditionalPanel(this);
	}
	
}
