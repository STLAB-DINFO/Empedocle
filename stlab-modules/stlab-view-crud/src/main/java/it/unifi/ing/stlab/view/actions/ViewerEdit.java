package it.unifi.ing.stlab.view.actions;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.viewers.SuggestionInterface;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.dao.types.UnitDao;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.view.actions.visitor.IsViewerLeafVisitor;
import it.unifi.ing.stlab.view.actions.wrappers.ViewerBean;
import it.unifi.ing.stlab.view.actions.wrappers.ViewerTreeNode;
import it.unifi.ing.stlab.view.factory.ViewerFactory;
import it.unifi.ing.stlab.view.factory.ViewerLinkFactory;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.ViewerLink;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.UserTransaction;

import org.richfaces.event.DropEvent;

@Named
@Stateful
@ConversationScoped
@TransactionManagement(TransactionManagementType.BEAN)
public class ViewerEdit extends ViewerController {

	// FIELDS
	
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	@EJB
	private TypeDao typeDao;

	private SuggestionInterface typeSuggestion;

	// METHODS
	
	public SuggestionInterface getTypeSuggestion() {
		if (typeSuggestion == null) {
			typeSuggestion = new TypeSuggestion();
		}
		return typeSuggestion;
	}

	public class TypeSuggestion implements SuggestionInterface {

		@Override
		public List<SelectItem> autocomplete(String suggestion) {
			List<SelectItem> result = new ArrayList<SelectItem>();
			for (Type t : typeDao.findBySuggestion(suggestion, 10)) {
				result.add(new SelectItem(t.getUuid(), t.getName()));
			}

			return result;
		}
	};

	// =============================================================
	// OLD STUFF
	// =============================================================

	// FIXME una volta finito questo bean, generare UID
	private static final long serialVersionUID = 0L;

	//
	// CDI injections
	//

	@Inject
	private Conversation conversation;

	@Inject
	@HttpParam("viewerId")
	private String viewerId;

	@Inject
	@HttpParam("cloneViewer")
	private String cloneViewer;

	//
	// EJB injections
	//

	@Resource
	private UserTransaction utx;

	@EJB
	private UnitDao unitDao;

	//
	// Local attributes
	//

	private ViewerBean current;

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public Boolean isUsed() {
		return false;
	}
	
	//FIXME cancellare
	private Type link;
	public Type getLink() {
		return link;
	}
	public void setLink(Type link) {
		System.out.println( link.getName() );
		this.link = link;
	}
	
	// fino a qui

	@PostConstruct
	public void init() {
		Viewer source = null;

		// edit
		if (viewerId != null) {
			source = entityManager.find(Viewer.class, Long.parseLong(viewerId));

			if ("true".equals(cloneViewer)) {
				// TODO cloneViewer
				// il codice sottostante è preso da cloneType
				// TypeCopyVisitor v = new TypeCopyVisitor();
				// source.accept(v);
				// getEntityManager().persist(v.getResult());
				// current = new TypeBean(getEntityManager(), v.getResult());

			} else {
				current = new ViewerBean(source);

			}

		} else {
			source = createViewer("grid");
			source.setAnonymous(false);
			source.setReadOnly(false);
			getEntityManager().persist(source);
			current = new ViewerBean(createViewer("grid"));

		}
	}

	public void addNodeListener(DropEvent event) {
		ViewerTreeNode parentNode = findNode((String) event.getDropValue());
		Viewer parentElement = extractViewer(parentNode);

		Viewer newElement = createViewer((String) event.getDragValue());
		if (newElement != null) {
			newElement.setAnonymous(true);
			// entityManager.persist(newElement); in TypeEdit fa anche questo
		}

		// FIXME se creo un TabbedPanel, devo usare factory.createTab()
		ViewerLink newLink = ViewerLinkFactory.createSubViewer();

		/*
		 * in TypeEdit fa anche questo nextLink++; newLink.setName( "nuovo" +
		 * nextLink ); newLink.setPriority( new Long(
		 * parentType.listChildren().size() + 1 ) ); //TODO SARA serve la
		 * priorita' nei ViewerLinks?
		 */
		newLink.assignSource(parentElement);
		newLink.assignTarget(newElement);

		IsViewerLeafVisitor v = new IsViewerLeafVisitor();
		newElement.accept(v);

		// boolean leaf = ( newType == null || !ClassHelper.instanceOf( newType,
		// CompositeType.class )); in TypeEdit fa anche questo

		ViewerTreeNode newNode = new ViewerTreeNode(v.isLeaf(), newLink);
		parentNode.addChild(newLink.getUuid(), newNode);
		// entityManager.persist(newLink); in TypeEdit fa anche questo
	}

