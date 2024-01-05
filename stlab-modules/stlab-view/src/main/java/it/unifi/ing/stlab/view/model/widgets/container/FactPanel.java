package it.unifi.ing.stlab.view.model.widgets.container;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.factquery.model.FactQuery;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerContainer;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("FPANEL")
public class FactPanel extends ViewerContainer {

	private FactQuery query;
	private List<Fact> factRoots;
	
	public FactPanel(String uuid) {
		super( uuid );
	}
	
	protected FactPanel() {
		super();
	}
	
	@Override
	public boolean isValidSubViewer(ViewerLink sv) {

        return sv != null && ClassHelper.instanceOf(sv, SubViewer.class);
    }
	
	@Transient
	@Override
	public String getXhtml() {
		return "../component/factpanel.xhtml";
	}

	@Override
	public void accept(ViewerVisitor v) {
		v.visitFactPanel(this);
	}

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name="fact_query_id" )
	public FactQuery getQuery() {
		return query;
	}
	public void setQuery(FactQuery query) {
		this.query = query;
	}
	
	@Transient
	public List<Fact> getFactRoots() {
		return factRoots;
	}
	public void setFactRoots(List<Fact> factRoots) {
		this.factRoots= factRoots;
	}
	public void addFactRoot(Fact fact) {
		factRoots.add( fact );
	}

}
