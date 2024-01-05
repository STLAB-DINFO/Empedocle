package it.unifi.ing.stlab.empedocle.view.controllers.cardiology;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.view.controllers.ContainerController;
import it.unifi.ing.stlab.view.model.Viewer;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.math.BigDecimal;

@Named
@RequestScoped
public class AltezzaPesoController extends ContainerController {

	private QuantitativeFact altezza;
	private QuantitativeFact peso;
	private QuantitativeFact sc;
	private QuantitativeFact imc;

	
	public void altezzaUnitChanged(ValueChangeEvent vce){
		Unit selectedUnit = (Unit)vce.getNewValue();
		Quantity q = altezza.getQuantity().as(selectedUnit);
		if(q != null)
			altezza.setQuantity(q);
	}
	
	public QuantitativeFact retrieveAltezza(Fact value, Viewer viewer){
			Fact f = findBySelector(value, viewer.getByPriority(0l).getSelector());
			altezza = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
			updateAltezzaQuantity();
		
		return altezza;
		
	}
	
	public QuantitativeFact retrievePeso(Fact value, Viewer viewer){
			Fact f = findBySelector(value, viewer.getByPriority(1l).getSelector());
			peso = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
		return peso;
		
	}
	
	public QuantitativeFact retrieveSC(Fact value, Viewer viewer){
			Fact f = findBySelector(value, viewer.getByPriority(2l).getSelector());
			sc = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
			updateSc();
		return sc;
	}
	
	public QuantitativeFact retrieveIMC(Fact value, Viewer viewer){
			Fact f = findBySelector(value, viewer.getByPriority(3l).getSelector());
			imc = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
			updateImc();
		return imc;
	}
	
	
	//
	// Getters Methods
	//
	public QuantitativeFact getAltezza() {
		return altezza;
	}
	public QuantitativeFact getPeso() {
		return peso;
	}
	public QuantitativeFact getSc() {
		return sc;
	}
	public QuantitativeFact getImc() {
		return imc;
	}
	
	
	//
	// Private Methods
	//
	private void updateAltezzaQuantity(){
		if( altezza != null && altezza.getQuantity() == null ) {
			altezza.setQuantity( new Quantity() );
		}
	}
	
	private void updateSc(){
		if(sc == null)
			return;
		
		if( checkEmptyField( altezza ) ) {
			sc.getQuantity().setValue(null);
			return;
		}
		
		if( checkEmptyField( peso ) ) {
			sc.getQuantity().setValue(null);
			return;
		}
		
		//FIXME utilizzare il convertitore per ottenere la quantità in un udm fissa e arrotondare (anche per imc)
		
		int scale = 1;
		if(altezza.getQuantity().getUnit().getSimbol().equals("cm"))
			scale = 100;

		sc.getQuantity().setValue(round(2, Math.sqrt(altezza.getQuantity().getValue() * peso.getQuantity().getValue()/ (36*scale) )));
			
	}
	
	
	private void updateImc(){
		if(imc == null)
			return;
		
		if( checkEmptyField( altezza ) ) {
			imc.getQuantity().setValue(null);
			return;
		}
		
		if( checkEmptyField( peso ) ) {
			imc.getQuantity().setValue(null);
			return;
		}
		
		int scale = 1;
		if(altezza.getQuantity().getUnit().getSimbol().equals("cm"))
			scale = 10000;
		
		imc.getQuantity().setValue(round(2, peso.getQuantity().getValue() / Math.pow(altezza.getQuantity().getValue(), 2) * scale));
	}
	
	
	private Double round(int decimalPlace, Double x) {
		BigDecimal bd = new BigDecimal(x);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);

		return bd.doubleValue();
	}
	
	
	private boolean checkEmptyField(QuantitativeFact fact) {
		return fact == null ||
				fact.getQuantity().getValue() == null ||
				fact.getQuantity().getValue() <= 0;
	}
	
}
