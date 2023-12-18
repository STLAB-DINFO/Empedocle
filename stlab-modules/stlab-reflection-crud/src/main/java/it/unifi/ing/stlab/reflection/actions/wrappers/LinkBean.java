package it.unifi.ing.stlab.reflection.actions.wrappers;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.factory.values.FactValueFactory;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.QualitativeFactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.QuantitativeFactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.TemporalFactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.TextualFactValue;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import javax.persistence.EntityManager;

public class LinkBean {
	
	private TypeLink link;
	private FactValue defaultValue;
	private EntityManager entityManager;
	
	public LinkBean(TypeLink link, EntityManager em) {
		this.link = link;
		this.entityManager = em;
	}
	
	public void clearDefaultValue() {
		defaultValue = null;
		if(link.getDefaultValue() != null) {
			entityManager.remove(link.getDefaultValue());
			link.setDefaultValue(null);
		}
	}
	
	// TODO test
	public void syncDefaultValue() {
		if(link == null)
			return;
		
		if (defaultValue == null || defaultValue.isEmpty() ){
			clearDefaultValue();
			return;
		}

		if (isTargetEnumerated()) {
			QualitativeFactValue defValue = ClassHelper.cast(link.getDefaultValue(), QualitativeFactValue.class);
			if(defValue == null){
				defValue = FactValueFactory.createQualitativeValue();
				link.setDefaultValue(defValue);
			}
			defValue.setPhenomenon(
					ClassHelper.cast(defaultValue, QualitativeFactValue.class).getPhenomenon()
					);
		}

		if (isTargetQuantitative()) {
			QuantitativeFactValue defValue = ClassHelper.cast(link.getDefaultValue(), QuantitativeFactValue.class);
			if(defValue == null){
				defValue = FactValueFactory.createQuantitativeValue();
				link.setDefaultValue(defValue);
			}
			defValue.setQuantity(ClassHelper.cast(defaultValue, QuantitativeFactValue.class).getQuantity());
		}

		if (isTargetTextual()) {
			TextualFactValue defValue = ClassHelper.cast(link.getDefaultValue(), TextualFactValue.class);
			if(defValue == null){
				defValue = FactValueFactory.createTextualValue();
				link.setDefaultValue(defValue);
			}
			defValue.setText(ClassHelper.cast(defaultValue, TextualFactValue.class).getText());
		}	
		
		if (isTargetTemporal()) {
			TemporalFactValue defValue = ClassHelper.cast(link.getDefaultValue(), TemporalFactValue.class);
			if(defValue == null){
				defValue = FactValueFactory.createTemporalValue();
				link.setDefaultValue(defValue);
			}
			
			defValue.setUseToday( ClassHelper.cast(defaultValue, TemporalFactValue.class).getUseToday() );
			if( defValue.getUseToday() )
				defValue.setDate( null );
			else
				defValue.setDate(ClassHelper.cast(defaultValue, TemporalFactValue.class).getDate());
		}	
		
	}
	
//	public void syncDefaultValue() {
//		if (link != null) {
//			if (defaultValue != null && !defaultValue.isEmpty() ) {
//				
//				if (link.getDefaultValue() != null) {
//					if (isTargetEnumerated()) {
//						ClassHelper.cast(link.getDefaultValue(), QualitativeFactValue.class)
//							.setPhenomenon(
//								ClassHelper.cast(defaultValue, QualitativeFactValue.class).getPhenomenon()
//								);
//					}
//		
//					if (isTargetQuantitative()) {
//						ClassHelper.cast(link.getDefaultValue(), QuantitativeFactValue.class)
//						.setQuantity(ClassHelper.cast(defaultValue, QuantitativeFactValue.class).getQuantity());
//					}
//					
//				}
//				else {
//					link.setDefaultValue(defaultValue);
//					
//				}
//			}
//			else {
//				link.setDefaultValue(null)
//			}
//
//		}
//	}
	
	public boolean isTargetTextual() {
		return link.getTarget() != null && ClassHelper.instanceOf( link.getTarget(), TextualType.class );
	}
	
	public boolean isTargetEnumerated() {
		return link.getTarget() != null && ClassHelper.instanceOf( link.getTarget(), EnumeratedType.class );
	}
	
	public boolean isTargetQuantitative() {
		return link.getTarget() != null && ClassHelper.instanceOf( link.getTarget(), QuantitativeType.class );
	}
	
	public boolean isTargetTemporal() {
		return link.getTarget() != null && ClassHelper.instanceOf( link.getTarget(), TemporalType.class );
	}
	
	private FactValue createDefaultValue(Type type) {
		if (ClassHelper.instanceOf(type, EnumeratedType.class)) {
			return FactValueFactory.createQualitativeValue();
			
		}
		else if (ClassHelper.instanceOf(type, QuantitativeType.class)) {
			QuantitativeFactValue fv = FactValueFactory.createQuantitativeValue();
			fv.setQuantity(new Quantity());
			return fv;
			
		}
		else if (ClassHelper.instanceOf(type, TextualType.class)) {
			return FactValueFactory.createTextualValue();
			
		}
		else if (ClassHelper.instanceOf(type, TemporalType.class)) {
			return FactValueFactory.createTemporalValue();
			
		}
		else {
			return null;
			
		}
	}

	public TypeLink getLink() {
		return link;
	}

	public void setLink(TypeLink link) {
		this.link = link;
	}

	public FactValue getDefaultValue() {
		if(defaultValue == null) {
			initDefaultValue();
		}
		return defaultValue;
	}
	private void initDefaultValue() {
		if(link != null) {
			if(link.getDefaultValue() != null) {
				defaultValue = fetchDefaultValue(link);
				
				// FIX per il "non bug" di Hibernate con le Embeddable...
				if (isTargetQuantitative()) {
					if (((QuantitativeFactValue)defaultValue).getQuantity() == null) {
						((QuantitativeFactValue)defaultValue).setQuantity(new Quantity());
					}
				}
				
			}
			else {
				defaultValue = createDefaultValue(link.getTarget());
				
			}
		}
		
	}
	
	private FactValue fetchDefaultValue(TypeLink link) {
		return (FactValue)ClassHelper.unproxy(link.getDefaultValue());
	}

	public void setDefaultValue(FactValue defaultValue) {
		this.defaultValue = defaultValue;
	}

}
