package monopoly;

public class Taxes implements SquareIf {
	private int fixTax;  //fixed tax cost
	private double varTax;  //percentage tax rate for variable tax

	//constructor if no variable tax option
	public Taxes(int tax)
	{
		fixTax = tax;
		varTax = 0;
	}
	
	//constructor with variable tax rate
	public Taxes(int tax, int rate)
	{
		fixTax = tax;
		varTax = rate;
	}
	
	//return fixed rate tax owed
	public int tax()
	{
		return fixTax;
	}
	
	//return variable tax owed, based on value of player's assets
	public int tax(int value)
	{
		//if no variable tax option, return fixed tax value
		if (varTax == 0)
			return fixTax;
		double percent = varTax / 100;
		return (int) (value*percent);
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