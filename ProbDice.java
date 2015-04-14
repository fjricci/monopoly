package monopoly;

import java.util.Random;

public class ProbDice implements Dice {
    private final int N; //number of dice
    private final int SIDES; //number of sides per die
    private Random rand;

    //single six-sided die
    public ProbDice() {
        N = 2;
        SIDES = 6;
        rand = new Random();
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
        Roll roll = new Roll();

        int randA = rand.nextInt(6) + 1;

        int randB = rand.nextInt(6) + 1;
        roll.is_double = randA == randB;

        roll.val = randA + randB;

        return roll;
    }
}