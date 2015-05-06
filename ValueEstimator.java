package monopoly;

public class ValueEstimator {
 /*   private Queue<Player.PlayerType> playerQueue; //contains all player enums
    private Queue<Integer> posQueue;  //contains all player positions
    private Board board;  //stores a board
    private Player player;  //stores current player
    private ProbDice probDice;   //stores a pair of six-sided probDice
    private Cards chance; //stores a deck of chance cards
    private double[] doubleProb;
    
    //constructs ValueEstimator object
    public ValueEstimator(Board board, Queue<Player> queue,
                          Player player, ProbDice probDice, Cards chance)
    {
        posQueue = new LinkedList<>();
        playerQueue = new LinkedList<>();
        
        //iterate through queue of players, enqueue position and enum
        for (Player p : queue)
        {
            posQueue.add(p.position());
            playerQueue.add(p.getPlayer());
        }
        this.board = board;
        this.player = player;
        this.probDice = probDice;
        this.chance = chance;
        if (probDice.numDice() == 2)
            setDouble();
    }

    public static void main(String[] args) {
        Board board = new Board();
        ProbDice probDice = new ProbDice();
        Player player = new Player(Player.PlayerType.PLAYER_A, "Francis");
        Queue<Player> queue = new LinkedList<>();
        Cards chance = new Cards("Chance", 0, Card.CardType.CHANCE);

        Player player1 = new Player(Player.PlayerType.PLAYER_B, "John");
        Player player2 = new Player(Player.PlayerType.PLAYER_C, "Jim");
        Player player3 = new Player(Player.PlayerType.PLAYER_D, "James");
        Player player4 = new Player(Player.PlayerType.PLAYER_E, "Jacob");

        queue.add(player1);
        queue.add(player2);
        queue.add(player3);
        queue.add(player4);

        for (int i = 0; i < board.getSize(); i++) {
            Square square = new Square(i);
            if (square.ownable())
                player.addProperty(i);
        }

        Queue<Square> properties = player.properties();
        for (Square sq : properties) {
            System.out.println(sq);
            System.out.println();
        }

        ValueEstimator value = new ValueEstimator(board, queue,
                player, probDice, chance);

        double[][] probs = value.probLanding();

        for (double[] prob : probs) {
            for (int j = 0; j < prob.length; j++)
                System.out.println(prob[j]);
        }
    }
    
    //return the probability of a given number of players landing
    //on a given property
    public double[][] probLanding()
    {
        int SQUARES = board.getSize();
        int MAX_PLAYERS = 8;

        //first array dimension is the square position
        //second array dimension is the number of players landing on square
        double[][] probs = new double[SQUARES][MAX_PLAYERS];

        Iterable<Square> props = player.properties();
        for (int j : posQueue)
        {
            int playerID = playerQueue.remove().ordinal(); //enum to int
            for (Square s : props)
            {
                int i = s.position();
                probs[i][playerID] = getProb(i, j, s);
            }
        }
        return probs;
    }
    
    //probability of getting from playerPos to squarePos
    private double getProb(int squarePos, int playerPos, Square square)
    {
        if (squarePos < playerPos)
            squarePos += board.getSize(); //accounts for passing go
        int dist = squarePos - playerPos;

        double prob = directProb(dist);
        prob += indirectProb(squarePos, playerPos, square);
        return prob;
    }

    //probability of landing directly on property by probDice roll
    private double directProb(int dist)
    {
        return doubleProb(dist);
    }
    
    //probability of landing on property by chance card
    private double indirectProb(int squarePos, int playerPos, Square square)
    {
        //TODO Account for rolls remaining after chance if doubles
        double prob = 0.0;
        int chancePosA = 7;
        int chancePosB = 22;
        int chancePosC = 36;

        if (chancePosA < playerPos)
            chancePosA += board.getSize(); //accounts for passing go
        if (chancePosB < playerPos)
            chancePosB += board.getSize(); //accounts for passing go
        if (chancePosC < playerPos)
            chancePosC += board.getSize(); //accounts for passing go

        int distA = chancePosA - playerPos;
        int distB = chancePosB - playerPos;
        int distC = chancePosC - playerPos;
        double probChanceA = directProb(distA);
        double probChanceB = directProb(distB);
        double probChanceC = directProb(distC);

        double probChance = probChanceA + probChanceB + probChanceC;

        int SIZE = chance.size();

        for (Card card : chance.cards())
        {
            if (card.travelTo() == squarePos)
                prob += probChance / SIZE;
            if (card.travel() == squarePos - playerPos)
                prob += probChance / SIZE;
            if (card.travelNear() == type)
                prob += probChance / SIZE;
        }

        return prob;
    }

    private void setDouble()
    {
        double TOT = 43.0;
        double D = 36.0;
        double T = 1296.0;
        int MAX = 36;

        int[] ways = new int[MAX];
        int[] doubleWays = new int[MAX];
        int[] tripleWays = new int[MAX];

        doubleProb = new double[MAX];

        for (int i = 0; i < MAX; i++)
        {
            ways[i] = 0;
            doubleWays[i] = 0;
            tripleWays[i] = 0;
        }

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
            doubleProb[i] = (ways[i] / 1.0 + doubleWays[i] / D
                    + tripleWays[i] / T) / TOT;
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
        while (true)
        {
            int j = 0;
            while (true)
            {
                if (SIDES * k + j == total - N) {
                    numWays += Prob.combi(N, k) * Prob.combi(-N, j)
                            * Math.pow(-1, k + j);
                }
                if (j > total - N)
                    break;
                j++;
            }
            if (6 * k > total - N)
                break;
            k++;
        }

        return numWays;
    }*/
}