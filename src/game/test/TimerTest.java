package game.test;

import game.Timer;

public class TimerTest {
	public static void main(String[] args) {
		Timer t = new Timer();
		long start = System.currentTimeMillis();
		long[] ticks = new long[10];
		long end;
		for (int i=0; i<10; i++) {
			ticks[i] = System.currentTimeMillis();
			t.awaitNextTick();
		}
		end = System.currentTimeMillis();
		System.out.println("Start: " + start + " : 0");
		System.out.println("Ticks:");
		for (int i=0; i<10; i++) {
			System.out.println("  " + i + ": " + ticks[i] + " : " + (ticks[i] - start));
		}
		System.out.println("End: " + end + " : " + (end - start));
	}
}
