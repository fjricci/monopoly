package monopoly;

import java.util.Scanner;

/**
 * Created by fjricci on 4/7/2015.
 */
public class Input {
    private Scanner scanner;

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
                if (input.equalsIgnoreCase(choices[i]))
                    return i;
            }
            System.out.println("Please enter a valid decision.");
        }
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
