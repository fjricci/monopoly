/**
 * ***************************************************************************
 * <p>
 * Author: Francis Ricci
 * File: Monopoly.java
 * <p>
 * Source code file dependencies:
 * Board.java
 * Card.java
 * Cards.java
 * Deck.java
 * ProbDice.java
 * Inactive.java
 * Jail.java
 * Player.java
 * Prob.java
 * Property.java
 * Railroad.java
 * Shuffle.java
 * Square.java
 * Taxes.java
 * Utility.java
 * <p>
 * Execution:
 * monopoly.Monopoly N
 * <p>
 * Purpose:
 * Run a text-based Monopoly game emulator, with N human players.
 * <p>
 * ***************************************************************************
 */

package monopoly;

import monopoly.Jail.JailType;
import monopoly.Player.PlayerType;

import java.util.LinkedList;
import java.util.Queue;

public class Monopoly {
	private final boolean deterministic;
	private Dice dice; //two six-sided dice
	private Board board; //game board
	private Input input;
	private Queue<Player> players;

	public Monopoly() {
		board = new Board(); //create new board
		players = new LinkedList<>();
		input = new Input();

		System.out.println("Would you like to provide your own dice and card input?");
		deterministic = input.inputBool();
		if (deterministic)
			dice = new InputDice(input);
		else
			dice = new ProbDice(); //two dice, six sided

		initialize();
	}

	public static void main(String[] args) {
		Monopoly monopoly = new Monopoly();
		monopoly.run();
	}

