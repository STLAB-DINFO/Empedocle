package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor;

import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.tools.PathTools;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.TypeVisitor;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import java.util.ArrayList;
import java.util.List;

public class StatisticsPathGeneratorVisitor implements TypeVisitor {
	
	private List<String> paths;
	private String basePath;
	
	private static final int MAX_CEILING = 6;
	
	public StatisticsPathGeneratorVisitor(String basePath) {
		super();
		paths = new ArrayList<String>();
		if (basePath != null) {
			this.basePath = PathTools.buildPath(basePath);
		} else {
			this.basePath = "";
		}
	}

	@Override
	public void visitTextualType(TextualType type) {
		basicOperation(type);

	}

	@Override
	public void visitEnumeratedType(EnumeratedType type) {
		basicOperation(type);

	}

	@Override
	public void visitQueriedType(QueriedType type) {
		basicOperation(type);

	}

	@Override
	public void visitQuantitativeType(QuantitativeType type) {
		basicOperation(type);

	}
	
	@Override
	public void visitTemporalType(TemporalType type) {
		basicOperation(type);

	}

	@Override
	public void visitCompositeType(CompositeType type) {
		for(TypeLink tl : type.listChildrenOrdered()) {
			int max = tl.getMax();
			
			if(max == 0) {
				System.out.println("Numero massimo di figli indefinito ["+ tl.getName() +"], verrà limitato a " + MAX_CEILING);
				max = MAX_CEILING;
			}
			
			for(int i = 1; i <= max; i++) {
				String path = "".equals(basePath) ?
						PathTools.normalize(type.getName()) + "/" + PathTools.normalize(tl.getName())
						: basePath + PathTools.normalize(tl.getName());
				
				if(i > 1) {
					path = PathTools.appendSuffix(path, String.valueOf(i));
				}
				
				StatisticsPathGeneratorVisitor v = this.getInstance(path);
				tl.getTarget().accept(v);
				
				paths.addAll(v.getPaths());

			}
		}

	}
	
	protected StatisticsPathGeneratorVisitor getInstance(String path) {
		return new StatisticsPathGeneratorVisitor(path);
	}
	
	private void basicOperation(Type t) {
		if("".equals(basePath)) {
			paths.add(PathTools.normalize(t.getName()));
		}
		else {
			paths.add(PathTools.removeTrailingSlash(basePath));
		}
	}

	public List<String> getPaths() {
		return paths;
	}

}
