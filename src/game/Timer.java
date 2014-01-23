package game;

/**
 * Provides a timing service for the game. 
 * 
 * @author littlewoo
 */
public class Timer {
	private long timeOfNextTick;
	private long timeBetweenTicks;
	
	/**
	 * Build a new timer.
	 * 
	 */
	public Timer()
	{
		timeOfNextTick = System.currentTimeMillis();
		timeBetweenTicks = 600;
	}
	
	/**
	 * Await next tick. This method waits until the next tick is due, then
	 * returns. If the time has already passed, ticks straight away.
	 * 
	 */
	public void awaitNextTick()
	{
		while (true) { 
			long time = System.currentTimeMillis();
			if (time > timeOfNextTick) {
				timeOfNextTick += timeBetweenTicks;
				return;
			}
		}
	}
	
}
