package it.unifi.ing.stlab.empedocle.view.controllers.cardiology;

import it.unifi.ing.stlab.commons.util.NumberFormatUtils;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.dao.types.PhenomenonDao;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QualitativeType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.view.controllers.ContainerController;
import it.unifi.ing.stlab.view.model.Viewer;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

// TODO testare medicazione ulcere controller
@Named
@RequestScoped
public class MedicazioneUlcereController extends ContainerController {

	
	@Inject
	private PhenomenonDao phenomenonDao;
	
	private NumberFormatUtils numberUtils;
	
	public MedicazioneUlcereController(){
		numberUtils = new NumberFormatUtils();
	}
	
	//Retrieve text methods
	public String retrieveNoteDx(Fact value, Viewer viewer){
		return textualDescription( value, viewer, 6 );
	}

	public String retrieveNoteSx(Fact value, Viewer viewer){
		return textualDescription( value, viewer, 7 );
	}
	
	public String retrieveEdemiDx(Fact value, Viewer viewer){
		return edemiDescription( 
				qualitative( select( value, viewer, 8 ) ), 
				quantitative( select( value, viewer, 9 ) ) );
	}
	
	public String retrieveEdemiSx(Fact value, Viewer viewer){
		return edemiDescription( 
				qualitative( select( value, viewer, 10 ) ), 
				quantitative( select( value, viewer, 11 ) ) );
	}
	
	private String edemiDescription( QualitativeFact edemi, QuantitativeFact circonferenza ) {
		if ( edemi == null || circonferenza == null ) return "";

		StringBuffer buffer = new StringBuffer( qualitativeDescription( edemi.getPhenomenon() ) );
		
		if( buffer.toString().equals("Si") ){
			buffer.append( " - Circonf: " ) 
					.append( quantityDescription( circonferenza.getQuantity() ));
		}
		
		return buffer.toString();
		
	}
	
	
	public String retrieveDimensioniH(Fact value, Viewer viewer){
		return  quantityDescription( value, viewer, 12 );
	}

	public String retrieveDimensioniL(Fact value, Viewer viewer){
		return  quantityDescription( value, viewer, 13 );
	}
	
	public String retrieveDimensioniArea(Fact value, Viewer viewer){
		return  quantityDescription( value, viewer, 14 );
	}
	
	public String retrieveProfondita(Fact value, Viewer viewer){
		return qualitativeDescription( value, viewer, 15 );
	}
	
	public String retrieveNecrosi(Fact value, Viewer viewer){
		return qualitativeDescription( value, viewer, 17 );
	}
	
	public String retrieveInfezione(Fact value, Viewer viewer){
		return qualitativeDescription( value, viewer, 18 );
	}
	
	public String retrieveFlogosi(Fact value, Viewer viewer){
		return qualitativeDescription( value, viewer, 19 );
	}
	
	public String retrieveAlterazioniDeambulazione(Fact value, Viewer viewer){
		return textualDescription(value, viewer, 21);
	}
	
	public String retrieveEpocaInsorgenza(Fact value, Viewer viewer){
		return textualDescription(value, viewer, 22);
	}
	
	public String retrievePresenzaDolore(Fact value, Viewer viewer){
		return qualitativeDescription( value, viewer, 23 );
	}
	
	public String retrieveFasciaTemporale(Fact value, Viewer viewer){
		return qualitativeDescription( value, viewer, 24 );
	}
	
	public String retrieveNoteDetersione(Fact value, Viewer viewer){
		return textualDescription(value, viewer, 27 );
	}
	
	public String retrieveDisinfezione(Fact value, Viewer viewer){
		return textualDescription(value, viewer, 28);
	}
	
	public String retrieveNoteCopertura(Fact value, Viewer viewer){
		return textualDescription(value, viewer, 30);
	}
	
	
	public String retrieveQuantitaBenda(Fact value, Viewer viewer, Phenomenon phenomenon){
		for(FactLink fl : selectLinks( value, viewer, 33 ) ){
			if(phenomenon.equals(qualitative(getFactByTypePriority(fl.getTarget().listActiveLinks(), 0l)).getPhenomenon())){
				QuantitativeFact qf = quantitative(getFactByTypePriority(fl.getTarget().listActiveLinks(), 1l));
				return qf == null ? "" : "Quantità: " + quantityDescription( qf.getQuantity() );
			}
		}
		
		return "";
	}
	
