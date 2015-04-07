package monopoly;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Deck
{
	private final int SIZE; //store number of cards
	private Card[] deck; //store array of cards
	private int current;  //store current card
	
	//create shuffled deck of cards
	public Deck(Card[] cards)
	{
		SIZE = cards.length;
		deck = new Card[SIZE];
		for (int i = 0; i < SIZE; i++)
			 deck[i] = cards[i];
		Collections.shuffle(Arrays.asList(deck));
	}

	//draw next card from deck
	public Card drawCard()
	{
		if (current == SIZE)
		{
			Collections.shuffle(Arrays.asList(deck));
			current = 0;
		}
		return deck[current++];
	}
	
	//return iterable queue of cards
	public Queue<Card> cards()
	{
	    Queue<Card> queue = new LinkedList<Card>();
	    for (int i = 0; i < SIZE; i++)
	        queue.add(deck[i]);
	    return queue;
	}
}