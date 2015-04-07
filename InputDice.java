package monopoly;

import java.util.Scanner;

/**
 * Created by fjricci on 4/7/2015.
 */
public class InputDice implements Dice {
    private final int N;
    private final int SIDES;
    private Scanner scanner;

    public InputDice(Scanner scanner) {
        N = 2;
        SIDES = 6;
        this.scanner = scanner;
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
            Roll roll = Input.inputRoll(scanner);
            if (roll.val < N || roll.val > N * SIDES)
                System.out.println("Please enter a valid dice roll.");
            else
                return roll;
        }
    }
}