	//FIXME per ora l'ho messo qui..
	private Fact getFactByTypePriority(Collection<? extends FactLink> links, Long index){
		for(FactLink fl : links)
			if(fl.getType().getPriority() == index)
				return fl.getTarget();
		
		return null;
	}
	
	
	public String retrieveNoteCompressione(Fact value, Viewer viewer){
		return textualDescription(value, viewer, 34);
	}
	
	public String retrieveTerapiaAtto(Fact value, Viewer viewer){
		return textualDescription(value, viewer, 35);
	}
	
	public String retrieveTerapiaPrescritta(Fact value, Viewer viewer){
		return textualDescription(value, viewer, 36);
	}
	
	public String retrieveOsservazioni(Fact value, Viewer viewer){
		return textualDescription( value, viewer, 37 );
	}
	
	// Metodi per Cardiologia II Rev
	public String retrieveDimensioniHSx(Fact value, Viewer viewer){
		return  quantityDescription( value, viewer, 38 );
	}

	public String retrieveDimensioniLSx(Fact value, Viewer viewer){
		return  quantityDescription( value, viewer, 39 );
	}
	
	public String retrieveDimensioniAreaSx(Fact value, Viewer viewer){
		return  quantityDescription( value, viewer, 40 );
	}
	
	public String retrieveProfonditaSx(Fact value, Viewer viewer){
		return qualitativeDescription( value, viewer, 41 );
	}
	
	public String retrieveNecrosiSx(Fact value, Viewer viewer){
		return qualitativeDescription( value, viewer, 43 );
	}
	
	public String retrieveInfezioneSx(Fact value, Viewer viewer){
		return qualitativeDescription( value, viewer, 44 );
	}
	
	public String retrieveFlogosiSx(Fact value, Viewer viewer){
		return qualitativeDescription( value, viewer, 45 );
	}
	
	public String retrieveNumMedicazioni(Fact value, Viewer viewer){
		return quantityDescription( value, viewer, 46 );
	}
	
	public String retrieveNumPrestazioni(Fact value, Viewer viewer){
		return quantityDescription( value, viewer, 47 );
	}
	
	//Retrieve phenomena methods
	public List<Phenomenon> retrieveRegioneGambaPhenomena(Fact value, Viewer viewer){
		QualitativeType qt = qualitativeType( selectType( value, viewer, 0 ) );
		return alphabeticalSort(phenomenonDao.findByType(qt, new Date()));
		
	}
	
	public List<Phenomenon> retrieveRegioneMalleoloPhenomena(Fact value, Viewer viewer){
		QualitativeType qt = qualitativeType( selectType( value, viewer, 2 ) );
		return alphabeticalSort(phenomenonDao.findByType(qt, new Date()));
		
	}
	
	public List<Phenomenon> retrieveRegionePiedePhenomena(Fact value, Viewer viewer){
		QualitativeType qt = qualitativeType( selectType( value, viewer, 4 ) );
		List<Phenomenon> result = alphabeticalSort(phenomenonDao.findByType(qt, new Date()));
		if(result != null && !result.isEmpty()){
			Phenomenon p = result.remove(5);
			result.add(1, p);
		}
		
		return result;
	}
	
	public List<Phenomenon> retrieveBordiPhenomena(Fact value, Viewer viewer){
		QualitativeType qt = qualitativeType( selectType( value, viewer, 16 ) );
		return alphabeticalSort(phenomenonDao.findByType(qt, new Date()));
	}
	
	public List<Phenomenon> retrieveEziologiaPhenomena(Fact value, Viewer viewer){
		QualitativeType qt = qualitativeType( selectType( value, viewer, 20 ) );
		return alphabeticalSort(phenomenonDao.findByType(qt, new Date()));
		
	}
	
	public List<Phenomenon> retrieveModalitaDetersionePhenomena(Fact value, Viewer viewer){
		QualitativeType qt = qualitativeType( selectType( value, viewer, 25 ) );
		return alphabeticalSort(phenomenonDao.findByType(qt, new Date()));
		
	}

	public List<Phenomenon> retrieveMaterialiUsatiPhenomena(Fact value, Viewer viewer){
		QualitativeType qt = qualitativeType( selectType( value, viewer, 26 ) );
		return alphabeticalSort(phenomenonDao.findByType(qt, new Date()));
		
	}
	
