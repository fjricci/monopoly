package monopoly;

public class Jail implements Square {

	private final int pos;
	private JailType type;
	private String name;

	public Jail(String name, int pos, JailType type) {
		if (type != JailType.VISITING && type != JailType.TO_JAIL)
			throw new IllegalArgumentException("Jail type invalid!");
		this.type = type;
		this.name = name;
		this.pos = pos;
	}

	public String name() {
		return name;
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

	public boolean isOwnable() {
		return false;
	}

	public boolean isMortgaged() {
		return false;
	}

	public int mortgageCost() {
		return 0;
	}

	public JailType getType() {
		return type;
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

	public enum JailType {
		VISITING, TO_JAIL
	}
}