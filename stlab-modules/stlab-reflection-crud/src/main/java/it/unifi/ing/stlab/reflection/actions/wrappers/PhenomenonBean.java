package it.unifi.ing.stlab.reflection.actions.wrappers;

import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import java.util.Date;

public class PhenomenonBean {

	private Phenomenon phenomenon;

	public PhenomenonBean() {
		super();
	}

	public PhenomenonBean(Phenomenon phenomenon) {
		if ( phenomenon == null ) throw new IllegalArgumentException( "Phenomeno is null" );
		
		this.phenomenon = phenomenon;
	}

	public Phenomenon getPhenomenon() {
		return phenomenon;
	}
	
	public String getName() {
		return phenomenon.getName();
	}

	public void setName(String name) {
		phenomenon.setName(name);
	}

	public Date getLeft() {
		if ( phenomenon.getTimeRange() == null || 
			 phenomenon.getTimeRange().getLeft() == null ) return null; 
			
		return phenomenon.getTimeRange().getLeft().getDate();
	}

	public void setLeft(Date left) {
		Time right = null;
		if ( phenomenon.getTimeRange() == null ) {
			right = new Time( null );
		} else {
			right = phenomenon.getTimeRange().getRight();
		}

		phenomenon.setTimeRange( new TimeRange( new Time( left ), right ));
	}

	public Date getRight() {
		if ( phenomenon.getTimeRange() == null || 
			 phenomenon.getTimeRange().getRight() == null ) return null; 
				
			return phenomenon.getTimeRange().getRight().getDate();
	}

	public void setRight(Date right) {
		Time left = null;
		if ( phenomenon.getTimeRange() == null ) {
			left = new Time( null );
		} else {
			left = phenomenon.getTimeRange().getLeft();
		}

		phenomenon.setTimeRange( new TimeRange( left, new Time( right )));
	}

}
