package monopoly;

import java.util.LinkedList;
import java.util.Queue;

public class Board
{
	private final int N = 40; //number of squares on the board (40 for monopoly)
	private Square[] board; //representation of board
	
	//constructor for a new board of squares
	public Board()
	{
		board = new Square[N];
		//initialize board squares
		for (int i = 0; i < N; i++)
		{
			board[i] = new Square(i);
		}
	}
	
	//return an array of the squares on the board
	public Square[] getBoard()
	{
	    return board;
	}
	
	//return a queue of the squares on the board
	public Queue<Square> squares()
	{
	    Queue<Square> queue = new LinkedList<Square>();
	    for (int i = 0; i < N; i++)
	        queue.add(board[i]);
	    return queue;
	}
	
	//return the number of squares on the board
	public int getSize()
	{
	    return N;
	}
}