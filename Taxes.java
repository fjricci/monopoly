package monopoly;

public class Taxes implements Square {
	private final int fixTax;  //fixed tax cost
	private final double varTax;  //percentage tax rate for variable tax

	private final String name;
	private final int pos;

	//constructor if no variable tax option
	public Taxes(int pos, boolean income) {
		if (income) {
			fixTax = 200;
			varTax = 10;
			this.name = "Income Tax";
		} else {
			fixTax = 75;
			varTax = 0;
			this.name = "Luxury Tax";
		}
		this.pos = pos;
	}

	public boolean isOwned() {
		return false;
	}

	public int mortgage() {
		return 0;
	}

	public int position() {
		return pos;
	}

	public String name() {
		return name;
	}

	public boolean isOwnable() {
		return false;
	}

	public boolean isMortgaged() {
		return false;
	}

	public int mortgageCost() {
		return 0;
	}

	//return fixed rate tax owed
	public int tax() {
		return fixTax;
	}

	//return variable tax owed, based on value of player's assets
	public int tax(int value) {
		//if no variable tax option, return fixed tax value
		if (varTax == 0)
			return fixTax;
		double percent = varTax / 100;
		return (int) (value * percent);
	}

	public int cost() {
		return 0;
	}

	public void purchase(Player player) {
	}

	public int rent(int val) {
		return 0;
	}

	public Player owner() {
		return null;
	}
}