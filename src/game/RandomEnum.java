package game;

import java.util.Random;

public class RandomEnum <T extends Enum<T>> {
	private Random rand;
	private T[] values;
	
	/**
	 * Create a new enum randomizer
	 */
	public RandomEnum(Class<T> cls)
	{
		this(cls, System.currentTimeMillis());
	}
	
	/**
	 * Create a new enum randomizer with a given seed
	 */
	public RandomEnum(Class<T> cls, long seed) {
		rand = new Random(seed);
		values = cls.getEnumConstants();
	}
	
	/**
	 * Get a new random value	
	 */
	public T getRandomValue()
	{
		int n = values.length;
		return values[rand.nextInt(n)];
	}
}
