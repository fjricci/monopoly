package monopoly;

import java.util.LinkedList;
import java.util.Queue;

public class ValueEstimator {
	private final int NUM_PLAYERS;
	private final int DECK_SIZE = 16;
	private Queue<Player> players;  //contains all player positions
	private Board board;  //stores a board
    private ProbDice probDice;   //stores a pair of six-sided probDice
    private Cards chance; //stores a deck of chance cards
    private double[] doubleProb;
	private double[] singleProb;

	//constructs ValueEstimator object
	public ValueEstimator(Board board, Queue<Player> queue, ProbDice probDice, Cards chance) {
	    players = queue;

		//iterate through queue of players, enqueue position and enum
        this.board = board;
        this.probDice = probDice;
        this.chance = chance;
        if (probDice.numDice() == 2)
            setDouble();

	    this.NUM_PLAYERS = players.size();
	}

    public static void main(String[] args) {
	    Deck chanceDeck = new RandomDeck();
	    Deck commDeck = new RandomDeck();
	    Board board = new Board(chanceDeck, commDeck, false);
	    ProbDice probDice = new ProbDice();
        Queue<Player> queue = new LinkedList<>();
	    Cards chance = (Cards) board.square(7);

        Player player1 = new Player(Player.PlayerType.PLAYER_B, "John");
        Player player2 = new Player(Player.PlayerType.PLAYER_C, "Jim");
        Player player3 = new Player(Player.PlayerType.PLAYER_D, "James");
        Player player4 = new Player(Player.PlayerType.PLAYER_E, "Jacob");

        queue.add(player1);
        queue.add(player2);
	    queue.add(player3);
	    queue.add(player4);

	    ValueEstimator value = new ValueEstimator(board, queue, probDice, chance);

	    double[] probs = value.probLanding(0);

        for (double prob : probs)
	        System.out.println(prob);

	    double totProb = 0;
	    for (int i = 0; i < 41; i++) {
		    double prob = value.getProb(i, 0);
		    System.out.println(i + ") " + prob * 100);
		    totProb += prob;
	    }
	    System.out.println("Tot prob: " + totProb);
    }

	public double expectedValue(int squarePos, int val) {
		Player p;
		double[] probs = probLanding(squarePos);

		double tot = 0;
		for (int i = 0; i < probs.length; i++)
			tot += probs[i] * val * i;

		return tot;
	}

	//return the probability of a given number of players landing
    //on a given property
    public double[] probLanding(int squarePos)
    {
        //first array dimension is the square position
        //second array dimension is the number of players landing on square
	    double[] probs = new double[NUM_PLAYERS + 1];
	    double[] totProbs = new double[NUM_PLAYERS + 1];

	    int num = 0;
	    for (Player p : players)
		    probs[num++] = getProb(squarePos, p.position());

	    totProbs[0] = 1;
	    for (double prob : probs)
		    totProbs[0] *= (1 - prob);

	    for (int i = 0; i < probs.length; i++) {
		    double prod = probs[i];
		    for (int j = 0; j < probs.length; j++) {
			    if (i != j)
				    prod *= (1 - probs[j]);
		    }
		    totProbs[1] += prod;
	    }

	    if (NUM_PLAYERS == 1)
		    return totProbs;

	    for (int i = 0; i < probs.length; i++) {
		    for (int j = i + 1; j < probs.length; j++) {
			    double prod = probs[i] * probs[j];
			    for (int k = 0; k < probs.length; k++) {
				    if (i != k && j != k)
					    prod *= (1 - probs[k]);
			    }
			    totProbs[2] += prod;
		    }
	    }

	    if (NUM_PLAYERS == 2)
		    return totProbs;

	    //assume that p[4+] is negligible.
	    totProbs[3] = 1 - totProbs[0] - totProbs[1] - totProbs[2];
	    return totProbs;
    }
    
