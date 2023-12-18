package it.unifi.ing.stlab.entities.implementation.timed;

import it.unifi.ing.stlab.entities.model.timed.Time;
import it.unifi.ing.stlab.entities.model.timed.TimeRange;
import it.unifi.ing.stlab.entities.model.timed.TimedEntity;

public class TimedEntityImpl<R extends TimeRange<H>, H extends Time> 
	implements TimedEntity<R,H> {

	private R timeRange;
	
	public TimedEntityImpl(R timeRange) {
		super();
		this.timeRange = timeRange;
	}

	public TimedEntityImpl() {
		super();
	}

	@Override
	public R getTimeRange() {
		return timeRange;
	}
	public void setTimeRange(R timeRange) {
		this.timeRange = timeRange;
	}

	@Override
	public boolean isValidReference(TimedEntity<?,?> timedEntity) {
		if ( timeRange == null || timedEntity.getTimeRange() == null ) return false;

		return timedEntity.getTimeRange().contains( timeRange );
	}

	@Override
	public boolean isValidAt(H time) {
		if ( timeRange == null ) return true;
		
		return timeRange.contains( time );
	}

}