	public void run() {
		while (players.size() > 1) {
			try {
				players.forEach(this::turn);
			} catch (Exception e) {
				System.out.println("Early Termination initiated.");
				return;
			} finally {
				printState();
			}
		}

		Player winner = players.remove();
		System.out.println("----------------------------------------");
		System.out.print("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
		System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("THE WINNER IS " + winner.name() + "!!!");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("////////////////////////////////////////");
		System.out.println("----------------------------------------");
	}

	private void initialize() {
		int N = 0;
		System.out.println("How many players?");
		while (N == 0) {
			N = input.inputInt();
			if (N < 2 || N > 8) {
				System.out.println("Must have between 2 and 8 players. Please try again.");
				N = 0;
			}
		}

		int[] order = new int[N];
		for (int i = 0; i < N; i++) {
			System.out.println("Player " + (i + 1) + " name?");
			String name = input.inputString();
			Player.PlayerType type = Player.PlayerType.values()[i];
			Player player = new Player(type, name);
			players.add(player);
		}

		if (deterministic)
			return;

		boolean tie = true;
		boolean[] ties = new boolean[N];
		for (int i = 0; i < N; i++)
			ties[i] = true;
		int first = -1;

		while (tie) {
			for (int i = 0; i < N; i++) {
				if (ties[i])
					order[i] = dice.roll().val;
			}

			int maxRoll = 0;

			for (int i = 0; i < N; i++) {
				if (ties[i]) {
					if (order[i] > maxRoll) {
						maxRoll = order[i];
						first = i;
					}
				}
			}

			tie = false;
			for (int i = 0; i < N; i++)
				ties[i] = false;

			for (int i = 0; i < N; i++) {
				if (order[i] == maxRoll && i != first) {
					ties[i] = true;
					tie = true;
				}
			}
		}

		Queue<Player> tempPlayers = new LinkedList<>();
		for (int i = 0; i < first; i++)
			tempPlayers.add(players.remove());

		while (!tempPlayers.isEmpty())
			players.add(tempPlayers.remove());

		printState();
	}

	private void turn(Player player) {
		System.out.println("It's " + player.name() + "'s turn");
		int double_count = 0;
		while (true) {
			Dice.Roll roll = dice.roll();
			if (roll.is_double)
				double_count++;

			if (player.inJail()) {
				if (roll.is_double) {
					player.leaveJail();
					roll.is_double = false; //we don't re-roll if the double was used to escape jail
				} else {
					if (!player.stayJail())
						leaveJail(player);
					break;
				}
			}

			if (double_count == 3) {
				toJail(player);
				break;
			}

			player.move(roll.val);
			System.out.print("You rolled a " + roll.val);
			if (roll.is_double)
				System.out.print(" (double)");
			int pos = player.position();
			Square[] square = board.getBoard();
			System.out.println(" and landed on " + square[pos].name());
			boolean owned = square[pos].isOwned();
			boolean ownable = square[pos].isOwnable();

			if (!owned && ownable)
				unowned(player, square[pos]);
			else if (ownable)
				owned(player, square[pos], roll.val);
			else if (square[pos] instanceof Taxes)
				payTax(player, (Taxes) square[pos], square[pos]);
			else if (square[pos] instanceof Cards)
				drawCard(player, (Cards) square[pos], square[pos]);
			else if (square[pos] instanceof Jail)
				jailInteraction(player, (Jail) square[pos]);

			if (!roll.is_double)
				break;
		}
		System.out.println();
	}

	private void leaveJail(Player player) {
		int JAIL_COST = 50;
		if (player.numJailFree() > 0) {
			player.useJailFree();
			System.out.println("You used a Get Out Of Jail Free Card!");
		} else if (player.getMoney() >= JAIL_COST) {
			player.excMoney(JAIL_COST * -1);
			System.out.println("You paid 50 to get out of jail!");
		} else {
			int cost = JAIL_COST;
			Player bank = new Player(PlayerType.BANK, "Bank");
			while (true) {
				cost = additionalFunds(cost, player, bank);
				if (cost == Integer.MIN_VALUE)
					return;
				if (cost < 0) {
					player.excMoney(cost * -1);
					break;
				}
			}
		}
	}

	public void unowned(Player player, Square square) {
		int cost = square.cost();

		if (totalVal(availableAssets(player)) + player.getMoney() < cost) {
			System.out.println("You cannot afford to purchase " + square.name());
			purchase(auction(player, square), square);
			return;
		}

		boolean additional = false;
		System.out.println("Would you like to purchase " + square.name() + " for " + cost + " (Yes/No)?");

		if (player.getMoney() < cost) {
			additional = true;
			System.out.println("This transaction will require additional funds.");
		}

		if (input.inputBool()) {
			if (!additional)
				player.excMoney(-1 * cost);
			else {
				Player bank = new Player(PlayerType.BANK, "Bank");
				while (true) {
					cost = additionalFunds(cost, player, bank);
					if (cost == Integer.MIN_VALUE)
						return;
					if (cost < 0) {
						player.excMoney(cost * -1);
						break;
					}
				}
			}

			purchase(player, square);
		} else
			purchase(auction(player, square), square);

	}

	private void purchase(Player player, Square square) {
		if (player == null || square == null) return;

		player.addProperty(square);
		square.purchase(player);
	}

	private Player auction(Player player, Square square) {
		System.out.println("Auctioning off " + square.name() + ".");
		int currentBid = -10;
		final int BID_INCREMENT = 10;

		int count = 0;
		String[] names = new String[players.size() - 1];
		for (Player p : players) {
			if (!p.equals(player))
				names[count++] = p.name();
		}

		Player winner = null;
		while (true) {
			int minBid = currentBid + BID_INCREMENT;
			System.out.println("Would anyone like to place a bid? Minimum bid: $" + minBid);
			if (!input.inputBool())
				break;

			System.out.println("Please enter player name");
			String name = names[input.inputDecision(names)];
			for (Player p : players) {
				if (p.name().equals(name)) {
					winner = p;
					break;
				}
			}

			System.out.println(winner.name() + ", please enter your bid.");
			int bid = input.inputInt();
			if (bid < minBid) {
				System.out.println("Bid is below minimum bid. Please try again.");
				continue;
			}

			System.out.println("Bid accepted. Current highest bid - " + winner.name() + " for $" + bid);
			currentBid = bid;
		}

		if (winner != null) {
			winner.excMoney(-1 * currentBid);
			System.out.println(winner.name() + " wins auction, for $" + currentBid);
		} else
			System.out.println("No player wins auction.");

		return winner;
	}

	private void owned(Player player, Square square, int val) {
		Square squareIf = square;
		int cost = squareIf.rent(val);
		Player owner = squareIf.owner();
		if (player.getPlayer() == owner.getPlayer())
			return;
		boolean additional = false;
		System.out.println("You have landed on " + square.name() + " and owe " + cost + " in rent.");
		if (player.getMoney() < cost) {
			additional = true;
			System.out.println("This transaction will require additional funds.");
		}

		if (!additional) {
			player.excMoney(-1 * cost);
			owner.excMoney(cost);
		} else {
			while (true) {
				cost = additionalFunds(cost, player, owner);
				if (cost == Integer.MIN_VALUE)
					return;
				if (cost < 0) {
					player.excMoney(cost * -1);
					break;
				}
			}
		}
	}

	private void payTax(Player player, Taxes tax, Square square) {
		int cost;
		if (square.position() == 4) {
			System.out.println("Would you like to pay 10% or 200 (10%/200)?");
			if (input.inputDecision(new String[]{"10%", "200"}) == 0)
				cost = tax.tax(player.getAssets());
			else
				cost = tax.tax();
		} else
			cost = tax.tax();
		boolean additional = false;
		System.out.println("You have landed on " + square.name() + " and owe " + cost + " in rent.");
		if (player.getMoney() < cost) {
			additional = true;
			System.out.println("This transaction will require additional funds.");
		}

		if (!additional)
			player.excMoney(-1 * cost);
		else {
			Player bank = new Player(PlayerType.BANK, "Bank");
			while (true) {
				cost = additionalFunds(cost, player, bank);
				if (cost == Integer.MIN_VALUE)
					return;
				if (cost < 0) {
					player.excMoney(cost * -1);
					break;
				}
			}
		}
	}

	private void drawCard(Player player, Cards cards, Square square) {
		int numString = 3;
		Card card = cards.draw();
		String[] string = new String[numString];
		if (card.textA() != null)
			string[0] = card.textA();
		if (card.textB() != null)
			string[1] = card.textB();
		if (card.textC() != null)
			string[2] = card.textC();
		for (int i = 0; i < numString; i++) {
			if (string[i] == null)
				break;
			System.out.println(string[i]);
		}

		switch (card.action()) {
			case BANK_MONEY:
				player.excMoney(card.value());
				break;
			case PLAYER_MONEY:
				player.excMoney(card.eachPlayer());
				allPlayers(-1 * card.eachPlayer());
				break;
			case MOVE:
				player.move(card.travel());
				break;
			case MOVE_TO:
				player.moveTo(card.travelTo());
				break;
			case MOVE_NEAREST:   //TODO Move Nearest
				break;
			case STREET_REPAIRS: //TODO Street Repairs
				break;
			case OUT_JAIL:
				player.addJailFree();
				break;
			default:
				break;
		}
	}

	private void allPlayers(int value) {
		for (Player player : players)
			player.excMoney(value);
	}

	private void jailInteraction(Player player, Jail jail) {
		Jail.JailType type = jail.getType();
		if (type == JailType.TO_JAIL)
			intoJail(player);
	}

	private void toJail(Player player) {
		int JAIL_POS = 30;
		System.out.println("Go to Jail!");
		player.moveTo(JAIL_POS);
		Square[] square = board.getBoard();
		Jail jail = (Jail) square[JAIL_POS];
		jailInteraction(player, jail);
	}

	private void intoJail(Player player) {
		int VISIT_POS = 10;
		player.toJail();
		player.moveTo(VISIT_POS);
	}

	private int additionalFunds(int cost, Player player, Player owner) {
		Queue<Square> props = availableAssets(player);
		int availableAssets = totalVal(props) + player.getMoney();

		if (availableAssets < cost) {
			lose(player, owner);
			return Integer.MIN_VALUE;
		} else {
			System.out.println("You need additional funds!");
			System.out.println("How will you obtain necessary funds (Mortgage/Sell Houses)?");

			int choice = input.inputDecision(new String[]{"Mortgage", "Sell Houses"});

			if (choice == 0) {
				System.out.println("Which property would you like to mortgage?");
				System.out.println("Please enter number.");
				System.out.println("You own the following properties:");
				int counter = 1;
				for (Square sq : props)
					System.out.println(counter++ + ") " + sq.name());

				int propNum = input.inputInt();
				int propState = 1;

				for (Square sq : props) {
					if (propState++ == propNum)
						cost -= sq.mortgage();
				}
			} else
				; //TODO case where sell houses
		}

		return cost;
	}

	private Queue<Square> availableAssets(Player player) {
		Queue<Square> props = new LinkedList<>();
		for (Square sq : player.properties()) {
			Square property = sq;
			if (property.isOwnable() && property.isMortgaged())
				props.add(property);
		}

		return props;
	}

	private int totalVal(Queue<Square> props) {
		int totalMoney = 0;
		for (Square sq : props) {
			totalMoney += sq.cost();
			if (sq instanceof Property) {
				Property prop = (Property) sq;
				totalMoney += prop.numHouses() * prop.houseCost();
			}
		}
		return totalMoney;
	}

	private void lose(Player loser, Player winner) {
		Queue<Square> squares = loser.properties();
		while (!squares.isEmpty()) {
			winner.addProperty(squares.remove());
			squares.remove();
		}
		winner.excMoney(loser.getMoney());
		while (loser.numJailFree() > 0) {
			loser.useJailFree();
			winner.addJailFree();
		}
		players.remove(loser);
	}

	private void printState() {
		int counter = 1;
		for (Player player : players) {
			System.out.println("----------------------------------------");
			System.out.println("Player " + counter++);
			System.out.printf("%-10s%30s%n", "Name", player.name());
			System.out.printf("%-10s%30s%n", "Money", player.getMoney());
			System.out.printf("%-10s%30s%n", "Position", player.position());
			System.out.printf("%-10s", "Properties");
			Queue<Square> owned = player.properties();
			if (owned.isEmpty())
				System.out.printf("%30s%n", "none");
			else
				System.out.printf("%30s%n", owned.remove().name());
			for (Square s : owned)
				System.out.printf("%40s%n", s.name());
			System.out.println("----------------------------------------");
		}
	}
}
