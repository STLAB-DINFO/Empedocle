package it.unifi.ing.stlab.empedocle.view.controllers.cardiology;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.view.controllers.ContainerController;
import it.unifi.ing.stlab.view.model.Viewer;

import java.math.BigDecimal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class RepertiMonoBidimensionaliController extends ContainerController {

	private QuantitativeFact diametroTelediastolico;
	private QuantitativeFact diametroTelediastolicoIndicizzato;
	private QuantitativeFact diametroTelesistolico;
	private QuantitativeFact diametroTelesistolicoIndicizzato;
	private QuantitativeFact accorciamentoFrazionale;
	private QuantitativeFact volumeTelediastolico;
	private QuantitativeFact volumeTelediastolicoIndicizzato;
	private QuantitativeFact volumeTelesistolico;
	private QuantitativeFact volumeTelesistolicoIndicizzato;
	private QuantitativeFact frazioneEiezione;
	private QuantitativeFact settoInterventricolare;
	private QuantitativeFact paretePosteriore;
	private QuantitativeFact massa;
	private QuantitativeFact massaIndicizzata;
	private QuantitativeFact volume;
	private QuantitativeFact volumeIndicizzato;
	
	private Double superficieCorporea;
	private Double sommaDiametri;
	private Double elevDiametro;
	private Double elevVentricolo;
	
	public QuantitativeFact retrieveDiametroTelediastolico(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 0 );
		diametroTelediastolico = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
			
		return diametroTelediastolico;
	}
	
	public QuantitativeFact retrieveDiametroTelediastolicoIndicizzato(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 1 );
		diametroTelediastolicoIndicizzato = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
		retrieveSuperficieCorporea(value, viewer);
		updateDiametroTelediastolicoIndicizzato();

		return diametroTelediastolicoIndicizzato;
	}
	
	public QuantitativeFact retrieveDiametroTelesistolico(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 2 );
		diametroTelesistolico = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));

		return diametroTelesistolico;
	}

	public QuantitativeFact retrieveDiametroTelesistolicoIndicizzato(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 3 );
		diametroTelesistolicoIndicizzato = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
		retrieveSuperficieCorporea(value, viewer);
		updateDiametroTelesistolicoIndicizzato();
		
		return diametroTelesistolicoIndicizzato;
	}
	
	public QuantitativeFact retrieveAccorciamentoFrazionale(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 4 );
		accorciamentoFrazionale = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
		updateAccorciamentoFrazionale();
		
		return accorciamentoFrazionale;
	}
	
	public QuantitativeFact retrieveVolumeTelediastolico(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 5 );
		volumeTelediastolico = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));

		return volumeTelediastolico;
	}
	
	public QuantitativeFact retrieveVolumeTelediastolicoIndicizzato(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 6 );
		volumeTelediastolicoIndicizzato = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
		retrieveSuperficieCorporea(value, viewer);
		updateVolumeTelediastolicoIndicizzato();
		
		return volumeTelediastolicoIndicizzato;
	}
	
	public QuantitativeFact retrieveVolumeTelesistolico(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 7 );
		volumeTelesistolico = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
		
		return volumeTelesistolico;
	}
	
	public QuantitativeFact retrieveVolumeTelesistolicoIndicizzato(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 8 );
		volumeTelesistolicoIndicizzato = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
		retrieveSuperficieCorporea(value, viewer);
		updateVolumeTelesistolicoIndicizzato();
		
		return volumeTelesistolicoIndicizzato;
	}
	
	public QuantitativeFact retrieveFrazioneEiezione(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 9 );
		frazioneEiezione = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
		updateFrazioneEiezione();
			
		return frazioneEiezione;
	}

	public QuantitativeFact retrieveSettoInterventricolare(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 10 );
		settoInterventricolare = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));

		return settoInterventricolare;
	}
	
	public QuantitativeFact retrieveParetePosteriore(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 11 );
		paretePosteriore = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));

		return paretePosteriore;
	}
	
	public QuantitativeFact retrieveMassa(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 12 );
		massa = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
		updateMassa();
		
		return massa;
	}
	
	public QuantitativeFact retrieveMassaIndicizzata(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 13 );
		massaIndicizzata = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
		retrieveSuperficieCorporea(value, viewer);
		updateMassaIndicizzata();

		return massaIndicizzata;
	}
	
	public QuantitativeFact retrieveVolume(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 14 );
		volume = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));

		return volume;
	}
	
	public QuantitativeFact retrieveVolumeIndicizzato(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 15 );
		volumeIndicizzato = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
		retrieveSuperficieCorporea(value, viewer);
		updateVolumeIndicizzato();

		return volumeIndicizzato;
	}

	public void retrieveSuperficieCorporea(Fact value, Viewer viewer) {
		Fact f = retrieveFact( value, viewer, 22 );
		superficieCorporea = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class).getQuantity().getValue());
	}
	
	//
	// Update Methods
	//
	
	private void updateDiametroTelediastolicoIndicizzato(){
		if(diametroTelediastolicoIndicizzato == null)
			return;

		if(diametroTelediastolico == null || diametroTelediastolico.getQuantity().getValue() == null
				|| superficieCorporea == null ) {
			
			diametroTelediastolicoIndicizzato.getQuantity().setValue( null );
			return;
		}
		
		Double dti = diametroTelediastolico.getQuantity().getValue()/superficieCorporea;
		diametroTelediastolicoIndicizzato.getQuantity().setValue(round(0, dti));
	}
	
	private void updateDiametroTelesistolicoIndicizzato(){
		if(diametroTelesistolicoIndicizzato == null)
			return;
		
		if(diametroTelesistolico == null || diametroTelesistolico.getQuantity().getValue() == null
				|| superficieCorporea == null) {
			
			diametroTelesistolicoIndicizzato.getQuantity().setValue( null );
			return;
		}
		
		Double dti = diametroTelesistolico.getQuantity().getValue()/superficieCorporea;
		diametroTelesistolicoIndicizzato.getQuantity().setValue(round(0, dti));
	}
	
	private void updateAccorciamentoFrazionale(){
		if(accorciamentoFrazionale == null)
			return;
		
		if(diametroTelediastolico == null || diametroTelediastolico.getQuantity().getValue() == null || 
				diametroTelesistolico == null || diametroTelesistolico.getQuantity().getValue() == null) {
			
			accorciamentoFrazionale.getQuantity().setValue( null );
			return;
		}
		
		Double af = (diametroTelediastolico.getQuantity().getValue() - diametroTelesistolico.getQuantity().getValue())*100 / diametroTelediastolico.getQuantity().getValue();
		accorciamentoFrazionale.getQuantity().setValue(round(0, af));
	}
	
	private void updateVolumeTelediastolicoIndicizzato(){
		if(volumeTelediastolicoIndicizzato == null)
			return;
		
		if(volumeTelediastolico == null || volumeTelediastolico.getQuantity().getValue() == null
				|| superficieCorporea == null) {
			
			volumeTelediastolicoIndicizzato.getQuantity().setValue( null );
			return;
		}
		
		Double vti = volumeTelediastolico.getQuantity().getValue()/superficieCorporea;
		volumeTelediastolicoIndicizzato.getQuantity().setValue(round(0, vti));
	}

	private void updateVolumeTelesistolicoIndicizzato(){
		if(volumeTelesistolicoIndicizzato == null)
			return;
		
		if(volumeTelesistolico == null || volumeTelesistolico.getQuantity().getValue() == null
				|| superficieCorporea == null) {
			
			volumeTelesistolicoIndicizzato.getQuantity().setValue( null );
			return;
		}
		
		Double vti = volumeTelesistolico.getQuantity().getValue()/superficieCorporea;
		volumeTelesistolicoIndicizzato.getQuantity().setValue(round(0, vti));
	}

	private void updateFrazioneEiezione(){
		if(frazioneEiezione == null)
			return;
		
		if(volumeTelediastolicoIndicizzato == null || volumeTelediastolicoIndicizzato.getQuantity().getValue() == null || 
				volumeTelesistolicoIndicizzato == null || volumeTelesistolicoIndicizzato.getQuantity().getValue() == null || 
				volumeTelediastolicoIndicizzato.getQuantity().getValue() == null) {
			
			frazioneEiezione.getQuantity().setValue( null );
			return;
		}
		
		Double fe = (volumeTelediastolicoIndicizzato.getQuantity().getValue() - volumeTelesistolicoIndicizzato.getQuantity().getValue())*100
						/ volumeTelediastolicoIndicizzato.getQuantity().getValue();
		frazioneEiezione.getQuantity().setValue(round(0, fe));
	
	}
	
	private void updateSommaDiametri(){
		if(settoInterventricolare == null || settoInterventricolare.getQuantity().getValue() == null || 
				paretePosteriore == null || paretePosteriore.getQuantity().getValue() == null ||
				diametroTelediastolico == null || diametroTelediastolico.getQuantity().getValue() == null) {
			
			sommaDiametri = null;
			return;
		}
		
		sommaDiametri = (settoInterventricolare.getQuantity().getValue() / 10) +
						(paretePosteriore.getQuantity().getValue() / 10) +
						(diametroTelediastolico.getQuantity().getValue() / 10);
	}
	
	private void updateElevDiametri(){
		updateSommaDiametri();
		
		if(sommaDiametri == null) {
			
			elevDiametro = null;
			return;
		}
		
		elevDiametro = sommaDiametri*sommaDiametri*sommaDiametri;
	}
	
	private void updateElevVentricolo(){
		updateSommaDiametri();
		
		if(diametroTelediastolico == null || diametroTelediastolico.getQuantity().getValue() == null) {
			
			elevVentricolo = null;
			return;
		}
		
		elevVentricolo = (diametroTelediastolico.getQuantity().getValue() / 10)*
						(diametroTelediastolico.getQuantity().getValue() / 10)*
						(diametroTelediastolico.getQuantity().getValue() / 10);
		
	}
	
	private void updateMassa(){
		updateElevDiametri();
		updateElevVentricolo();
		
		if(massa == null)
			return;
		
		if(elevVentricolo == null || elevDiametro == null) {
			massa.getQuantity().setValue( null );
			return;
		}
			
		Double m = 0.8 * 1.04 * (elevDiametro - elevVentricolo) + 0.6;
		massa.getQuantity().setValue(round(0, m));
	}
	
	private void updateMassaIndicizzata(){
		if(massaIndicizzata == null)
			return;
		
		if(superficieCorporea == null || massa == null || massa.getQuantity().getValue() == null) {
			massaIndicizzata.getQuantity().setValue( null );
			return;
		}
		
		Double mi = massa.getQuantity().getValue()/superficieCorporea;
		massaIndicizzata.getQuantity().setValue(round(0, mi));
	}

	private void updateVolumeIndicizzato(){
		if(volumeIndicizzato == null)
			return;
		
		if(superficieCorporea == null || volume == null || volume.getQuantity().getValue() == null) {
			volumeIndicizzato.getQuantity().setValue( null );
			return;
		}
		
		Double vi = volume.getQuantity().getValue()/superficieCorporea;
		volumeIndicizzato.getQuantity().setValue(round(0, vi));
	}
	
	private Fact retrieveFact(Fact value, Viewer viewer, int viewerPriority){
		try {
			return findBySelector(value, viewer.getByPriority(new Long(viewerPriority)).getSelector());
			
		} catch(RuntimeException e){
			return null;
		}
	}
	
	private Double round(int decimalPlace, Double x) {
		if( x == null || x.isNaN() || x.isInfinite() ) return null;
		
		BigDecimal bd = new BigDecimal(x);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);

		return bd.doubleValue();
	}
	
	public QuantitativeFact getDiametroTelediastolico() {
		return diametroTelediastolico;
	}
	
	public QuantitativeFact getDiametroTelediastolicoIndicizzato() {
		return diametroTelediastolicoIndicizzato;
	}
	
	public QuantitativeFact getDiametroTelesistolico() {
		return diametroTelesistolico;
	}
	
	public QuantitativeFact getDiametroTelesistolicoIndicizzato() {
		return diametroTelesistolicoIndicizzato;
	}
	
	public QuantitativeFact getAccorciamentoFrazionale() {
		return accorciamentoFrazionale;
	}
	
	public QuantitativeFact getVolumeTelediastolico() {
		return volumeTelediastolico;
	}
	
	public QuantitativeFact getVolumeTelediastolicoIndicizzato() {
		return volumeTelediastolicoIndicizzato;
	}
	
	public QuantitativeFact getVolumeTelesistolico() {
		return volumeTelesistolico;
	}
	
	public QuantitativeFact getVolumeTelesistolicoIndicizzato() {
		return volumeTelesistolicoIndicizzato;
	}
	
	public QuantitativeFact getFrazioneEiezione() {
		return frazioneEiezione;
	}
	
	public QuantitativeFact getSettoInterventricolare() {
		return settoInterventricolare;
	}
	
	public QuantitativeFact getParetePosteriore() {
		return paretePosteriore;
	}
	
	public QuantitativeFact getMassa() {
		return massa;
	}
	
	public QuantitativeFact getMassaIndicizzata() {
		return massaIndicizzata;
	}
	
	public QuantitativeFact getVolume() {
		return volume;
	}
	
	public QuantitativeFact getVolumeIndicizzato() {
		return volumeIndicizzato;
	}
	
}
