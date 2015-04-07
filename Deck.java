package monopoly;

import java.util.LinkedList;
import java.util.Queue;

public class Deck
{
	private Card[] deck; //store array of cards
	private int current;  //store current card
	private final int SIZE; //store number of cards
	
	//create shuffled deck of cards
	public Deck(Card[] cards)
	{
		SIZE = cards.length;
		deck = new Card[SIZE];
		for (int i = 0; i < SIZE; i++)
			 deck[i] = cards[i];
		Shuffle.shuffle(deck);
	}

	//draw next card from deck
	public Card drawCard()
	{
		if (current == SIZE)
		{
			Shuffle.shuffle(deck);
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