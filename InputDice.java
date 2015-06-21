package monopoly;

/**
 * Created by fjricci on 4/7/2015.
 * Player-determined dice rolls.
 */
public class InputDice implements Dice {
	private final int N;
	private final int SIDES;
	private final Input input;

	public InputDice(Input input) {
		N = 2;
		SIDES = 6;
		this.input = input;
	}

	//return number of dice
	public int numDice() {
		return N;
	}

	//return sides per die
	public int sides() {
		return SIDES;
	}

	public Roll roll() {
		System.out.println("Please enter a manual dice roll, one die per line.");
		while (true) {
			Roll roll = input.inputRoll();
			if (roll.val < N || roll.val > N * SIDES)
				System.out.println("Please enter a valid dice roll.");
			else
				return roll;
		}
	}
}
