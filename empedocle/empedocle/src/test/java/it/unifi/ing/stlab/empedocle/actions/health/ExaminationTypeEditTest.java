package it.unifi.ing.stlab.empedocle.actions.health;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;

import it.unifi.ing.stlab.empedocle.actions.health.examination.types.AgendaExamTypeBean;
import it.unifi.ing.stlab.empedocle.actions.health.examination.types.ExaminationTypeEdit;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.test.FieldUtils;

//XXX da rifare
public class ExaminationTypeEditTest {

	protected ExaminationTypeEdit examTypeEdit;
	protected EntityManager entityManager;
	protected AgendaDao agendaDao;
	protected List<Agenda> addedAgendas;
	protected List<Agenda> removedAgendas;
	
	@Before
	public void setUp() {
		entityManager = mock( EntityManager.class );
		when( entityManager.merge(any(Agenda.class))).thenReturn(null);
		
		examTypeEdit = new ExaminationTypeEdit();
		agendaDao = mock( AgendaDao.class );
		FieldUtils.assignField( examTypeEdit, "agendaDao", agendaDao );
		
		addedAgendas = new ArrayList<Agenda>();
		removedAgendas = new ArrayList<Agenda>();
		FieldUtils.assignField( examTypeEdit, "agendas", new ArrayList<AgendaExamTypeBean>() );
		FieldUtils.assignField( examTypeEdit, "addedAgendas", addedAgendas );
		FieldUtils.assignField( examTypeEdit, "removedAgendas", removedAgendas );
	}
	
//	@Test
//	public void testAdjustAddedAgenda() {
//		Agenda agenda = AgendaFactory.createAgenda();
//		agenda.setUuid( "UUID PROVA" );
//		AgendaExamTypeBean bean = new AgendaExamTypeBean( agenda, null, null );
//		removedAgendas.add( agenda );
//		
//		assertTrue( addedAgendas.isEmpty() );
//		assertEquals( addedAgendas.size(), 0 );
//		assertEquals( removedAgendas.size(), 1 );
//		
//		examTypeEdit.adjustAddedAgendas( bean );
//		
//		assertEquals( addedAgendas.size(), 1 );
//		assertEquals( addedAgendas.get( 0 ).getUuid(), agenda.getUuid() );
//		assertTrue( removedAgendas.isEmpty() );
//		assertEquals( removedAgendas.size(), 0 );
//	}
//	
//	@Test
//	public void testRemoveAgenda() {
//		Agenda agenda = AgendaFactory.createAgenda();
//		agenda.setUuid( "UUID PROVA" );
//		AgendaExamTypeBean bean = new AgendaExamTypeBean( agenda, null, null );
//		FieldUtils.assignField(bean, "entityManager", entityManager);
//		addedAgendas.add( agenda );
//		
//		assertEquals( addedAgendas.size(), 1 );
//		assertTrue( removedAgendas.isEmpty() );
//		assertEquals( removedAgendas.size(), 0 );
//		
//		examTypeEdit.removeAgenda( bean );
//		
//		assertEquals( addedAgendas.size(), 0 );
//		assertFalse( removedAgendas.isEmpty() );
//		assertEquals( removedAgendas.size(), 1 );
//		assertEquals( removedAgendas.get( 0 ).getUuid(), agenda.getUuid() );
//	}
//	
//	
//	@Test
//	public void testAutocomplete() {
//		List<SelectItem> result = examTypeEdit.getAgendaSuggestion().autocomplete( "123" );
//		verify( agendaDao ).findUnusedAgendasBySuggestion( "123", 10 );
//		assertTrue( result.isEmpty() );
//		
//		addedAgendas.add( AgendaFactory.createAgenda() );
//		examTypeEdit.getAgendaSuggestion().autocomplete( "123" );
//		verify( agendaDao ).findUnusedAgendasBySuggestion( "123", 10, addedAgendas );
//		
//		Agenda agenda = AgendaFactory.createAgenda();
//		agenda.setUuid( "UUID PROVA" );
//		agenda.setCode( "123" );
//		agenda.setDescription( "PROVA AGENDA" );
//		AgendaExamTypeBean bean = new AgendaExamTypeBean( agenda, null, null );
//		FieldUtils.assignField(bean, "entityManager", entityManager);
//		examTypeEdit.removeAgenda( bean );
//		
//		result = examTypeEdit.getAgendaSuggestion().autocomplete( "123" );
//		assertFalse( result.isEmpty() );
//		assertEquals( result.size(), 1 );
//		assertEquals( result.get( 0 ).getValue(), agenda.getUuid() );
//		assertEquals( result.get( 0 ).getLabel(), agenda.toString() );
//	}
	
	
}
