package monopoly;

import java.util.Scanner;

/**
 * Created by fjricci on 4/7/2015.
 */
public class Input {
    public static boolean inputBool(Scanner scanner){
        return Input.inputDecision(scanner, new String[]{"Yes", "No"}) == 0;
    }

    public static int inputDecision(Scanner scanner, String[] choices) {
        while (true) {
            String input = scanner.nextLine();
            for (int i = 0; i < choices.length; i++) {
                if (input.equalsIgnoreCase(choices[i]))
                    return i;
            }
            System.out.println("Please enter a valid decision.");
        }
    }

    public static Dice.Roll inputRoll(Scanner scanner) {
        Dice.Roll roll = new Dice.Roll();

        while (true) {
            try {
                roll.val = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
                continue;
            }
            break;
        }

        while (true) {
            int second_val;
            try {
                second_val = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
                continue;
            }

            roll.is_double = second_val == roll.val;
            roll.val += second_val;

            return roll;
        }
    }
}
