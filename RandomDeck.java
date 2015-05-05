package monopoly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RandomDeck implements Deck {
	private final int SIZE; //store number of cards
	private ArrayList<Card> deck; //store array of cards
	private int current;  //store current card

	//create shuffled deck of cards
	public RandomDeck(Card[] cards) {
		deck = new ArrayList<>();
		SIZE = cards.length;
		deck.addAll(Arrays.asList(cards));
		Collections.shuffle(deck);
	}

	//draw next card from deck
	public Card drawCard() {
		if (current == SIZE) {
			Collections.shuffle(deck);
			current = 0;
		}
		return deck.get(current++);
	}
}