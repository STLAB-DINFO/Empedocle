package it.unifi.stlab.view.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.JpaTest;
import it.unifi.ing.stlab.view.dao.ViewerDao;
import it.unifi.ing.stlab.view.dao.ViewerDaoBean;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerCustom;
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

//XXX check
@Ignore
public class ViewerDaoFindByIdTest extends JpaTest {

	protected ViewerDao viewerDao;

	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "test_mysql" );
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		viewerDao = new ViewerDaoBean();
		FieldUtils.assignField( viewerDao, "entityManager", entityManager );

		entityManager.clear();
	}

	@Test
	public void testFindById() {
		assertNotNull( viewerDao.findById( new Long( 1344 ) ) );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testFindByIdFail() {
		viewerDao.findById( null );

	}

	@Test
	public void testFetchById() {
		Viewer viewer = viewerDao.fetchById( new Long( 1344 ) );
		inspectViewer( viewer );
	}

	private void inspectViewer( Viewer view ) {
		InspectViewerVisitor inspectViewer = new InspectViewerVisitor();
		view.accept( inspectViewer );
	}

	class InspectViewerVisitor implements ViewerVisitor {

		@Override
		public void visitInputText( InputText viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitInputTemporal( InputTemporal viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitTextArea( TextArea viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitCombo( Combo viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitSuggestion( Suggestion viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitInputList( InputList viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitFileUpload( FileUpload viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitLabel( Label viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitOutputType( OutputType viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitOutputValue( OutputValue viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitOutputMeasurementUnit( OutputMeasurementUnit viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitOutputPath( OutputPath viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitOutputList( OutputList viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitOutputField( OutputField viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitOutputImage( OutputImage viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitOutputLink( OutputLink viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitGrid( Grid viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitFactPanel( FactPanel viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitBox( Box viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitConditionalPanel( ConditionalPanel viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitTabbedPanel( TabbedPanel viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitReport( Report viewer ) {
			basicInspect( viewer );
		}

		@Override
		public void visitViewerCustom( ViewerCustom viewer ) {
			basicInspect( viewer );
		}

		private void basicInspect( Viewer viewer ) {
			viewer.listChildren();

			for ( ViewerLink link : viewer.listChildren() ) {
				if ( ClassHelper.instanceOf( link, SubViewer.class ) ) {
					SubViewer subViewer = ClassHelper.cast( link, SubViewer.class );

					TypeSelector selector = subViewer.getSelector();
					while ( selector != null ) {
						selector = selector.getNext();
					}
				}

				link.getSource();
				if ( link.getTarget() != null ) {
					link.getTarget().accept( this );
				}
			}

		}

		@Override
		public void visitParagraph( Paragraph viewer) {
			basicInspect( viewer );
		}
	}
}
