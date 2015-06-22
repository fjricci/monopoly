package monopoly;

import java.util.Scanner;

/**
 * Created by fjricci on 4/7/2015.
 * Gather player inputs
 */
class Input {
	private final Scanner scanner;

	public Input() {
		scanner = new Scanner(System.in);
	}

	public String inputString() {
		return scanner.nextLine();
	}

	public boolean inputBool() {
		return inputDecision(new String[]{"Yes", "No"}) == 0;
	}

	public int inputInt() {
		while (true) {
			int val;
			try {
				val = Integer.parseInt(inputString());
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid integer.");
				continue;
			}
			return val;
		}
	}

	public int inputDecision(String[] choices) {
		while (true) {
			String input = inputString();
			for (int i = 0; i < choices.length; i++) {
				if (input.equalsIgnoreCase(choices[i]) || input.equalsIgnoreCase(choices[i].substring(0, 1)))
					return i;
			}
			System.out.println("Please enter a valid decision.");
		}
	}

	public Player inputPlayer(Iterable<Player> players, Player notAllowed) {
		Player player = null;
		do {
			String name = inputString();
			for (Player p : players) {
				if (name.equals(p.name()))
					player = p;
			}
			if (player == null)
				System.out.println("Invalid player, please enter another name.");

			else if (notAllowed != null && player.name().equals(notAllowed.name())) {
				System.out.println("You may not select this player. Choose another.");
				player = null;
			}
		} while (player == null);

		return player;
	}

	public Dice.Roll inputRoll() {
		Dice.Roll roll = new Dice.Roll();

		while (true) {
			try {
				roll.val = Integer.parseInt(inputString());
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid integer.");
				continue;
			}

			if (roll.val < 1 || roll.val > 6)
				System.out.println("Please enter a valid die value.");
			else
				break;
		}

		while (true) {
			int second_val;
			try {
				second_val = Integer.parseInt(inputString());
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid integer.");
				continue;
			}

			if (second_val < 1 || second_val > 6) {
				System.out.println("Please enter a valid die value.");
				continue;
			}

			roll.is_double = (second_val == roll.val);
			roll.val += second_val;

			return roll;
		}
	}
}
