package monopoly;

import static monopoly.Player.PlayerType;

public class Railroad implements Square {
	//rent, given number of railroads owned by a player
	private final int ONE = 25;
	private final int TWO = 50;
	private final int THREE = 100;
	private final int FOUR = 200;
	private final int COST = 200;
	private int numOwned;  //number of railroads owned by a player
	private PlayerType ownerType;
	private Player owner;
	private boolean owned;  //is property owned?
	private boolean mortgaged;

	private String name;
	private int pos;

	private Railroad[] others = new Railroad[3];

	//constructor
	public Railroad(String name, int pos) {
		numOwned = 1;
		this.name = name;
		this.pos = pos;
	}

	public void createGroup(Railroad a, Railroad b, Railroad c){
		others[0] = a;
		others[1] = b;
		others[2] = c;
	}

	public void updateOwners(){
		numOwned = 1;
		for (Railroad r : others){
			if (r.owner().equals(owner))
				numOwned++;
		}
	}

	public int position() {
		return pos;
	}

	public String name() {
		return name;
	}

	//update status of property to owned
	public void purchase(Player player) {
		owned = true;
		owner = player;
		ownerType = player.getPlayer();

		updateOwners();
	}

	public boolean isOwnable() {
		return true;
	}

	//return rent owed
	public int rent(int val) {
		updateOwners();

		switch (numOwned) {
			case 1:
				return ONE;
			case 2:
				return TWO;
			case 3:
				return THREE;
			case 4:
				return FOUR;
			default:
				return 0;
		}
	}

	public boolean isOwned() {
		return owned;
	}

	public Player owner() {
		return owner;
	}

	public int cost() {
		return COST;
	}

	//mortgage or unmortgage property
	public int mortgage() {
		updateOwners();

		if (mortgaged) {
			mortgaged = false;
			return (int) Math.round((COST / 2) * 1.1);
		} else {
			mortgaged = true;
			return COST / 2;
		}
	}

	public boolean isMortgaged() {
		return mortgaged;
	}

	public int mortgageCost() {
		return COST / 2;
	}

	public String toString() {
		return name;
	}
}