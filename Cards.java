package monopoly;

public class Cards implements Square {
	private final int DECK_SIZE = 16; //16 cards in either type of deck
	private final Deck deck; //store deck of cards
	private String name;
	private int pos;
	private Card.CardType type;

	//construct square of type cards
	public Cards(String name, int pos, Card.CardType type, Deck deck) {
		this.deck = deck;
		if (type != Card.CardType.COMMUNITY && type != Card.CardType.CHANCE)
			throw new IllegalArgumentException("Card type invalid!");
		if (type == Card.CardType.CHANCE)
			chance();
		else
			community();

		this.name = name;
		this.pos = pos;
		this.type = type;
	}

	public Card.CardType type() {
		return type;
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

	public int position() {
		return pos;
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

	//create deck of community chest cards
	private void community() {
		Card[] cards = new Card[DECK_SIZE];

		for (int i = 0; i < DECK_SIZE; i++)
			cards[i] = new Card(Card.CardType.COMMUNITY, i);

		deck.initialize(cards);
	}

	//create deck of chance cards
	private void chance() {
		Card[] cards = new Card[DECK_SIZE];

		for (int i = 0; i < DECK_SIZE; i++)
			cards[i] = new Card(Card.CardType.CHANCE, i);

		deck.initialize(cards);
	}

	//draw next card
	public Card draw() {
		return deck.drawCard();
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

	public String toString() {
		return name;
	}

	public Iterable<Card> cards() {
		return deck.cards();
	}
}