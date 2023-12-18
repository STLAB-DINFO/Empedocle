package it.unifi.ing.stlab.reflection.actions.wrappers;

import it.unifi.ing.stlab.commons.util.TimeFormat;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitUseFactory;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

public class TypeBean {

	private Type type;
	private EntityManager entityManager;
	private List<PhenomenonBean> phenomena;
	private List<UnitUseBean> units;
	private TypeTreeNode treeRoot;
	
	public TypeBean() { 
		super();
	}
	public TypeBean( EntityManager entityManager, Type type) {
		if ( type == null ) throw new IllegalArgumentException( "type is null" );
		
		this.entityManager = entityManager;
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	
	//
	// Textual
	//
	
	public boolean isTextual() {
		return type != null && ClassHelper.instanceOf( type, TextualType.class );
	}
	public TextualType getTextualType() {
		return isTextual() ? ClassHelper.cast( type, TextualType.class ) : null;
	}

	
	
	//
	// Enumerated
	//
	
	public boolean isEnumerated() {
		return type != null && ClassHelper.instanceOf( type, EnumeratedType.class );
	}
	public EnumeratedType getEnumeratedType() {
		return isEnumerated() ? ClassHelper.cast( type, EnumeratedType.class ) : null;
	}
	
	public List<PhenomenonBean> getPhenomena() {
		if ( phenomena == null ) {
			initPhenomena();
		}
		return phenomena;
	}
	private void initPhenomena() {
		phenomena = new ArrayList<PhenomenonBean>();

		if ( !isEnumerated() ) return;


		for( Phenomenon ph : getEnumeratedType().listPhenomena() ) {
			phenomena.add( new PhenomenonBean( ph ));
		}
		
		
		Collections.sort( phenomena, new Comparator<PhenomenonBean>() {
			
			@Override
			public int compare(PhenomenonBean ph1, PhenomenonBean ph2) {
				String name1 = ph1.getName() != null ? ph1.getName() : "";
				String name2 = ph2.getName() != null ? ph2.getName() : "";
				
				return name1.compareTo( name2 );
			}
		} );
	}
	
	public void addPhenomenon() {
		Phenomenon ph = PhenomenonFactory.createPhenomenon();
		PhenomenonBean phBean = new PhenomenonBean( ph );
		getEnumeratedType().addPhenomenon( ph );
		getPhenomena().add( phBean );
	}
	
	public void deletePhenomenon( PhenomenonBean phBean ) {
		getPhenomena().remove( phBean );
		
		getEnumeratedType().removePhenomenon( phBean.getPhenomenon() );
		entityManager.remove( phBean.getPhenomenon() );
	}
	
	public Boolean isPhenomenonUsed( PhenomenonBean phBean ) {
		if(phBean.getPhenomenon().getId() != null) {
			return !entityManager.createQuery("select f.id from FactImpl f where f.phenomenon = :ph")
				.setMaxResults(1)
				.setParameter("ph", phBean.getPhenomenon())
				.getResultList().isEmpty();
		}
		else {
			return false;
		}
	}
	
	public List<SelectItem> getPhenomenaItems() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for(PhenomenonBean phb : getPhenomena()) {
			if(phb.getPhenomenon().getName() != null && !phb.getPhenomenon().getName().isEmpty()) {
				items.add(new SelectItem(phb.getPhenomenon(), phb.getPhenomenon().getName()));
			}
		}
		
		return items;

	}

	
	//
	// Queried
	//
	
	public boolean isQueried() {
		return type != null && ClassHelper.instanceOf( type, QueriedType.class );
	}
	public QueriedType getQueriedType() {
		return isQueried() ? ClassHelper.cast( type, QueriedType.class ) : null;
	}
	
	
	//
	// Quantitative
	//
	
	public boolean isQuantitative() {
		return type != null && ClassHelper.instanceOf( type, QuantitativeType.class );
	}
	public QuantitativeType getQuantitativeType() {
		return isQuantitative() ? ClassHelper.cast( type, QuantitativeType.class ) : null;
	}
	
	public List<UnitUseBean> getUnits() {
		if ( units == null ) {
			initUnits();
		}
		return units;
	}
	private void initUnits() {
		units = new ArrayList<UnitUseBean>();

		if ( !isQuantitative() ) return;
		
		for ( UnitUse uu : getQuantitativeType().listUnits() ) {
			units.add( new UnitUseBean( entityManager, uu ));
		}
		
		Collections.sort( units, new Comparator<UnitUseBean>() {
			@Override
			public int compare(UnitUseBean uu1, UnitUseBean uu2) {
				String unit1 = unitName( uu1 );
				String unit2 = unitName( uu2 );

				return unit1.compareTo( unit2 );
			}
			
			private String unitName( UnitUseBean uu ) {
				if ( uu == null ||
					 uu.getUnitUse() == null ||
					 uu.getUnitUse().getUnit() == null ||
					 uu.getUnitUse().getUnit().getName() == null ) return "";
				
				return uu.getUnitUse().getUnit().getName();
			}
			
		} );
		
	}
	