    //probability of getting from playerPos to squarePos
    private double getProb(int squarePos, int playerPos)
    {
	    Square square = board.square(squarePos);

        if (squarePos < playerPos)
	        squarePos += board.size(); //accounts for passing go
	    int dist = squarePos - playerPos;

	    double prob = 0;
	    if (square instanceof Cards)
		    return cardProb(dist, square);
	    else if (squarePos < 40)
		    prob = directProb(dist);

        prob += indirectProb(squarePos, playerPos, square);
        return prob;
    }

	private double cardProb(int dist, Square square) {
		Cards cards = (Cards) square;
		if (cards.type() == Card.CardType.CHANCE)
			return directProb(dist) * (6.0 / 16);
		else
			return directProb(dist) * (14.0 / 16);
	}

    //probability of landing directly on property by probDice roll
    private double directProb(int dist)
    {
	    return doubleProb(dist);
	    //return singleProb(dist);
    }
    
    //probability of landing on property by chance card
    private double indirectProb(int squarePos, int playerPos, Square square)
    {
	    return chanceProb(squarePos, playerPos, square) + communityProb(squarePos, playerPos, square);
    }

	private double communityProb(int squarePos, int playerPos, Square square) {
		double prob = 0.0;
		int comPosA = 2;
		int comPosB = 17;
		int comPosC = 33;

		if (squarePos != 0 && squarePos != 40)
			return 0;

		if (comPosA < playerPos)
			comPosA += board.size(); //accounts for passing go
		if (comPosB < playerPos)
			comPosB += board.size(); //accounts for passing go
		if (comPosC < playerPos)
			comPosC += board.size(); //accounts for passing go

		int distA = comPosA - playerPos;
		int distB = comPosB - playerPos;
		int distC = comPosC - playerPos;
		double probChanceA = directProb(distA);
		double probChanceB = directProb(distB);
		double probChanceC = directProb(distC);

		double probChance = probChanceA + probChanceB + probChanceC;

		return probChance / DECK_SIZE;
	}

	private double chanceProb(int squarePos, int playerPos, Square square) {
		//TODO Account for rolls remaining after chance if doubles
		double prob = 0.0;
		int chancePosA = 7;
		int chancePosB = 22;
		int chancePosC = 36;

		if (chancePosA < playerPos)
			chancePosA += board.size(); //accounts for passing go
		if (chancePosB < playerPos)
			chancePosB += board.size(); //accounts for passing go
		if (chancePosC < playerPos)
			chancePosC += board.size(); //accounts for passing go

		int distA = chancePosA - playerPos;
		int distB = chancePosB - playerPos;
		int distC = chancePosC - playerPos;
		double probChanceA = directProb(distA);
		double probChanceB = directProb(distB);
		double probChanceC = directProb(distC);

		double probChance = probChanceA + probChanceB + probChanceC;

		int SIZE = DECK_SIZE;

		for (Card card : chance.cards()) {
			if (card.travelTo() == squarePos)
				prob += probChance / SIZE;
			else if (card.travel() == squarePos - chancePosA)
				prob += probChanceA / SIZE;
			else if (card.travel() == squarePos - chancePosB)
				prob += probChanceB / SIZE;
			else if (card.travel() == squarePos - chancePosC)
				prob += probChanceC / SIZE;
			prob += moveNearest(card, square, probChanceA, probChanceB, probChanceC) / SIZE;
		}

		return prob;
	}

	private double moveNearest(Card card, Square square, double probA, double probB, double probC) {
		if (card.action() != Card.CardAction.MOVE_NEAREST)
			return 0;

		if (square instanceof Railroad) {
			if (!card.travelRail())
				return 0;
			switch (square.position()) {
				case 5:
					return probC;
				case 15:
					return probA;
				case 25:
					return probB;
				case 35:
					return 0;
				default:
					throw new RuntimeException("Impossible railroad position.");
			}
		}

		if (square instanceof Utility) {
			if (card.travelRail())
				return 0;
			switch (square.position()) {
				case 12:
					return probA + probC;
				case 28:
					return probB;
				default:
					throw new RuntimeException("Impossible utility position.");
			}
		}

		return 0;
	}

