package monopoly;

public class Railroad implements Square {
	private final int COST = 200;
	private final String name;
	private final int pos;
	private final Railroad[] others = new Railroad[3];
	private int numOwned;  //number of railroads owned by a player
	private Player owner;
	private boolean owned;  //is property owned?
	private boolean mortgaged;

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

	private void updateOwners() {
		numOwned = 1;
		for (Railroad r : others){
			if (r.isOwned() && r.owner().equals(owner))
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
				return 25;
			case 2:
				return 50;
			case 3:
				return 100;
			case 4:
				return 200;
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
		if (mortgaged)
			return name + " Mortgaged";
		return name;
	}
}