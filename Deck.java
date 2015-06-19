package monopoly;

/**
 * Created by fjricci on 5/4/2015. *
 * Interface for decks and cards
 */
public interface Deck {
	Card drawCard();

	void initialize(Card[] cards);

	void returnOutOfJail();

	Iterable<Card> cards();
}
