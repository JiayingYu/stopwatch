package stopwatch.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import stopwatch.api.IStopwatch;

public class StopWatchImpl implements IStopwatch {
	private final String id;
	private long startTime;
	private List<Long> lapTimeList = Collections
			.synchronizedList(new ArrayList<Long>());
	private boolean isRunning = false;
	private Object lock = new Object();

	StopWatchImpl(String id) {
		if (id.isEmpty() || id == null) {
      throw new IllegalArgumentException("Error: id is empty or null!");
    }
		this.id = id;
	}

	public static IStopwatch newStopWatch(String id) {
		return new StopWatchImpl(id);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void start() {
		synchronized (lock) {
			if (isRunning)
				throw new IllegalStateException("Stopwatch already started!");
			
			startTime = System.currentTimeMillis();
			isRunning = true;
			
		}
	}

	@Override
	public void lap() {
		long stopTime;
		long lapTime;
		synchronized (lock) {
			if (!isRunning) {
				throw new IllegalStateException("Stopwath is not running!");
			}
			stopTime = System.currentTimeMillis();
			lapTime = stopTime - startTime;
			startTime = stopTime;	
		}
		lapTimeList.add(lapTime);
	}

	@Override
	public void stop() {
		long lapTime;
		synchronized(lock) {
			if (!isRunning) {
				throw new IllegalStateException("Stopwath is already stopped!");
			}
			lapTime = System.currentTimeMillis() - startTime;
			isRunning = false;		
		}
		lapTimeList.add(lapTime);
	}

	@Override
	public void reset() {
		synchronized(lock) {
			isRunning = false;
		}
		lapTimeList.clear();
	}

	@Override
	public List<Long> getLapTimes() {
		synchronized(lock) {
			return Collections.unmodifiableList(lapTimeList);
		}
	}
	
	@Override
	public int hashCode() {
		int res = 17;
		res = res * 31 + id.hashCode();
		return res;
	}
	
	@Override
	public String toString() {
		String s = "ID: " + id + "\n";
		s += lapTimeList.toString();
		return s;
	}
}
