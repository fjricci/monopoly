package monopoly;

/**
 * Created by fjricci on 6/22/2015.
 * An interface for a monopoly player, to allow for different types of players (ie CPU vs Human)
 */
public interface Player {
	void addProperty(Square square);

	void move(int numSpaces);

	void moveTo(int pos);

	int position();

	Iterable<Square> properties();

	String name();

	int getMoney();

	void excMoney(int money);

	void toJail();

	boolean stayJail();

	void sellProp(Square sq);

	void leaveJail();

	boolean inJail();

	void addJailFree(boolean chance);

	boolean useJailFree();

	int numJailFree();

	int getAssets();
}
