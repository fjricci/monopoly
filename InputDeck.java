package monopoly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by fjricci on 5/4/2015.
 * Manual card input
 */
public class InputDeck implements Deck {
	private final int SIZE; //store number of cards
	private ArrayList<Card> deck; //store array of cards
	private Input input;  //store current card

	//create shuffled deck of cards
	public InputDeck(Card[] cards) {
		deck = new ArrayList<>();
		SIZE = cards.length;
		deck.addAll(Arrays.asList(cards));
		Collections.shuffle(deck);
		input = new Input();
	}

	//draw next card from deck
	public Card drawCard() {
		System.out.println("Please select appropriate card.");
		for (int i = 0; i < SIZE; i++) {
			Card card = deck.get(i);
			System.out.println(i + ") " + card.textA());
		}

		int card = -1;
		while (card < 0) {
			card = input.inputInt();
			if (card < 0 || card >= SIZE) {
				System.out.println("Invalid decision. Enter a valid number.");
				card = -1;
			}
		}

		return deck.get(card);
	}
}
