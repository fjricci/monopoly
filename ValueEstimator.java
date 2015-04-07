package monopoly;

import java.util.LinkedList;
import java.util.Queue;

public class ValueEstimator
{
    private Queue<Player.PlayerType> playerQueue; //contains all player enums
    private Queue<Integer> posQueue;  //contains all player positions
    private Board board;  //stores a board
    private Player player;  //stores current player
    private Dice dice;   //stores a pair of six-sided dice
    private Cards chance; //stores a deck of chance cards
    
    //constructs ValueEstimator object
    public ValueEstimator(Board board, Queue<Player> queue, 
                                      Player player, Dice dice, Cards chance)
    {
        posQueue = new LinkedList<Integer>();
        playerQueue = new LinkedList<Player.PlayerType>();
        
        //iterate through queue of players, enqueue position and enum
        for (Player p : queue)
        {
            posQueue.add(p.getPos());
            playerQueue.add(p.getPlayer());
        }
        this.board = board;
        this.player = player;
        this.dice = dice;
        this.chance = chance;
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
                int i = s.getPos();
                Square.SquareType type = s.type();
                probs[i][playerID] = getProb(i, j, type);
            }
        }
        return probs;
    }
    
    //probability of getting from playerPos to squarePos
    private double getProb(int squarePos, int playerPos,
                                        Square.SquareType type)
    {
        if (squarePos < playerPos)
            squarePos += board.getSize(); //accounts for passing go
        int dist = squarePos - playerPos;
        
        double prob = directProb(dist);
        prob += indirectProb(squarePos, playerPos, type);
        return prob;
    }
    
    //probability of landing directly on property by dice roll
    private double directProb(int dist)
    {
        double prob = dice.doubleProb(dist);
        return prob;
    }
    
    //probability of landing on property by chance card
    private double indirectProb(int squarePos, int playerPos,
                                        Square.SquareType type)
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
    
    public static void main(String[] args)
    {
        Board board = new Board();
        Dice dice = new Dice(2);
        Player player = new Player(Player.PlayerType.PLAYER_A, "Francis");
        Queue<Player> queue = new LinkedList<Player>();
        Cards chance = new Cards(Card.CardType.CHANCE);
        
        Player player1 = new Player(Player.PlayerType.PLAYER_B, "John");
        Player player2 = new Player(Player.PlayerType.PLAYER_C, "Jim");
        Player player3 = new Player(Player.PlayerType.PLAYER_D, "James");
        Player player4 = new Player(Player.PlayerType.PLAYER_E, "Jacob");
        
        queue.add(player1);
        queue.add(player2);
        queue.add(player3);
        queue.add(player4);
        
        for (int i = 0; i < board.getSize(); i++)
        {
            Square square = new Square(i);
            if (square.ownable())
                player.addProperty(i);
        }
        
        Queue<Square> properties = player.properties();
        for (Square sq : properties)
        {
            System.out.println(sq);
            System.out.println();
        }
        
        ValueEstimator value = new ValueEstimator(board, queue, 
                                                        player, dice, chance);
        
        double[][] probs = value.probLanding();
        
        for (int i = 0; i < probs.length; i++)
        {
            for (int j = 0; j < probs[i].length; j++)
            {
                System.out.println(probs[i][j]);
            }
        }
    }
}