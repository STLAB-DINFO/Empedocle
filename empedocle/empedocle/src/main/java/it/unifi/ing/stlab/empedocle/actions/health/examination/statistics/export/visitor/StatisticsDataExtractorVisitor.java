package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor;

import it.unifi.ing.stlab.commons.util.NumberFormatUtils;
import it.unifi.ing.stlab.commons.util.TimeFormat;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.datamodel.FactDataPath;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.tools.PathTools;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

public class StatisticsDataExtractorVisitor implements FactVisitor {
	
	private static final int DEFAULT_MIN_DECIMALS = 2;
	
	private String basePath;
	private List<FactDataPath> results;
	
	public StatisticsDataExtractorVisitor(String basePath) {
		super();
		if(basePath != null) {
			this.basePath = PathTools.buildPath(basePath);
		} else {
			this.basePath = "";
		}
		results = new ArrayList<FactDataPath>();
	}

	@Override
	public void visitTextual(TextualFact fact) {
		basicOperation(fact, fact.getText());

	}
	
	// FIXME in generale, ci vorrebbe anche l'unità di misura (anche se a Cardio non la volevano)
	@Override
	public void visitQuantitative(QuantitativeFact fact) {
		if(fact.getQuantity() != null && fact.getQuantity().getValue() != null) {
			Set<UnitUse> unitUses = ClassHelper.cast(fact.getType(), QuantitativeType.class).listUnits();
			Integer nDecimals = DEFAULT_MIN_DECIMALS;
			for(UnitUse uu : unitUses) {
				if(uu.getUuid().equals(fact.getQuantity().getUnit().getUuid())) {
					nDecimals = uu.getDecimals();
					break;
				}
			}
			
			NumberFormatUtils utils = new NumberFormatUtils();
			basicOperation(fact, utils.formatDecimals(fact.getQuantity().getValue(), 0, nDecimals));
		}

	}

	@Override
	public void visitQualitative(QualitativeFact fact) {
		if(fact.getPhenomenon()!= null) {
			basicOperation(fact, fact.getPhenomenon().getName());
		}

	}
	
	@Override
	public void visitTemporal(TemporalFact fact) {
		if(fact.getDate() != null) {
			TimeFormat format = ClassHelper.cast( fact.getType(), TemporalType.class ).getType();
			SimpleDateFormat sdf = new SimpleDateFormat( format.getDefaultFormat() );
			sdf.setTimeZone( TimeZone.getDefault() );
			basicOperation(fact, sdf.format( fact.getDate() ) );
		}

	}
	
	@Override
	public void visitComposite(CompositeFact fact) {
		Map<String, Integer> typeOccurrences = new HashMap<String, Integer>();

		for(FactLink fl : fact.listChildrenOrdered()) {
			String path = "".equals(basePath) ?
					PathTools.normalize(fact.getType().getName()) + "/" + PathTools.normalize(fl.getType().getName())
					: basePath + PathTools.normalize(fl.getType().getName());
			
			// se ho già incontrato questo tipo, aumento il contatore e lo aggiungo in coda al path
			if(!typeOccurrences.containsKey(fl.getType().getName())) {
				typeOccurrences.put(fl.getType().getName(), 1);
			}
			else {
				typeOccurrences.put(fl.getType().getName(), typeOccurrences.get(fl.getType().getName()) + 1);
				path = PathTools.appendSuffix(path, String.valueOf(typeOccurrences.get(fl.getType().getName())));
			}
			
			if( fl.getTarget() != null ){
				// visito i figli
				StatisticsDataExtractorVisitor v = this.getInstance( path );
				fl.getTarget().accept(v);
				results.addAll(v.getResults());
			}
			
		}
		
	}
	
	protected StatisticsDataExtractorVisitor getInstance(String path) {
		return new StatisticsDataExtractorVisitor(path);
	}

	private void basicOperation(Fact fact, String data) {
		if(fact != null && !fact.isEmpty()) {
			if("".equals(basePath)) {
				results.add( new FactDataPath( PathTools.normalize(fact.getType().getName()), data ) );
			}
			else {
				results.add( new FactDataPath( PathTools.removeTrailingSlash(basePath), data ) );
			}
		}
	}

	public List<FactDataPath> getResults() {
		return results;
	}

}
