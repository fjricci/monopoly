package monopoly;

public class ProbDice implements Dice {
    private final int N; //number of dice
    private final int SIDES; //number of sides per die

    //single six-sided die
    public ProbDice() {
        N = 2;
        SIDES = 6;
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

        roll.val = (int) (Math.random() * SIDES);

        int randInt = (int) (Math.random() * SIDES);
        roll.is_double = randInt == roll.val;

        roll.val += randInt;

        return roll;
    }
}