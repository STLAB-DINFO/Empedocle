package it.unifi.ing.stlab.entities.model.timed;

/**
 * Interface that defines the temporal validity of an entity.
 * @see it.unifi.ing.stlab.entities.implementation.timed.TimedEntityImpl
 *
 * @param <R> generic which extends TimeRange<H>, containing two time points
 * @param <H> generic which extends Time
 */
public interface TimedEntity<R extends TimeRange<H>, H extends Time> {

	/**
	 * Method that returns the time range during which the entity is considered valid
	 * @return R which extends TimeRange<H>
	 */
	public R getTimeRange(); 
	
	/**
	 * Method that checks if the validity yime range of the TimedEntity
	 * passed as a parameter encompasses that of the entity on which the method it is invoked.
	 * @param timedEntity another TimedEntity
	 * @return true if it is contained, false otherwise
	 */
	public boolean isValidReference( TimedEntity<?,?> timedEntity );
	
	/**
	 * Method that indicated whether this entity is valid at the time passed as a parameted
	 * @param time
	 * @return true if timeRange contains time.
	 */
	public boolean isValidAt( H time );

}
