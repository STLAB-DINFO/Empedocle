package it.unifi.ing.stlab.empedocle.actions.util.time;

import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import java.util.Calendar;


public class TimeUtils {
	
	public static it.unifi.ing.stlab.users.model.time.Time now() {
		return new it.unifi.ing.stlab.users.model.time.Time(Calendar.getInstance().getTime());
	}
	
	public static TimeRange timeRange(Time t1, Time t2) {
		return new it.unifi.ing.stlab.users.model.time.TimeRange(t1, t2);
	}
	
	public static Time infinity() {
		return new it.unifi.ing.stlab.users.model.time.Time(null);
	}
	
	public static Time getBeginning(TimeRange timeRange) {
		if(timeRange == null || timeRange.getLeft() == null) {
			return infinity();
		}
		
		return timeRange.getLeft();
	}
	
	public static Time getEnd(TimeRange timeRange) {
		if(timeRange == null || timeRange.getRight() == null) {
			return infinity();
		}
		
		return timeRange.getRight();
	}

}