	public void moveNodeListener(DropEvent event) {
		ViewerTreeNode source = findNode((String) event.getDragValue());
		ViewerTreeNode target = findNode((String) event.getDropValue());

		if (source.deepContains(target) || target.deepContains(source))
			return;

		ViewerTreeNode sourceParent = current.getTreeRoot().parentOf(source);
		ViewerTreeNode targetParent = current.getTreeRoot().parentOf(target);

		sourceParent.removeChild(source.getLink().getUuid());
		sourceParent.adjustChildrenPriority();

		targetParent.insertAfter(target, source);
		targetParent.adjustChildrenPriority();

		source.getLink().assignSource(extractViewer(targetParent));
	}

	public void deleteNode(ViewerTreeNode node) {
		if (node == null || node.getLink() == null)
			return;

		ViewerTreeNode parent = current.getTreeRoot().parentOf(node);
		if (parent != null) {

			parent.removeChild(node.getLink().getUuid());

			ViewerLink link = node.getLink();
			Viewer v = link.getTarget();

			link.delete();
			if (v != null && v.getAnonymous()) {
				v.delete();
			}

			setSelectedLink(null);

			GarbageCollector.getInstance().flush(
					new JpaGarbageAction(entityManager));
		}

	}

	public void deleteCurrentNode() {
		if (currentSelectedNode == null
				|| currentSelectedNode.getLink() == null)
			return;

		ViewerTreeNode parent = current.getTreeRoot().parentOf(
				currentSelectedNode);
		if (parent != null) {

			parent.removeChild(currentSelectedNode.getLink().getUuid());

			ViewerLink link = currentSelectedNode.getLink();
			Viewer viewer = link.getTarget();

			link.delete();
			if (viewer != null /* && viewer.getAnonymous() */) { // TODO:
																	// scoprire
																	// perche'
																	// anonymous
																	// e' a null
																	// e
																	// ripristinare
				viewer.delete();
			}

			closeSelection();

			GarbageCollector.getInstance().flush(
					new JpaGarbageAction(entityManager));
		}

	}

	public String cancel() {
		conversation.end();
		return "cancel";
	}

	public String save() {
		try {
			conversation.end();

			utx.begin();
			utx.commit();

			return "save";
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public ViewerBean getCurrent() {
		return current;

	}

	public String getViewerId() {
		return viewerId;
	}

	public void closeSelection() {
		// syncCurrentDefaultValue();
		setSelectedLink(null);
		// setReferencedType(null);
		// setCurrentDefaultValue(null);

	}

	public String component(ViewerLink link) {
		if (link == null || link.getTarget() == null)
			return "";
		return link.getTarget().getClass().getSimpleName();
	}

	//
	// Private Methods
	//

	private Viewer createViewer(String command) {
		// containers
		if ("box".equals(command)) {
			return ViewerFactory.createBox();
		} else if ("conditional-panel".equals(command)) {
			return ViewerFactory.createConditionalPanel();
		} else if ("grid".equals(command)) {
			return ViewerFactory.createGrid();
		} else if ("report".equals(command)) {
			return ViewerFactory.createReport();
		} else if ("tabbed-panel".equals(command)) {
			return ViewerFactory.createTabbedPanel();
		}
		// input
		else if ("combo".equals(command)) {
			return ViewerFactory.createCombo();
		} else if ("input-list".equals(command)) {
			return ViewerFactory.createInputList();
		} else if ("suggestion".equals(command)) {
			return ViewerFactory.createSuggestion();
		} else if ("text-area".equals(command)) {
			return ViewerFactory.createTextArea();
		} else if ("file-upload".equals(command)) {
			return ViewerFactory.createFileUpload();
		}
		// output
		else if ("label".equals(command)) {
			return ViewerFactory.createLabel();
		} else if ("output-field".equals(command)) {
			return ViewerFactory.createOutputField();
		} else if ("output-image".equals(command)) {
			return ViewerFactory.createOutputImage();
		} else if ("output-list".equals(command)) {
			return ViewerFactory.createOutputList();
		} else if ("output-path".equals(command)) {
			return ViewerFactory.createOutputPath();
		} else if ("output-type".equals(command)) {
			return ViewerFactory.createOutputType();
		} else if ("output-value".equals(command)) {
			return ViewerFactory.createOutputValue();
		} else {
			throw new IllegalArgumentException(
					"The requested Viewer is not valid");
		}
	}

	private ViewerTreeNode findNode(String uuid) {
		ViewerTreeNode result = current.getTreeRoot().find(uuid);
		if (current.getTreeRoot().equals(result)) {
			result = (ViewerTreeNode) result.getChild("root");
		}
		return result;
	}

	private Viewer extractViewer(ViewerTreeNode node) {
		if (node.getLink() == null) {
			return current.getViewer();
		} else {
			return node.getLink().getTarget();
		}
	}

}