    private void setDouble()
    {
	    final double D = 36.0;
	    final double T = 1296.0;
	    final int MAX = 36;

        int[] ways = new int[MAX];
        int[] doubleWays = new int[MAX];
        int[] tripleWays = new int[MAX];

        doubleProb = new double[MAX];
	    singleProb = new double[13];

        ways[2] = 1;
        ways[3] = 2;
        ways[4] = 3;
        ways[5] = 4;
        ways[6] = 5;
        ways[7] = 6;
        ways[8] = 5;
        ways[9] = 4;
        ways[10] = 3;
        ways[11] = 2;
        ways[12] = 1;

        doubleWays[4] = 1;
        doubleWays[5] = 2;
        doubleWays[6] = 4;
        doubleWays[7] = 6;
        doubleWays[8] = 9;
        doubleWays[9] = 12;
        doubleWays[10] = 14;
        doubleWays[11] = 16;
        doubleWays[12] = 17;
        doubleWays[13] = 18;
        doubleWays[14] = 18;
        doubleWays[15] = 18;
        doubleWays[16] = 17;
        doubleWays[17] = 16;
        doubleWays[18] = 14;
        doubleWays[19] = 12;
        doubleWays[20] = 9;
        doubleWays[21] = 6;
        doubleWays[22] = 4;
        doubleWays[23] = 2;
        doubleWays[24] = 1;

        tripleWays[0] = 216;
        tripleWays[7] = 2;
        tripleWays[8] = 2;
        tripleWays[9] = 8;
        tripleWays[10] = 8;
        tripleWays[11] = 20;
        tripleWays[12] = 18;
        tripleWays[13] = 36;
        tripleWays[14] = 30;
        tripleWays[15] = 54;
        tripleWays[16] = 42;
        tripleWays[17] = 72;
        tripleWays[18] = 54;
        tripleWays[19] = 86;
        tripleWays[20] = 62;
        tripleWays[21] = 92;
        tripleWays[22] = 62;
        tripleWays[23] = 86;
        tripleWays[24] = 54;
        tripleWays[25] = 72;
        tripleWays[26] = 42;
        tripleWays[27] = 54;
        tripleWays[28] = 30;
        tripleWays[29] = 36;
        tripleWays[30] = 18;
        tripleWays[31] = 20;
        tripleWays[32] = 8;
        tripleWays[33] = 8;
        tripleWays[34] = 2;
        tripleWays[35] = 2;

        for (int i = 0; i < MAX; i++)
	        doubleProb[i] = (ways[i] + doubleWays[i] / D + tripleWays[i] / T) / MAX;

	    for (int i = 0; i < 13; i++)
		    singleProb[i] = ways[i] / 36.0;
    }

	public double singleProb(int roll) {
		if (roll < 2 || roll > 12)
			return 0.0;
		return singleProb[roll];
	}

    public double doubleProb(int roll) {
        if (roll < 2 || roll > 35)
            return 0.0;
        return doubleProb[roll];
    }

    public double jailProb() {
        return doubleProb[0];
    }

    //return probability of getting a given total
    public double prob(int total) {
        int N = probDice.numDice();
        int SIDES = probDice.sides();
        if (total < N || total > N * SIDES)
            return 0.0;
        return numWays(total) / Math.pow(SIDES, N);
    }

    //return number of ways to get a given total
    private double numWays(int total) {
        int N = probDice.numDice();
        int SIDES = probDice.sides();
        int numWays = 0;

        int k = 0;
	    while (true) {
		    int j = 0;
		    while (true) {
			    if (SIDES * k + j == total - N)
				    numWays += Prob.combi(N, k) * Prob.combi(-N, j) * Math.pow(-1, k + j);
			    if (j > total - N)
                    break;
                j++;
            }
            if (6 * k > total - N)
                break;
            k++;
        }

        return numWays;
    }
}