	public List<Phenomenon> retrieveTipologiaCoperturaPhenomena(Fact value, Viewer viewer){
		QualitativeType qt = qualitativeType( selectType( value, viewer, 29 ) );
		return alphabeticalSort(phenomenonDao.findByType(qt, new Date()));
		
	}
	
	public List<Phenomenon> retrieveMaterialeProtezionePhenomena(Fact value, Viewer viewer){
		QualitativeType qt = qualitativeType( selectType( value, viewer, 31 ) );
		return alphabeticalSort(phenomenonDao.findByType(qt, new Date()));
	}
	
	public List<Phenomenon> retrieveTipoCompressionePhenomena(Fact value, Viewer viewer){
		QualitativeType qt = qualitativeType( selectType( value, viewer, 32 ) );
		return alphabeticalSort(phenomenonDao.findByType(qt, new Date()));
		
	}
	
	public List<Phenomenon> retrieveTipoBendaPhenomena(Fact value, Viewer viewer){
		CompositeType ct = compositeType( selectType( value, viewer, 33 ) );
		if( ct == null )
			return null;
		
		QualitativeType qt = qualitativeType( ct.listChildrenOrdered().get( 0 ).getTarget() );
		return alphabeticalSort(phenomenonDao.findByType(qt, new Date()));
	}
	
	private Type selectType(Fact fact, Viewer viewer, int index) {
		TypeLink selected = viewer.listChildrenOrdered().get( index ).getSelector().applyType( fact.getType() );
		
		if( selected == null )
			return null;
		
		return selected.getTarget();
	}
	
	private List<Phenomenon> alphabeticalSort(List<Phenomenon> inputList){
		if(inputList == null) return null;
		
		Collections.sort(inputList, new Comparator<Phenomenon>() {

			@Override
			public int compare(Phenomenon arg0, Phenomenon arg1) {
				if(arg0.getName() == null) return -1;
				if(arg1.getName() == null) return 1;
				
				return arg0.getName().compareTo(arg1.getName());
			}
			
		});
		
		return inputList;
	}
	
	
	//Checked methods
	public boolean isCheckedRegioneGambaDx(Fact value, Viewer viewer, Phenomenon phenomenon){
		return isChecked( selectLinks( value, viewer, 0 ), phenomenon);
	}
	
	public boolean isCheckedRegioneGambaSx(Fact value, Viewer viewer, Phenomenon phenomenon){
		return isChecked( selectLinks( value, viewer, 1 ), phenomenon);
	}
	
	public boolean isCheckedRegioneMalleoloDx(Fact value, Viewer viewer, Phenomenon phenomenon){
		return isChecked( selectLinks( value, viewer, 2 ), phenomenon);
	}
	
	public boolean isCheckedRegioneMalleoloSx(Fact value, Viewer viewer, Phenomenon phenomenon){
		return isChecked( selectLinks( value, viewer, 3 ), phenomenon);
	}
	
	public boolean isCheckedRegionePiedeDx(Fact value, Viewer viewer, Phenomenon phenomenon){
		return isChecked( selectLinks( value, viewer, 4 ), phenomenon);
	}
	
	public boolean isCheckedRegionePiedeSx(Fact value, Viewer viewer, Phenomenon phenomenon){
		return isChecked( selectLinks( value, viewer, 5 ), phenomenon);
	}
	
	public boolean isCheckedBordi(Fact value, Viewer viewer, Phenomenon phenomenon){
		return isChecked( selectLinks( value, viewer, 16 ), phenomenon);
	}
	
	public boolean isCheckedEziologia(Fact value, Viewer viewer, Phenomenon phenomenon){
		return isChecked( selectLinks( value, viewer, 20 ), phenomenon);
	}
	
	public boolean isCheckedModalitaDetersione(Fact value, Viewer viewer, Phenomenon phenomenon){
		return isChecked( selectLinks( value, viewer, 25 ), phenomenon);
	}
	
	public boolean isCheckedMaterialiUsati(Fact value, Viewer viewer, Phenomenon phenomenon){
		return isChecked( selectLinks( value, viewer, 26 ), phenomenon);
	}
	
	public boolean isCheckedTipologiaCopertura(Fact value, Viewer viewer, Phenomenon phenomenon){
		return isChecked( selectLinks( value, viewer, 29 ), phenomenon);
	}
	
