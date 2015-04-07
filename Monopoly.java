/******************************************************************************
 * 
 *  Author: Francis Ricci
 *  File: Monopoly.java
 *  
 *  Source code file dependencies:
 *      Board.java
 *      Card.java
 *      Cards.java
 *      Deck.java
 *      Dice.java
 *      Inactive.java
 *      Jail.java
 *      Player.java
 *      Prob.java
 *      Property.java
 *      Railroad.java
 *      Shuffle.java
 *      Square.java
 *      Taxes.java
 *      Utility.java
 *  
 *  Execution:
 *      monopoly.Monopoly N
 *      
 *  Purpose:
 *      Run a text-based Monopoly game emulator, with N human players.
 * 
 *****************************************************************************/

package monopoly;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import monopoly.Jail.JailType;
import monopoly.Player.PlayerType;
import monopoly.Square.SquareType;

public class Monopoly
{
	private Dice dice; //two six-sided dice
	private Board board; //game board
    private Scanner input;
	
	private Queue<Player> players;

    public Monopoly(){
        dice = new Dice(2, 6); //two dice, six sided
        board = new Board(); //create new board
        players = new LinkedList<>();
        input = new Scanner(System.in);

        initialize();
    }

    public void run(){
        while (players.size() > 1)
        {
            players.stream().filter(player -> !turn(player)).forEach(players::remove);
            printState();
        }

        Player winner = players.remove();
        System.out.println("----------------------------------------");
        System.out.print("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("THE WINNER IS " + winner.getName() + "!!!");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("////////////////////////////////////////");
        System.out.println("----------------------------------------");
    }
		
	private void initialize()
	{
        int N = 0;
        System.out.println("How many players?");
        while (N == 0) {
            N = input.nextInt();
            if (N < 2 || N > 8){
                System.out.println("Must have between 2 and 8 players. Please try again.");
                N = 0;
            }
        }

		int[] order = new int[N];
		for (int i = 0; i < N; i++)
		{
		    System.out.println("Player " + (i + 1) + " name?");
            String name = input.nextLine();
		    Player.PlayerType type = Player.PlayerType.values()[i];
		    Player player = new Player(type, name);
		    players.add(player);
		}
		
        boolean tie = true;
        boolean[] ties = new boolean[N];
        for (int i = 0; i < N; i++)
            ties[i] = true;
        int first = -1;
        
        while (tie)
        {
            for (int i = 0; i < N; i++)
            {
                if (ties[i])
                    order[i] = dice.roll();
            }
            
            int maxRoll = 0;
            
            for (int i = 0; i < N; i++)
            {
                if (ties[i])
                {
                    if (order[i] > maxRoll)
                    {
                        maxRoll = order[i];
                        first = i;
                    }
                }
            }
            
            tie = false;
            for (int i = 0; i < N; i++)
                ties[i] = false;
            
            for (int i = 0; i < N; i++)
            {
                if (order[i] == maxRoll && i != first)
                {
                    ties[i] = true;
                    tie = true;
                }
            }
        }
        
        Queue<Player> tempPlayers = new LinkedList<Player>();
		for (int i = 0; i < first; i++)
		    tempPlayers.add(players.remove());
		
		while (!tempPlayers.isEmpty())
		    players.add(tempPlayers.remove());
		
		printState();
	}

    private boolean turn(Player player)
    {
        System.out.println("It's " + player.getName() + "'s turn");
        int[] rolls = dice.rollDouble();
        for (int i = 0; i < rolls.length; i++)
        {
            if (player.inJail())
            {
                if (rolls[1] != 0)
                {
                    player.leaveJail();
                    rolls[1] = 0;
                }
                else
                {
                    boolean stillJail = player.stayJail();
                    if (!stillJail)
                        leaveJail(player);
                    break;
                }
            }
            if (rolls[i] == 0)
                break;
            if (rolls[i] == -1)
            {
                toJail(player);
                break;
            }
            int roll = rolls[i];
            player.move(roll);
            System.out.print("You rolled a " + roll);
            int pos = player.getPos();
            Square[] square = board.getBoard();
            System.out.println(" and landed on " + square[pos].getName());
            boolean owned = square[pos].isOwned();
            boolean ownable = square[pos].ownable();
            if (!owned && ownable)
            {
                switch (square[pos].type())
                {
                case PROPERTY: buyProp(player, (Property) 
                                      square[pos].square(), square[pos]);
                break;
                case RAILROAD: buyRail(player, (Railroad) 
                                      square[pos].square(), square[pos]);
                break;
                case UTILITY: buyUtil(player, (Utility) 
                                      square[pos].square(), square[pos]);
                break;
                default: break;
                }
            }
            else if (ownable)
            {
                switch (square[pos].type())
                {
                case PROPERTY: payProp(player, (Property) 
                                      square[pos].square(), square[pos]);
                break;
                case RAILROAD: payRail(player, (Railroad) 
                                      square[pos].square(), square[pos]);
                break;
                case UTILITY: payUtil(player, (Utility) 
                                      square[pos].square(), square[pos], roll);
                break;
                default: break;
                }
            }
            else
            {
                switch(square[pos].type())
                {
                case TAXES: payTax(player, (Taxes) 
                                      square[pos].square(), square[pos]);
                break;
                case CARDS: drawCard(player, (Cards)
                                      square[pos].square(), square[pos]);
                break;
                case JAIL: jailInteraction(player, (Jail) square[pos].square());
                break;
                case INACTIVE: break;
                default: break;
                }
            }
        }
        System.out.println();
        return true;
    }

    private void leaveJail(Player player)
    {
        int JAIL_COST = 50;
        if (player.numJailFree() > 0)
        {
            player.useJailFree();
            System.out.println("You used a Get Out Of Jail Free Card!");
        }
        else if (player.getMoney() >= JAIL_COST)
        {
            player.excMoney(JAIL_COST * -1);
            System.out.println("You paid 50 to get out of jail!");
        }
        else
        {
            int cost = JAIL_COST;
            Player bank = new Player(PlayerType.BANK, "Bank");
            while (true)
            {
                cost = additionalFunds(cost, player, bank);
                if (cost == Integer.MIN_VALUE)
                    return;
                if (cost < 0)
                {
                    player.excMoney(cost * -1);
                    break;
                }
            }
        }
    }

    private void buyProp(Player player, Property prop, Square square)
    {
        int cost = prop.cost();
        boolean additional = false;
        System.out.println("Would you like to purchase " + square.getName() +
                                 " for " + cost + " (Yes/No)?");
        if (player.getMoney() < cost)
        {
            additional = true;
            System.out.println("This transaction will require "
                                                       + "additional funds.");
        }
        
        String string = input.nextLine();
        if (string.equals("Yes") || string.equals("yes"))
        {
            if (!additional)
            {
                player.excMoney(-1*cost);
                player.addProperty(square.getPos());
                prop.purchase(player);
            }
            else
            {
                Player bank = new Player(PlayerType.BANK, "Bank");
                while (true)
                {
                    cost = additionalFunds(cost, player, bank);
                    if (cost == Integer.MIN_VALUE)
                        return;
                    if (cost < 0)
                    {
                        player.excMoney(cost * -1);
                        break;
                    }
                }
            }
        }
        else if (string.equals("No") || string.equals("no"))
            //TODO case where property is not purchased by player
            ;
        else
            throw new IllegalArgumentException("Must respond Yes or No!");
    }
    
    private void buyRail(Player player, Railroad rail, Square square)
    {
        int cost = rail.cost();
        boolean additional = false;
        System.out.println("Would you like to purchase " + square.getName() +
                                 " for " + cost + " (Yes/No)?");
        if (player.getMoney() < cost)
        {
            additional = true;
            System.out.println("This transaction will require"
                                                     + " additional funds.");
        }
        
        String string = input.nextLine();
        
        if (string.equals("Yes") || string.equals("yes"))
        {
            if (!additional)
            {
                player.excMoney(-1*cost);
                player.addProperty(square.getPos());
                rail.purchase(player);
            }
            else
            {
                Player bank = new Player(PlayerType.BANK, "Bank");
                while (true)
                {
                    cost = additionalFunds(cost, player, bank);
                    if (cost == Integer.MIN_VALUE)
                        return;
                    if (cost < 0)
                    {
                        player.excMoney(cost * -1);
                        break;
                    }
                }
            }
        }
        else if (string.equals("No") || string.equals("no"))
            //TODO case where property is not purchased by player
            ;
        else
            throw new IllegalArgumentException("Must respond Yes or No!");
    }
    
    private void buyUtil(Player player, Utility util, Square square)
    {
        int cost = util.cost();
        boolean additional = false;
        System.out.println("Would you like to purchase " + square.getName() +
                                 " for " + cost + " (Yes/No)?");
        if (player.getMoney() < cost)
        {
            additional = true;
            System.out.println("This transaction will require"
                                                     + " additional funds.");
        }
        
        String string = input.nextLine();
        
        if (string.equals("Yes") || string.equals("yes"))
        {
            if (!additional)
            {
                player.excMoney(-1*cost);
                player.addProperty(square.getPos());
                util.purchase(player);
            }
            else
            {
                Player bank = new Player(PlayerType.BANK, "Bank");
                while (true)
                {
                    cost = additionalFunds(cost, player, bank);
                    if (cost == Integer.MIN_VALUE)
                        return;
                    if (cost < 0)
                    {
                        player.excMoney(cost * -1);
                        break;
                    }
                }
            }
        }
        else if (string.equals("No") || string.equals("no"))
            //TODO case where property is not purchased by player
            ;
        else
            throw new IllegalArgumentException("Must respond Yes or No!");
    }
    
    private void payProp(Player player, Property prop, Square square)
    {
        int cost = prop.rent();
        Player owner = prop.owner();
        if (player.getPlayer() == owner.getPlayer())
            return;
        boolean additional = false;
        System.out.println("You have landed on " + square.getName() +
                                 " and owe " + cost + " in rent.");
        if (player.getMoney() < cost)
        {
            additional = true;
            System.out.println("This transaction will require"
                                                  + " additional funds.");
        }
        
        if (!additional)
        {
            player.excMoney(-1*cost);
            owner.excMoney(cost);
        }
        else
        {
            while (true)
            {
                cost = additionalFunds(cost, player, owner);
                if (cost == Integer.MIN_VALUE)
                    return;
                if (cost < 0)
                {
                    player.excMoney(cost * -1);
                    break;
                }
            }
        }
    }

    private void payRail(Player player, Railroad rail, Square square)
    {
        int cost = rail.rent();
        Player owner = rail.owner();
        if (player.getPlayer() == owner.getPlayer())
            return;
        boolean additional = false;
        System.out.println("You have landed on " + square.getName() +
                                 " and owe " + cost + " in rent.");
        if (player.getMoney() < cost)
        {
            additional = true;
            System.out.println("This transaction will require"
                                                  + " additional funds.");
        }
        
        if (!additional)
        {
            player.excMoney(-1*cost);
            owner.excMoney(cost);
        }
        else
        {
            while (true)
            {
                cost = additionalFunds(cost, player, owner);
                if (cost == Integer.MIN_VALUE)
                    return;
                if (cost < 0)
                {
                    player.excMoney(cost * -1);
                    break;
                }
            }
        }
    }
  
    private void payUtil(Player player, Utility util, Square square, int roll)
    {
        int cost = util.rent(roll);
        Player owner = util.owner();
        if (player.getPlayer() == owner.getPlayer())
            return;
        boolean additional = false;
        System.out.println("You have landed on " + square.getName() +
                                 " and owe " + cost + " in rent.");
        if (player.getMoney() < cost)
        {
            additional = true;
            System.out.println("This transaction will require"
                                               + " additional funds.");
        }
        
        if (!additional)
        {
            player.excMoney(-1*cost);
            owner.excMoney(cost);
        }
        else
        {
            while (true)
            {
                cost = additionalFunds(cost, player, owner);
                if (cost == Integer.MIN_VALUE)
                    return;
                if (cost < 0)
                {
                    player.excMoney(cost * -1);
                    break;
                }
            }
        }
    }
    
    private void payTax(Player player, Taxes tax, Square square)
    {
        int cost;
        if (square.getPos() == 4)
        {
            System.out.println("Would you like to pay 10% or 200 (10%/200)?");
            String taxString = input.nextLine();
            if (taxString.startsWith("10"))
            {
                int assets = player.getAssets();
                cost = tax.tax(assets);
            }
            else if (taxString.startsWith("200"))
                cost = tax.tax();
            else
                throw new IllegalArgumentException("Must respond with "
                                                            + "10% or 200!");
        }
        else
            cost = tax.tax();
        boolean additional = false;
        System.out.println("You have landed on " + square.getName() +
                                 " and owe " + cost + " in rent.");
        if (player.getMoney() < cost)
        {
            additional = true;
            System.out.println("This transaction will require"
                                                     + " additional funds.");
        }
        
        if (!additional)
            player.excMoney(-1*cost);
        else
        {
            Player bank = new Player(PlayerType.BANK, "Bank");
            while (true)
            {
                cost = additionalFunds(cost, player, bank);
                if (cost == Integer.MIN_VALUE)
                    return;
                if (cost < 0)
                {
                    player.excMoney(cost * -1);
                    break;
                }
            }
        }
    }
    
    private void drawCard(Player player, Cards cards, Square square)
    {
        int numString = 3;
        Card card = cards.draw();
        String[] string = new String[numString];
        if (card.textA() != null)
            string[0] = card.textA();
        if (card.textB() != null)
            string[1] = card.textB();
        if (card.textC() != null)
            string[2] = card.textC();
        for (int i = 0; i < numString; i++)
        {
            if (string[i] == null)
                break;
            System.out.println(string[i]);
        }
        
        switch(card.action())
        {
        case BANK_MONEY:     player.excMoney(card.value());
                             break;
        case PLAYER_MONEY:   player.excMoney(card.eachPlayer());
                             allPlayers(-1 * card.eachPlayer());
                             break;
        case MOVE:           player.move(card.travel());
                             break;
        case MOVE_TO:        player.moveTo(card.travelTo());
                             break;
        case MOVE_NEAREST:   //TODO Move Nearest
                             break;
        case STREET_REPAIRS: //TODO Street Repairs
                             break;
        case OUT_JAIL:       player.addJailFree();
                             break;
        default:             break;
        }
    }
    
    private void allPlayers(int value)
    {
        for (Player p : players)
        {
            p.excMoney(value);
        }
    }

    private void jailInteraction(Player player, Jail jail)
    {
        Jail.JailType type = jail.getType();
        if (type == JailType.TO_JAIL)
            intoJail(player);
    }
    
    private void toJail(Player player)
    {
        int JAIL_POS = 30;
        System.out.println("Go to Jail!");
        player.moveTo(JAIL_POS);
        Square[] square = board.getBoard();
        Jail jail = (Jail) square[JAIL_POS].square();
        jailInteraction(player, jail);
    }
    
    private void intoJail(Player player)
    {
        int VISIT_POS = 10;
        player.toJail();
        player.moveTo(VISIT_POS);
    }
    
    private int additionalFunds(int cost, Player player, Player owner)
    {
        int totalMoney = 0;
        
        Queue<Square> props = new LinkedList<Square>();
        for (Square sq : player.properties())
        {
            SquareType type = sq.type();
            switch(type)
            {
            case PROPERTY: Property prop = (Property) sq.square();
                           if (!prop.isMortgaged())
                           {
                               props.add(sq);
                               continue;
                           }
                           break;
            case UTILITY:  Utility util = (Utility) sq.square();
                           if (util.isMortgaged())
                           {
                               props.add(sq);
                               continue;
                           }
                           break;
            case RAILROAD: Railroad rail = (Railroad) sq.square();
                           if (rail.isMortgaged())
                           {
                               props.add(sq);
                               continue;
                           }
                           break;
            default:  break;
            }
        }
        for (Square sq : props)
        {
            SquareType type = sq.type();
            switch (type)
            {
            case PROPERTY: Property prop = (Property) sq.square();
                           totalMoney += prop.mortgageCost();
                           totalMoney += prop.numHouses() * prop.houseCost();
                           break;
            case UTILITY:  Utility util = (Utility) sq.square();
                           totalMoney += util.mortgageCost();
                           break;
            case RAILROAD: Railroad rail = (Railroad) sq.square();
                           totalMoney += rail.mortgageCost();
                           break;
            default:       break;
            }
        }
        
        if (totalMoney < cost)
        {
            lose(player, owner);
            return Integer.MIN_VALUE;
        }
        else
        {
            System.out.println("You need additional funds!");
            System.out.println("How will you obtain necessary "
                    + "funds (Mortgage/Sell Houses)?");
            
            String funding = input.nextLine();
            
            if (funding.equals("Mortgage") || funding.equals("mortgage"))
            {
                System.out.println("Which property would you like to mortgage?");
                System.out.println("Please enter number.");
                System.out.println("You own the following properties:");
                int counter = 1;
                for (Square sq : props)
                {
                    System.out.println(counter++ + ") " + sq.getName());
                }
                
                int propNum = input.nextInt();
                int propState = 1;
                
                for (Square sq : props)
                {
                    if (propState++ == propNum)
                    {
                        SquareType type = sq.type();
                        switch (type)
                        {
                        case PROPERTY: Property prop = (Property) sq.square();
                                       cost -= prop.mortgage();
                                       break;
                        case UTILITY:  Utility util = (Utility) sq.square();
                                       cost -= util.mortgage();
                                       break;
                        case RAILROAD: Railroad rail = (Railroad) sq.square();
                                       cost -= rail.mortgage();
                                       break;
                        default:       break;
                        }
                    }
                }
            }
            else if (funding.startsWith("Sell") || 
                                    funding.startsWith("sell"))
                ; //TODO case where sell houses
            else
                throw new IllegalArgumentException("Must mortgage "
                                                      + "or sell houses!");            
        }
        
        return cost;
    }
    
    private void lose(Player player, Player owner)
    {
        Queue<Integer> props = player.propIDs();
        Queue<Square> squares = player.properties();
        while (!props.isEmpty())
        {
            int i = props.remove();
            owner.addProperty(i);
            squares.remove();
        }
        owner.excMoney(player.getMoney());
        while (player.numJailFree() > 0)
        {
            player.useJailFree();
            owner.addJailFree();
        }
        Queue<Player> temp = new LinkedList<Player>();
        for (Player p : players)
        {
            if (p.getPlayer() != player.getPlayer())
                temp.add(p);                
        }
        players = temp;
    }
    
	private void printState()
	{
        int counter = 1;
        for (Player player : players)
        {
            System.out.println("----------------------------------------");
            System.out.println("Player " + counter++);
            System.out.printf("%-10s%30s%n", "Name", player.getName());
            System.out.printf("%-10s%30s%n", "Money", player.getMoney());
            System.out.printf("%-10s%30s%n", "Position", player.getPos());
            System.out.printf("%-10s", "Properties");
            Queue<Square> owned = player.properties();
            if (owned.isEmpty())
                System.out.printf("%30s%n", "none");
            else
                System.out.printf("%30s%n", owned.remove().getName());
            for (Square s : owned)
            {
                System.out.printf("%40s%n", s.getName());
            }
            System.out.println("----------------------------------------");
        }
	}

	public static void main(String[] args)
	{
        Monopoly mono = new Monopoly();
        mono.run();
	}
}