package game.test;

import game.Timer;

public class TimerTest {
	public static void main(String[] args) {
		Timer t = new Timer();
		long start = System.currentTimeMillis();
		System.out.println("Start: " + start + " : 0");
		long[] ticks = new long[10];
		long end;
		System.out.println("Ticks:");
		for (int i=0; i<10; i++) {
			ticks[i] = System.currentTimeMillis();
			System.out.println("  " + i + ": " + ticks[i] + " : " + (ticks[i] - start));
			t.awaitNextTick();
		}
		end = System.currentTimeMillis();
		System.out.println("End: " + end + " : " + (end - start));
	}
}