	public boolean isCheckedMaterialeProtezione(Fact value, Viewer viewer, Phenomenon phenomenon){
		return isChecked( selectLinks( value, viewer, 31 ), phenomenon);
	}
	
	public boolean isCheckedTipoCompressione(Fact value, Viewer viewer, Phenomenon phenomenon){
		return isChecked( selectLinks( value, viewer, 32 ), phenomenon);
	}
	
	public boolean isCheckedTipoBenda(Fact value, Viewer viewer, Phenomenon phenomenon){
		for(FactLink fl : selectLinks( value, viewer, 33 ) ){
			if(phenomenon.equals( qualitative( getFactByTypePriority(fl.getTarget().listActiveLinks(), 0l)).getPhenomenon()))
				return true;
			
		}
		
		return false;

	}
	
	// Metodo checked per Cardiologia II Rev
	public boolean isCheckedBordiSx(Fact value, Viewer viewer, Phenomenon phenomenon){
		return isChecked( selectLinks( value, viewer, 42 ), phenomenon);
	}
	
	
	private boolean isChecked(Collection<FactLink> factLinks, Phenomenon phenomenon){
		if(factLinks == null)
			return false;
		
		for(FactLink fl : factLinks){
			if(phenomenon.equals( qualitative( fl.getTarget()).getPhenomenon()))
				return true;
		}
		
		return false;
	}
	
	
	//
	// Cast methods
	//
	
	private TextualFact textual( Fact fact ) {
		if ( fact == null ) return null;
		return ClassHelper.cast( fact, TextualFact.class );
	}

	private QualitativeFact qualitative( Fact fact ) {
		if ( fact == null ) return null;
		return ClassHelper.cast( fact, QualitativeFact.class );
	}

	private QuantitativeFact quantitative( Fact fact ) {
		if ( fact == null ) return null;
		return ClassHelper.cast( fact, QuantitativeFact.class );
	}
	
	private QualitativeType qualitativeType( Type type ) {
		if ( type == null ) return null;
		return ClassHelper.cast( type, QualitativeType.class );
	}
	
	private CompositeType compositeType( Type type ) {
		if ( type == null ) return null;
		return ClassHelper.cast( type, CompositeType.class );
	}
	
	
	//
	// select methods
	//
	
	private Fact select( Fact fact, Viewer viewer, int index ) {
		if ( viewer == null ) return null;
		
		return viewer.listChildrenOrdered().get( index ).getSelector().apply( fact );
	}

	private Set<FactLink> selectLinks( Fact fact, Viewer viewer, int index ) {
		if ( viewer == null ) return null;
		
		return viewer.listChildrenOrdered().get( index ).getSelector().applyItem( fact );
	}
	
	
	//
	// formatting methods
	//
	
	private String textualDescription( Fact fact, Viewer viewer, int index ) {
		TextualFact textualFact = textual( select( fact, viewer, index ) );
		if ( textualFact != null ) {
			return textualFact.getText();
		} else {
			return "";
		}
	}

	private String quantityDescription( Fact fact, Viewer viewer, int index ) {
		QuantitativeFact quantitativeFact = quantitative( select( fact, viewer, index ) );
		if ( quantitativeFact != null ) {
			return quantityDescription( quantitativeFact.getQuantity() );
		} else {
			return "";
		}
	}

	private String quantityDescription( Quantity q ) {
		if ( q == null || q.getValue() == null ) return "";
		
		StringBuffer buffer = new StringBuffer();
		buffer.append( numberUtils.formatDecimals( q.getValue(), 0, 2 ) );
	
		if ( q.getUnit() != null ) {
			buffer.append("  ");
			buffer.append( q.getUnit().getSimbol() );
		}
	
		return buffer.toString();
	}

	private String qualitativeDescription( Fact fact, Viewer viewer, int index ) {
		QualitativeFact qualitativeFact = qualitative( select( fact, viewer, index ));
		if ( qualitativeFact != null ) {
			return  qualitativeDescription( qualitativeFact.getPhenomenon() );
		} else {
			return "";
		}
	}

	private String qualitativeDescription( Phenomenon ph ) {
		if ( ph != null ) {
			return ph.getName();
		} else {
			return "";
		}
	}

}
