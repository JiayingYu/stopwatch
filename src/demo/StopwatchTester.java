package demo;

import java.util.Random;
import java.util.concurrent.*;

import api.IStopwatch;
import impl.StopwatchFactory;

public class StopwatchTester {
	public static void test() {
		final IStopwatch watch = StopwatchFactory.getStopwatch("test");
		ExecutorService exc = Executors.newCachedThreadPool();
		watch.start();
		for (int i = 0; i < 5; i++) {
			exc.execute(new Runnable() {
				public void run() {
					try {
						Random rand = new Random();
						TimeUnit.MILLISECONDS.sleep(rand.nextInt(300));
						watch.lap();
					} catch (InterruptedException e) {
						System.out.println("Sleep interrupted.");
					}		
				}
			});
		}
		
		try {
			TimeUnit.MILLISECONDS.sleep(600);
			watch.stop();
			System.out.println(watch.getLapTimes());
			
			TimeUnit.MILLISECONDS.sleep(100);
			watch.start();
			TimeUnit.MILLISECONDS.sleep(100);
			watch.stop();
			System.out.println(watch.getLapTimes());
			
			watch.reset();
			System.out.println(watch.getLapTimes());
		} catch (InterruptedException e) {
			System.out.println("Sleep interrupted.");
		} finally {
			exc.shutdownNow();
		}
	}
	
	public static void main(String[] args) {
		test();
	}
}
