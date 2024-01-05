package it.unifi.ing.stlab.test;

public class TimeTracker {

	private long calls;
	private long duration;
	private long start;
	private long stop;
	private final String method;
	
	public TimeTracker(String method) {
		super();
		this.method = method;
	}

	public void start() {
		start = System.currentTimeMillis();
	}
	
	public void stop() {
		stop = System.currentTimeMillis();

		duration += stop - start;
		calls++;
	}
	
	public void printResult() {
		
		if ( calls > 1 ) {
			System.out.println( "[" + method + "] time spent (" + calls + " calls) : " + duration + " millis " );
		} else {
			System.out.println( "[" + method + "] time spent: " + duration + " millis " );
		}
	}

	public long getCalls() {
		return calls;
	}
	public long getDuration() {
		return duration;
	}
	public String getMethod() {
		return method;
	}
}
