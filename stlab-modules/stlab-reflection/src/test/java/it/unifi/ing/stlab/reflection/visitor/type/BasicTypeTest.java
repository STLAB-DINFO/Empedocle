package it.unifi.ing.stlab.reflection.visitor.type;

import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitUseFactory;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import java.util.Calendar;

import org.junit.Before;

public class BasicTypeTest {
	
	protected TextualType textualType;
	protected EnumeratedType enumType;
	protected QuantitativeType qtType;
	protected QueriedType queriedType;
	protected CompositeType compType;
	protected CompositeType anonymousCmpType;
	protected TextualType anonymousTxtType;
	
	@Before
	public void setUp() {
		textualType = TypeFactory.createTextualType();
		enumType = TypeFactory.createEnumeratedType();
		qtType = TypeFactory.createQuantitativeType();
		queriedType = TypeFactory.createQueriedType();
		compType = TypeFactory.createCompositeType();
		
		initProperties(textualType, "TipoTXT", "Un tipo txt");
		initProperties(enumType, "TipoENUM", "Un tipo enum");
		initProperties(qtType, "TipoQNT", "Un tipo qnt");
		initProperties(queriedType, "TipoQUERY", "Un tipo queried");
		initProperties(compType, "TipoCMP", "Un tipo cmp");
		
		queriedType.setQueryDef("QUERY");
		
		Phenomenon ph = PhenomenonFactory.createPhenomenon();
		ph.setName("phen1");
		ph.setPosition(0);
		ph.setTimeRange(null);
		enumType.addPhenomenon(ph);
		
		UnitUse uu = UnitUseFactory.createUnitUse();
		uu.setDecimals(2);
		uu.setDigits(5);
		uu.setUnit(UnitFactory.createUnit());
		uu.getUnit().setName("Unit");
		uu.getUnit().setSimbol("Sym");
		qtType.addUnit(uu);
		
		TypeLink tl1 = TypeLinkFactory.createLink();
		tl1.assignSource(compType);
		tl1.assignTarget(textualType);
		tl1.setName("link1");
		tl1.setMin(0);
		tl1.setMax(1);
		tl1.setPriority(0L);
		
		// tipi anonimi
		anonymousCmpType = TypeFactory.createCompositeType();
		initProperties(anonymousCmpType, "TipoCMP anonimo", "Un tipo cmp anonimo");
		anonymousCmpType.setAnonymous(true);

		anonymousTxtType = TypeFactory.createTextualType();
		initProperties(anonymousTxtType, "TipoTXT anonimo", "Un tipo txt anonimo");
		anonymousTxtType.setAnonymous(true);
		
		TypeLink tl2 = TypeLinkFactory.createLink();
		tl2.assignSource(anonymousCmpType);
		tl2.assignTarget(anonymousTxtType);
		tl2.setName("link2");
		tl2.setMin(0);
		tl2.setMax(1);
		tl2.setPriority(0L);
		
	}
	
	private void initProperties(Type t, String name, String desc) {
		t.setAnonymous(false);
		t.setDescription(desc);
		t.setName(name);
		t.setReadOnly(false);
		t.setRecurrent(false);
		t.setTimeRange(new TimeRange(new Time(Calendar.getInstance().getTime()), new Time(Calendar.getInstance().getTime())));
		
	}

}
