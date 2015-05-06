package monopoly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RandomDeck implements Deck {
	private int SIZE; //store number of cards
	private ArrayList<Card> deck; //store array of cards
	private int current;  //store current card
	private boolean outOfJailFree;

	//create shuffled deck of cards
	public RandomDeck() {
		deck = new ArrayList<>();
		outOfJailFree = true;
	}

	public void initialize(Card[] cards) {
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
		Card card = deck.get(current++);
		if (card.outJailFree() && outOfJailFree)
			outOfJailFree = false;
		else
			card = deck.get(current++); //can't use out of jail free if in use
		return card;
	}

	public void returnOutOfJail() {
		outOfJailFree = true;
	}
}