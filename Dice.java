package monopoly;

/**
 * Created by fjricci on 4/7/2015.
 * Interface for dice
 */
public interface Dice {

	//return number of dice
	int numDice();

	//return sides per die
	int sides();

	Roll roll();

	class Roll {
		public int val;
		public boolean is_double;
	}
}