	public void addUnit() {
		UnitUse uu = UnitUseFactory.createUnitUse();
		getQuantitativeType().addUnit( uu );
		getUnits().add( new UnitUseBean( entityManager, uu ));
	}
	
	public void deleteUnit( UnitUseBean uuBean ) {
		getUnits().remove( uuBean );
		
		getQuantitativeType().removeUnit( uuBean.getUnitUse() );
		
		entityManager.remove( uuBean.getUnitUse() );
	}
	
	public Boolean isUnitUsed( UnitUseBean uuBean, Type currentType ) {
		if(uuBean.getUnitUse() != null && uuBean.getUnit() != null
				&& uuBean.getUnit().getId() != null) {
			return !entityManager.createQuery("select f.id from FactImpl f where f.quantity.unit = :u and f.type = :type")
					.setMaxResults(1)
					.setParameter("u", uuBean.getUnit())
					.setParameter("type", currentType)
					.getResultList()
					.isEmpty();
		}
		else {
			return false;
		}
	}
	

	public List<SelectItem> getUnitItems() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for(UnitUseBean uub : getUnits()) {
			if(uub.getUnit() != null && uub.getUnit().getName() != null && !uub.getUnit().getName().isEmpty()) {
				items.add(new SelectItem(uub.getUnit(), uub.getUnit().getName()));
			}
		}
		
		return items;

	}
	
	//
	// Temporal
	//
	
	public Boolean isTemporal() {
		return type != null && ClassHelper.instanceOf( type, TemporalType.class );
	}
	
	public TemporalType getTemporalType() {
		return isTemporal() ? ClassHelper.cast( type, TemporalType.class ) : null;
	}
	
	public SelectItem[] getTimeFormats() {
		SelectItem[] items = new SelectItem[TimeFormat.values().length];
		int i = 0;
		
		for(TimeFormat t : TimeFormat.values()) {
			StringBuilder sb = new StringBuilder();
			sb.append(t.getName()).append(" - ").append(t.getDefaultFormat());
			items[i] = new SelectItem(t, sb.toString());
			i++;
		}
		
		return items;
		
	}
	

	//
	// Composite
	//
	
	public boolean isComposite() {
		return type != null && ClassHelper.instanceOf( type, CompositeType.class );
	}
	public CompositeType getCompositeType() {
		return isComposite() ? ClassHelper.cast( type, CompositeType.class ) : null;
	}
	
	public TypeTreeNode getTreeRoot() {
		if ( treeRoot == null ) {
			initTreeRoot();
		}
		return treeRoot;
	}
	private void initTreeRoot() {
		if ( !isComposite() ) return;
		
		TypeTreeNode root = new TypeTreeNode( false, null );
		root.addChild("root", createTypeTreeNode( null, getType() ));
		
		treeRoot = root;
	}
	
	private TypeTreeNode createTypeTreeNode( TypeLink link, Type type ) {
		boolean leaf = !ClassHelper.instanceOf( type, CompositeType.class ) ||
						( link != null && ClassHelper.instanceOf( type, CompositeType.class ) && !type.getAnonymous() );
		
		TypeTreeNode result = new TypeTreeNode( leaf, link );

		if ( !leaf ) {
			for ( TypeLink child : getOrderedTypeLinks( type )) {
				result.addChild( child.getUuid(), createTypeTreeNode( child, child.getTarget() ));
			}
		}
		return result;
	}
	
	private List<TypeLink> getOrderedTypeLinks( Type type ) {
		List<TypeLink> result = new ArrayList<TypeLink>();
		result.addAll( type.listChildren() );
		
		Collections.sort( result, new Comparator<TypeLink>() {
			@Override
			public int compare(TypeLink l1, TypeLink l2) {
				Long p1 = l1.getPriority() != null ? l1.getPriority() : new Long( 0 );
				Long p2 = l2.getPriority() != null ? l2.getPriority() : new Long( 0 );
				
				int res = p1.compareTo( p2 );
				
				if ( res == 0 ) {
					String name1 = l1.getName() != null ? l1.getName() : "";
					String name2 = l2.getName() != null ? l2.getName() : "";
					
					res = name1.compareTo( name2 );
				}
				return res;
			}
		} );
		
		return result;
	}
	
	//
	// Reference
	//
	
	public boolean isReference() {
		return type == null || !type.getAnonymous();
	}
}
