package monopoly;

/**
 * Created by fjricci on 6/22/2015.
 * An interface for a monopoly player, to allow for different types of players (ie CPU vs Human)
 */
interface Player {
	/* Player stuff */
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

	/* Input stuff */
	boolean inputBool();

	int inputInt();

	int inputDecision(String[] choices);

	Player inputPlayer(Iterable<Player> players, Player notAllowed);
}
