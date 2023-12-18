package it.unifi.ing.stlab.view.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.view.model.widgets.output.OutputField;

import java.util.UUID;

import javax.enterprise.inject.Instance;

import org.junit.Before;
import org.junit.Test;

public class OutputFieldControllerTest {

	protected OutputFieldController controller;
	protected OutputField outputField;
	protected FieldRetriever retriever;
	
	@Before
	@SuppressWarnings("unchecked")
	public void setUp(){
		controller = new OutputFieldController();
		outputField = new OutputField(UUID.randomUUID().toString());
		Instance<FieldRetriever> retrieverInstance = mock(Instance.class);
		retriever = mock(FieldRetriever.class);
		when(retrieverInstance.get()).thenReturn(retriever);
		FieldUtils.assignField(controller, "retriever", retrieverInstance);
	}
	
	@Test
	public void testObtainField(){
		Fact fact = FactFactory.createTextual();
		controller.obtainField(outputField, fact);
		
		verify(retriever).retrieve(fact, outputField.getPath());
	}
	
}
