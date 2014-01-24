package game;

import java.util.concurrent.Semaphore;

/**
 * Provides a timing service for the game. 
 * 
 * @author littlewoo
 */
public class Timer {
	private long timeBetweenTicks;
	private boolean tickReady;
	private Object lock = new Object();
	
	/**
	 * Build a new timer.
	 * 
	 */
	public Timer()
	{
		timeBetweenTicks = 600;
		tickReady = false;
		tick();
	}
	
	/**
	 * This method maintains the Timer's internal timer, by setting the 
	 * tickReady flag when a tick is ready.
	 */
	public void tick()
	{
		
		Thread t = new Thread() {
		
			public void run() {
				while (true) {
					try {
						sleep(timeBetweenTicks);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tickReady = true;
			        synchronized (lock) {
			        	lock.notify();
			        }
				}
			}
		};
		t.start();
	}
	
	/**
	 * Await next tick. This method waits until the next tick is due, then
	 * returns. If the time has already passed, ticks straight away.
	 * 
	 */
	public void awaitNextTick()
	{
		synchronized (lock) {
			while (!tickReady) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			tickReady = false;
		}
	}
	
}
