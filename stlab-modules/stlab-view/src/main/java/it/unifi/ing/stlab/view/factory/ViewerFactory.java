package it.unifi.ing.stlab.view.factory;

import java.lang.reflect.Constructor;
import java.util.UUID;

import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.widgets.container.Box;
import it.unifi.ing.stlab.view.model.widgets.container.ConditionalPanel;
import it.unifi.ing.stlab.view.model.widgets.container.FactPanel;
import it.unifi.ing.stlab.view.model.widgets.container.Grid;
import it.unifi.ing.stlab.view.model.widgets.container.Paragraph;
import it.unifi.ing.stlab.view.model.widgets.container.Report;
import it.unifi.ing.stlab.view.model.widgets.container.TabbedPanel;
import it.unifi.ing.stlab.view.model.widgets.input.Combo;
import it.unifi.ing.stlab.view.model.widgets.input.FileUpload;
import it.unifi.ing.stlab.view.model.widgets.input.InputList;
import it.unifi.ing.stlab.view.model.widgets.input.InputTemporal;
import it.unifi.ing.stlab.view.model.widgets.input.InputText;
import it.unifi.ing.stlab.view.model.widgets.input.Suggestion;
import it.unifi.ing.stlab.view.model.widgets.input.TextArea;
import it.unifi.ing.stlab.view.model.widgets.output.Label;
import it.unifi.ing.stlab.view.model.widgets.output.OutputField;
import it.unifi.ing.stlab.view.model.widgets.output.OutputImage;
import it.unifi.ing.stlab.view.model.widgets.output.OutputLink;
import it.unifi.ing.stlab.view.model.widgets.output.OutputList;
import it.unifi.ing.stlab.view.model.widgets.output.OutputMeasurementUnit;
import it.unifi.ing.stlab.view.model.widgets.output.OutputPath;
import it.unifi.ing.stlab.view.model.widgets.output.OutputType;
import it.unifi.ing.stlab.view.model.widgets.output.OutputValue;

public class ViewerFactory {

	//
	// Containers
	//
	
	public static Box createBox() {
		return createInstance( Box.class );
	}
	public static ConditionalPanel createConditionalPanel() {
		return createInstance( ConditionalPanel.class );
	}
	public static Grid createGrid() {
		return createInstance( Grid.class );
	}
	public static Report createReport() {
		return createInstance( Report.class );
	}
	public static TabbedPanel createTabbedPanel() {
		return createInstance( TabbedPanel.class );
	}
	public static Paragraph createParagraph() {
		return createInstance( Paragraph.class );
	}

	
	//
	// Input
	//
	
	public static Combo createCombo() {
		return createInstance( Combo.class );
	}
	public static InputList createInputList() {
		return createInstance( InputList.class );
	}
	public static InputText createInputText() {
		return createInstance( InputText.class );
	}
	public static InputTemporal createInputTemporal() {
		return createInstance( InputTemporal.class );
	}
	public static Suggestion createSuggestion() {
		return createInstance( Suggestion.class );
	}
	public static TextArea createTextArea() {
		return createInstance( TextArea.class );
	}
	public static FileUpload createFileUpload() {
		return createInstance( FileUpload.class );
	}
	
	
	//
	// Output
	//
	
	public static Label createLabel() {
		return createInstance( Label.class );
	}
	public static OutputField createOutputField() {
		return createInstance( OutputField.class );
	}
	public static OutputImage createOutputImage() {
		return createInstance( OutputImage.class );
	}
	public static OutputList createOutputList() {
		return createInstance( OutputList.class );
	}
	public static OutputMeasurementUnit createOutputUnit() {
		return createInstance( OutputMeasurementUnit.class );
	}
	public static OutputPath createOutputPath() {
		return createInstance( OutputPath.class );
	}
	public static OutputType createOutputType() {
		return createInstance( OutputType.class );
	}
	public static OutputValue createOutputValue() {
		return createInstance( OutputValue.class );
	}
	public static FactPanel createFactPanel() {
		return createInstance( FactPanel.class );
	}
	public static OutputLink createOutputLink() {
		return createInstance( OutputLink.class );
	}	
	
	private static <T extends Viewer> T createInstance( Class<T> type ) { 
		try {
			Constructor<T> constructor = type.getConstructor( String.class );
			T result = constructor.newInstance( UUID.randomUUID().toString() );
			result.init();
			return result;
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}
	
}
