package it.unifi.ing.stlab.commons.random;

import java.util.Random;

public class RandomGenerator {

	private final Random random;
	
	public RandomGenerator() {
		random = new Random();
	}
	
	public int random( int min, int max ) {
		if(max == 0)
			max = random.nextInt(5)+1;
		int delta = max - min + 1;
		return min + random.nextInt( delta );
	}
}
