package it.unifi.ing.stlab.empedocle.view.controllers.cardiology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.empedocle.view.controllers.cardiology.RepertiMonoBidimensionaliController;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerCustom;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class RepertiMonoBidimensionaliControllerTest {

	protected RepertiMonoBidimensionaliController controller;
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
	private QuantitativeFact superficieCorporea;
	
	protected TypeSelector selector;
	protected Viewer viewer;
	
	@Before
	public void setUp() throws Exception{
		selector = mock(TypeSelector.class);
		viewer = mock(ViewerCustom.class);
		ViewerLink vl = new SubViewer(UUID.randomUUID().toString());
		vl.setSelector(selector);
		when(viewer.getByPriority(anyLong())).thenReturn(vl);
		
		controller = new RepertiMonoBidimensionaliController();
		
		diametroTelediastolico = FactFactory.createQuantitative();
		diametroTelediastolicoIndicizzato = FactFactory.createQuantitative();
		diametroTelesistolico = FactFactory.createQuantitative();
		diametroTelesistolicoIndicizzato = FactFactory.createQuantitative();
		accorciamentoFrazionale = FactFactory.createQuantitative();
		volumeTelediastolico = FactFactory.createQuantitative();
		volumeTelediastolicoIndicizzato = FactFactory.createQuantitative();
		volumeTelesistolico = FactFactory.createQuantitative();
		volumeTelesistolicoIndicizzato = FactFactory.createQuantitative();
		frazioneEiezione = FactFactory.createQuantitative();
		settoInterventricolare = FactFactory.createQuantitative();
		paretePosteriore = FactFactory.createQuantitative();
		massa = FactFactory.createQuantitative();
		massaIndicizzata = FactFactory.createQuantitative();
		volume = FactFactory.createQuantitative();
		volumeIndicizzato = FactFactory.createQuantitative();
		
		superficieCorporea = FactFactory.createQuantitative();
		
		Quantity quantityTeled = new Quantity();
		quantityTeled.setValue(new Double(18));
		diametroTelediastolico.setQuantity(quantityTeled);
		
		Quantity quantityTeledInd = new Quantity();
		quantityTeledInd.setValue(new Double(0));
		diametroTelediastolicoIndicizzato.setQuantity(quantityTeledInd);
		
		Quantity quantityTeles = new Quantity();
		quantityTeles.setValue(new Double(9));
		diametroTelesistolico.setQuantity(quantityTeles);
		
		Quantity quantityTelesInd = new Quantity();
		quantityTelesInd.setValue(new Double(0));
		diametroTelesistolicoIndicizzato.setQuantity(quantityTelesInd);
		
		Quantity quantityAccorciamentoFrazionale = new Quantity();
		quantityAccorciamentoFrazionale.setValue(new Double(0));
		accorciamentoFrazionale.setQuantity(quantityAccorciamentoFrazionale);
		
		Quantity quantityVolumeTelediastolico = new Quantity();
		quantityVolumeTelediastolico.setValue(new Double(15));
		volumeTelediastolico.setQuantity(quantityVolumeTelediastolico);
		
		Quantity quantityVolumeTelediastolicoIndicizzato = new Quantity();
		quantityVolumeTelediastolicoIndicizzato.setValue(new Double(0));
		volumeTelediastolicoIndicizzato.setQuantity(quantityVolumeTelediastolicoIndicizzato);
		
		Quantity quantityVolumeTelesistolico = new Quantity();
		quantityVolumeTelesistolico.setValue(new Double(11));
		volumeTelesistolico.setQuantity(quantityVolumeTelesistolico);
		
		Quantity quantityVolumeTelesistolicoIndicizzato = new Quantity();
		quantityVolumeTelesistolicoIndicizzato.setValue(new Double(0));
		volumeTelesistolicoIndicizzato.setQuantity(quantityVolumeTelesistolicoIndicizzato);
		
		Quantity quantityFrazioneEiezione = new Quantity();
		quantityFrazioneEiezione.setValue(new Double(0));
		frazioneEiezione.setQuantity(quantityFrazioneEiezione);
		
		Quantity quantitySettoInterventricolare = new Quantity();
		quantitySettoInterventricolare.setValue(new Double(43));
		settoInterventricolare.setQuantity(quantitySettoInterventricolare);
		
		Quantity quantityParetePosteriore = new Quantity();
		quantityParetePosteriore.setValue(new Double(76));
		paretePosteriore.setQuantity(quantityParetePosteriore);
		
		Quantity quantityVolume = new Quantity();
		quantityVolume.setValue(new Double(70));
		volume.setQuantity(quantityVolume);
		
		Quantity quantityVolumeIndicizzato = new Quantity();
		quantityVolumeIndicizzato.setValue(new Double(0));
		volumeIndicizzato.setQuantity(quantityVolumeIndicizzato);
		
		Quantity quantityMassa = new Quantity();
		quantityMassa.setValue(new Double(0));
		massa.setQuantity(quantityMassa);
		
		Quantity quantityMassaIndicizzata = new Quantity();
		quantityMassaIndicizzata.setValue(new Double(0));
		massaIndicizzata.setQuantity(quantityMassaIndicizzata);
		
		Quantity quantitySC = new Quantity();
		quantitySC.setValue(new Double(2.12));
		superficieCorporea.setQuantity(quantitySC);
		
		FieldUtils.assignField(controller, "diametroTelediastolico", diametroTelediastolico);
		FieldUtils.assignField(controller, "diametroTelesistolico", diametroTelesistolico);
		FieldUtils.assignField(controller, "volumeTelediastolico", volumeTelediastolico);
		FieldUtils.assignField(controller, "volumeTelesistolico", volumeTelesistolico);
		FieldUtils.assignField(controller, "settoInterventricolare", settoInterventricolare);
		FieldUtils.assignField(controller, "paretePosteriore", paretePosteriore);
		FieldUtils.assignField(controller, "volume", volume);
	}
	
	@Test
	public void testRetrieveDiametroTelediastolicoIndicizzato(){
		when(selector.apply(any(Fact.class))).thenReturn(diametroTelediastolicoIndicizzato, superficieCorporea);
		
		controller.retrieveDiametroTelediastolicoIndicizzato(FactFactory.createComposite(), viewer);
		
		assertNotNull(controller.getDiametroTelediastolicoIndicizzato().getQuantity());
		assertNotNull(controller.getDiametroTelediastolicoIndicizzato().getQuantity().getValue());
		assertEquals(new Double(8), controller.getDiametroTelediastolicoIndicizzato().getQuantity().getValue());
		
	}
	
	@Test
	public void testRetrieveDiametroTelediastolicoIndicizzatoNull(){
		diametroTelediastolico.getQuantity().setValue(null);
		diametroTelediastolicoIndicizzato.getQuantity().setValue(null);
		when(selector.apply(any(Fact.class))).thenReturn(diametroTelediastolicoIndicizzato, superficieCorporea);
		
		controller.retrieveDiametroTelediastolicoIndicizzato(FactFactory.createComposite(), viewer);
		
		assertNotNull(controller.getDiametroTelediastolicoIndicizzato().getQuantity());
		assertNull(controller.getDiametroTelediastolicoIndicizzato().getQuantity().getValue());
		assertEquals(null, controller.getDiametroTelediastolicoIndicizzato().getQuantity().getValue());
		
	}
	
	@Test
	public void testRetrieveDiametroTelesistolicoIndicizzato(){
		when(selector.apply(any(Fact.class))).thenReturn(diametroTelesistolicoIndicizzato, superficieCorporea);
		
		controller.retrieveDiametroTelesistolicoIndicizzato(FactFactory.createComposite(), viewer);
		
		assertNotNull(controller.getDiametroTelesistolicoIndicizzato().getQuantity());
		assertNotNull(controller.getDiametroTelesistolicoIndicizzato().getQuantity().getValue());
		assertEquals(new Double(4), controller.getDiametroTelesistolicoIndicizzato().getQuantity().getValue());
		
	}

	@Test
	public void testRetrieveAccorciamentoFrazionale(){
		when(selector.apply(any(Fact.class))).thenReturn(accorciamentoFrazionale);
		
		controller.retrieveAccorciamentoFrazionale(FactFactory.createComposite(), viewer);
		
		assertNotNull(controller.getAccorciamentoFrazionale().getQuantity());
		assertNotNull(controller.getAccorciamentoFrazionale().getQuantity().getValue());
		assertEquals(new Double(50), controller.getAccorciamentoFrazionale().getQuantity().getValue());
		
	}
	
	@Test
	public void testRetrieveVolumeTelediastolicoIndicizzato(){
		when(selector.apply(any(Fact.class))).thenReturn(volumeTelediastolicoIndicizzato, superficieCorporea);
		
		controller.retrieveVolumeTelediastolicoIndicizzato(FactFactory.createComposite(), viewer);
		
		assertNotNull(controller.getVolumeTelediastolicoIndicizzato().getQuantity());
		assertNotNull(controller.getVolumeTelediastolicoIndicizzato().getQuantity().getValue());
		assertEquals(new Double(7), controller.getVolumeTelediastolicoIndicizzato().getQuantity().getValue());
		
	}
	
	@Test
	public void testRetrieveVolumeTelesistolicoIndicizzato(){
		when(selector.apply(any(Fact.class))).thenReturn(volumeTelesistolicoIndicizzato, superficieCorporea);
		
		controller.retrieveVolumeTelesistolicoIndicizzato(FactFactory.createComposite(), viewer);
		
		assertNotNull(controller.getVolumeTelesistolicoIndicizzato().getQuantity());
		assertNotNull(controller.getVolumeTelesistolicoIndicizzato().getQuantity().getValue());
		assertEquals(new Double(5), controller.getVolumeTelesistolicoIndicizzato().getQuantity().getValue());
		
	}
	
	@Test
	public void testRetrieveFrazioneEiezione(){
		volumeTelediastolicoIndicizzato.getQuantity().setValue(new Double(40));
		volumeTelesistolicoIndicizzato.getQuantity().setValue(new Double(14.5));
		FieldUtils.assignField(controller, "volumeTelediastolicoIndicizzato", volumeTelediastolicoIndicizzato);
		FieldUtils.assignField(controller, "volumeTelesistolicoIndicizzato", volumeTelesistolicoIndicizzato);
		
		when(selector.apply(any(Fact.class))).thenReturn(frazioneEiezione);
		
		controller.retrieveFrazioneEiezione(FactFactory.createComposite(), viewer);
		
		assertNotNull(controller.getFrazioneEiezione().getQuantity());
		assertNotNull(controller.getFrazioneEiezione().getQuantity().getValue());
		assertEquals(new Double(64), controller.getFrazioneEiezione().getQuantity().getValue());
		
	}
	
	@Test
	public void testRetrieveMassa(){
		when(selector.apply(any(Fact.class))).thenReturn(massa);
		
		controller.retrieveMassa(FactFactory.createComposite(), viewer);
		
		assertNotNull(controller.getMassa().getQuantity());
		assertNotNull(controller.getMassa().getQuantity().getValue());
		assertEquals(new Double(2135), controller.getMassa().getQuantity().getValue());
		
	}
	
	@Test
	public void testRetrieveMassaIndicizzata(){
		massa.getQuantity().setValue(new Double(2135));
		FieldUtils.assignField(controller, "massa", massa);
		
		when(selector.apply(any(Fact.class))).thenReturn(massaIndicizzata, superficieCorporea);
		
		controller.retrieveMassaIndicizzata(FactFactory.createComposite(), viewer);
		
		assertNotNull(controller.getMassaIndicizzata().getQuantity());
		assertNotNull(controller.getMassaIndicizzata().getQuantity().getValue());
		assertEquals(new Double(1007), controller.getMassaIndicizzata().getQuantity().getValue());
		
	}
	
	@Test
	public void testRetrieveVolumeIndicizzato(){
		when(selector.apply(any(Fact.class))).thenReturn(volumeIndicizzato, superficieCorporea);
		
		controller.retrieveVolumeIndicizzato(FactFactory.createComposite(), viewer);
		
		assertNotNull(controller.getVolumeIndicizzato().getQuantity());
		assertNotNull(controller.getVolumeIndicizzato().getQuantity().getValue());
		assertEquals(new Double(33), controller.getVolumeIndicizzato().getQuantity().getValue());
		
	}
	
}
