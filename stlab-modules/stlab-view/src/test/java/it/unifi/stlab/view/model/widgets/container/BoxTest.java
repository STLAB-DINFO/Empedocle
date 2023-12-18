package it.unifi.stlab.view.model.widgets.container;

import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.widgets.container.Box;
import it.unifi.ing.stlab.view.model.widgets.input.InputText;
import it.unifi.ing.stlab.view.model.widgets.output.OutputValue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class BoxTest {

	protected Box box;
	protected SubViewer inputText, outputValue, outputValue1, outputValue2;
	
	@Before
	public void setUp() {
		box = new Box( UUID.randomUUID().toString() );
		
		inputText = new SubViewer(UUID.randomUUID().toString());
		inputText.assignTarget( new InputText( UUID.randomUUID().toString() ));
		
		outputValue = new SubViewer( UUID.randomUUID().toString() );
		outputValue.assignTarget( new OutputValue( UUID.randomUUID().toString() ));
		
		outputValue1 = new SubViewer( UUID.randomUUID().toString() );
		outputValue1.assignTarget( new OutputValue( UUID.randomUUID().toString() ));
		
		outputValue2 = new SubViewer( UUID.randomUUID().toString() );
		outputValue2.assignTarget( new OutputValue( UUID.randomUUID().toString() ));

	}
	
	@Test
	public void testAddSottoVista1() {
		inputText.assignSource(box);
	}

	@Test
	public void testAddSottoVista2() {
		outputValue.assignSource(box);
		inputText.assignSource(box);
	}
	
	@Test( expected = RuntimeException.class )
	public void testAddSottoVista3() {
		inputText.assignSource(box);
		outputValue.assignSource(box);
	}
	
	@Test( expected = RuntimeException.class )
	public void testAddSottoVista4() {
		outputValue1.assignSource(box);
		inputText.assignSource(box);
		outputValue2.assignSource(box);
	}

}
