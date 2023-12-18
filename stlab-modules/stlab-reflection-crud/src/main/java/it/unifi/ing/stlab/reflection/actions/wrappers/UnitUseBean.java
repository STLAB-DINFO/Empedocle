package it.unifi.ing.stlab.reflection.actions.wrappers;

import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;

import java.util.List;

import javax.persistence.EntityManager;

public class UnitUseBean {

	private EntityManager entityManager;
	private UnitUse unitUse;
	
	
	public UnitUseBean() {
		super();
	}
	public UnitUseBean(EntityManager entityManager, UnitUse unitUse) {
		if ( unitUse == null ) throw new IllegalArgumentException( "unitUse is null" );
		
		this.entityManager = entityManager;
		this.unitUse = unitUse;
	}
	
	public UnitUse getUnitUse() {
		return unitUse;
	}
	
	public Unit getUnit() {
		return unitUse.getUnit();
	}
	
	public String getUnitUuid() {
		if ( unitUse.getUnit() == null ) return null;
		
		return unitUse.getUnit().getUuid();
	}
	public void setUnitUuid(String unitUuid) {
		if ( unitUuid == null ) {
			unitUse.setUnit( null );
		} else {
			if ( unitUse.getUnit() == null || !unitUse.getUnit().getUuid().equals( unitUuid )) {
				// FIXME spostare in un dao
				@SuppressWarnings("unchecked")
				List<Unit> resultList = entityManager.createQuery( "select u from Unit u where u.uuid = :param" ).setParameter( "param", unitUuid ).getResultList();
				if ( resultList.size() == 1 ) {
					unitUse.setUnit( resultList.get( 0 ));
				} else {
					unitUse.setUnit( null );
				}
			}
			
		}
	}
	public Integer getDigits() {
		return unitUse.getDigits();
	}
	public void setDigits(Integer digits) {
		unitUse.setDigits(digits);
	}
	public Integer getDecimals() {
		return unitUse.getDecimals();
	}
	public void setDecimals(Integer decimals) {
		unitUse.setDecimals(decimals);
	}
	
	
	
}
