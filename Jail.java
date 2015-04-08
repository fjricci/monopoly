package monopoly;

public class Jail implements SquareIf {

    private JailType type;

	public Jail(JailType type) {
		if (type != JailType.VISITING && type != JailType.TO_JAIL)
			throw new IllegalArgumentException("Jail type invalid!");
		this.type = type;
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