package monopoly;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Created by fjricci on 6/22/2015.
 * A CPU player.
 */
public class CPUPlayer implements Player {
	private final int TO_JAIL = 30;
	private final int IN_JAIL = 10;
	private final Queue<Square> properties;
	private final String playerName;
	private int money;
	private int position;
	private boolean inJail;
	private int jailTurn;
	private int numJailFree;
	private boolean chanceFree;

	public CPUPlayer(int index) {
		money = 1500;
		properties = new LinkedList<>();
		position = 0;
		this.playerName = "CPU " + (index - 1);
		inJail = false;
		numJailFree = 0;
		chanceFree = false;
	}

	public void addProperty(Square square) {
		if (!square.isOwnable())
			throw new IllegalArgumentException("This property cannot be purchased!");
		properties.add(square);
		square.purchase(this);
	}

	public void move(int numSpaces) {
		position += numSpaces;
		int BOARD_SIZE = 40;
		if (position >= BOARD_SIZE) {
			position -= BOARD_SIZE;
			excMoney(200);
		}

		if (position == TO_JAIL) {
			position = IN_JAIL;
			toJail();
		}
	}

	public void moveTo(int pos) {
		if (pos < position && !inJail)
			excMoney(200);
		position = pos;

		if (position == TO_JAIL) {
			position = IN_JAIL;
			toJail();
		}
	}

	public int position() {
		return position;
	}

	public Queue<Square> properties() {
		return properties.stream().collect(Collectors.toCollection(LinkedList::new));
	}

	public String name() {
		return playerName;
	}

	public int getMoney() {
		return money;
	}

	public void excMoney(int money) {
		this.money += money;
	}

	public void toJail() {
		inJail = true;
		move(40);
		jailTurn = 0;
	}

	public boolean stayJail() {
		jailTurn++;
		if (jailTurn == 3) {
			inJail = false;
			return false;
		}
		return true;
	}

	public void sellProp(Square sq) {
		properties.remove(sq);
	}

	public void leaveJail() {
		inJail = false;
		moveTo(10);
	}

	public boolean inJail() {
		return inJail;
	}

	public void addJailFree(boolean chance) {
		numJailFree++;
		chanceFree = chance;
	}

	public boolean useJailFree() {
		if (numJailFree < 1)
			throw new RuntimeException("You do not have any cards!");

		numJailFree--;
		boolean deck = chanceFree;
		chanceFree = !chanceFree;
		return deck;
	}

	public int numJailFree() {
		return numJailFree;
	}

	public int getAssets() {
		int assets = this.money;
		for (Square s : properties) {
			assets += s.cost();
			if (s instanceof Property)
				assets += getHouseVal((Property) s);
		}
		return assets;
	}

	private int getHouseVal(Property prop) {
		int numHouses = prop.numHouses();
		int houseCost = prop.houseCost();

		return numHouses * houseCost;
	}
}
