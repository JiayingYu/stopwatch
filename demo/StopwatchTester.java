package stopwatch.demo;

import java.util.concurrent.TimeUnit;

import stopwatch.api.IStopwatch;
import stopwatch.impl.StopwatchFactory;

public class StopwatchTester {
	public static void main(String[] args) throws InterruptedException {
		IStopwatch stopwatch = StopwatchFactory.getStopwatch("testWatch");
		stopwatch.start();
		for (int i = 0; i < 5; i++) {
			TimeUnit.MILLISECONDS.sleep(100);
			stopwatch.lap();
		}
		TimeUnit.MILLISECONDS.sleep(100);
		stopwatch.stop();
		
		System.out.println(stopwatch.getLapTimes());
	}
}
