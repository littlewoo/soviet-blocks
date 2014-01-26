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
	
	/**
	 * Wait a fixed amount of time.
	 * 
	 * @param time the amount of time to wait, in milliseconds.
	 */
	public void await(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace(); // Not a real solution. Don't know if this matters.
		}
	}
	
	/**
	 * Level up: reduce the amount of time between ticks by 10%
	 */
	public void levelUp() {
		timeBetweenTicks = (long) (timeBetweenTicks * 0.9);
	}
}
