package monopoly;

import java.util.LinkedList;
import java.util.Queue;

public class Board
{
	private final int N = 40; //number of squares on the board (40 for monopoly)
	private Square[] board; //representation of board
	
	//constructor for a new board of squares
	public Board() {
		board = new Square[N];
		//initialize board squares
		for (int i = 0; i < N; i++)
		{
			board[i] = makeSquare(i);
		}
	}
	
	//return an array of the squares on the board
	public Square[] getBoard() {
	    return board;
	}
	
	//return a queue of the squares on the board
	public Queue<Square> squares() {
	    Queue<Square> queue = new LinkedList<Square>();
	    for (int i = 0; i < N; i++)
	        queue.add(board[i]);
	    return queue;
	}
	
	//return the number of squares on the board
	public int getSize() {
	    return N;
	}

	private Square makeSquare(int pos) {
		switch (pos) {
			case 0:
				return go("Go", pos);
			case 1:
				return mediterranean("Mediterranean Avenue", pos);
			case 2:
				return community("Community Chest", pos);
			case 3:
				return baltic("Baltic Avenue", pos);
			case 4:
				return income("Income Tax", pos);
			case 5:
				return reading("Reading Railroad", pos);
			case 6:
				return oriental("Oriental Avenue", pos);
			case 7:
				return chance("Chance", pos);
			case 8:
				return vermont("Vermont Avenue", pos);
			case 9:
				return connecticut("Connecticut Avenue", pos);
			case 10:
				return jail("Jail", pos);
			case 11:
				return charles("St. Charles Place", pos);
			case 12:
				return electric("Electric Company", pos);
			case 13:
				return states("States Avenue", pos);
			case 14:
				return virginia("Virginia Avenue", pos);
			case 15:
				return pennsylvaniaRR("Pennsylvania Railroad", pos);
			case 16:
				return james("St. James Place", pos);
			case 17:
				return community("Community Chest", pos);
			case 18:
				return tennessee("Tennessee Avenue", pos);
			case 19:
				return newYork("New York Avenue", pos);
			case 20:
				return parking("Free Parking", pos);
			case 21:
				return kentucky("Kentucky Avenue", pos);
			case 22:
				return chance("Chance", pos);
			case 23:
				return indiana("Indiana Avenue", pos);
			case 24:
				return illinois("Illinois Avenue", pos);
			case 25:
				return bAndO("B & O Railroad", pos);
			case 26:
				return atlantic("Atlantic Avenue", pos);
			case 27:
				return ventor("Ventor Avenue", pos);
			case 28:
				return water("Water Works", pos);
			case 29:
				return marvin("Marvin Gardens", pos);
			case 30:
				return toJail("Go to Jail", pos);
			case 31:
				return pacific("Pacific Avenue", pos);
			case 32:
				return carolina("North Carolina Avenue", pos);
			case 33:
				return community("Community Chest", pos);
			case 34:
				return pennsylvaniaAve("Pennsylvania Avenue", pos);
			case 35:
				return shortLine("Short Line", pos);
			case 36:
				return chance("Chance", pos);
			case 37:
				return park("Park Place", pos);
			case 38:
				return luxury("Luxury Tax", pos);
			case 39:
				return boardwalk("Boardwalk", pos);
			default:
				return null;
		}
	}

	private Square luxury(String name, int pos) {
		Square square = new Taxes(name, pos, 75);
		return square;
	}

	private Square shortLine(String name, int pos) {
		Square square = new Railroad(name, pos);
		return square;
	}

	private Square toJail(String name, int pos) {
		Square square = new Jail(name, pos, Jail.JailType.TO_JAIL);
		return square;
	}

	private Square water(String name, int pos) {
		Square square = new Utility(name, pos);
		return square;
	}

	private Square bAndO(String name, int pos) {
		Square square = new Railroad(name, pos);
		return square;
	}

	private Square pennsylvaniaRR(String name, int pos) {
		Square square = new Railroad(name, pos);
		return square;
	}

	private Square electric(String name, int pos) {
		Square square = new Utility(name, pos);
		return square;
	}

	private Square jail(String name, int pos) {
		Square square = new Jail(name, pos, Jail.JailType.VISITING);
		return square;
	}

	private Square chance(String name, int pos) {
		Square square = new Cards(name, pos, Card.CardType.CHANCE);
		return square;
	}

	private Square reading(String name, int pos) {
		Square square = new Railroad(name, pos);
		return square;
	}

	private Square income(String name, int pos) {
		Square square = new Taxes(name, pos, 200, 10);
		return square;
	}

	private Square community(String name, int pos) {
		Square square = new Cards(name, pos, Card.CardType.COMMUNITY);
		return square;
	}

	private Square go(String name, int pos) {
		Square square = new Inactive(name, pos);
		return square;
	}

	private Square mediterranean(String name, int pos) {
		int rent = 2;
		int oneH = 10;
		int twoH = 30;
		int threeH = 90;
		int fourH = 160;
		int hotel = 250;
		int cost = 60;
		int houses = 50;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square baltic(String name, int pos) {
		int rent = 4;
		int oneH = 20;
		int twoH = 60;
		int threeH = 180;
		int fourH = 320;
		int hotel = 450;
		int cost = 60;
		int houses = 50;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square oriental(String name, int pos) {
		int rent = 6;
		int oneH = 30;
		int twoH = 90;
		int threeH = 270;
		int fourH = 400;
		int hotel = 550;
		int cost = 100;
		int houses = 50;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square vermont(String name, int pos) {
		int rent = 6;
		int oneH = 30;
		int twoH = 90;
		int threeH = 270;
		int fourH = 400;
		int hotel = 550;
		int cost = 100;
		int houses = 50;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square connecticut(String name, int pos) {
		int rent = 8;
		int oneH = 40;
		int twoH = 100;
		int threeH = 300;
		int fourH = 450;
		int hotel = 600;
		int cost = 120;
		int houses = 50;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square charles(String name, int pos) {
		int rent = 10;
		int oneH = 50;
		int twoH = 150;
		int threeH = 450;
		int fourH = 625;
		int hotel = 750;
		int cost = 140;
		int houses = 100;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square states(String name, int pos) {
		int rent = 10;
		int oneH = 50;
		int twoH = 150;
		int threeH = 450;
		int fourH = 625;
		int hotel = 750;
		int cost = 140;
		int houses = 100;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square virginia(String name, int pos) {
		int rent = 12;
		int oneH = 60;
		int twoH = 180;
		int threeH = 500;
		int fourH = 700;
		int hotel = 900;
		int cost = 160;
		int houses = 100;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square james(String name, int pos) {
		int rent = 14;
		int oneH = 70;
		int twoH = 200;
		int threeH = 550;
		int fourH = 750;
		int hotel = 950;
		int cost = 180;
		int houses = 100;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square tennessee(String name, int pos) {
		int rent = 14;
		int oneH = 70;
		int twoH = 200;
		int threeH = 550;
		int fourH = 750;
		int hotel = 950;
		int cost = 180;
		int houses = 100;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square newYork(String name, int pos) {
		int rent = 16;
		int oneH = 80;
		int twoH = 220;
		int threeH = 600;
		int fourH = 800;
		int hotel = 1000;
		int cost = 200;
		int houses = 100;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square kentucky(String name, int pos) {
		int rent = 18;
		int oneH = 90;
		int twoH = 250;
		int threeH = 700;
		int fourH = 875;
		int hotel = 1050;
		int cost = 220;
		int houses = 150;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square indiana(String name, int pos) {
		int rent = 18;
		int oneH = 90;
		int twoH = 250;
		int threeH = 700;
		int fourH = 875;
		int hotel = 1050;
		int cost = 220;
		int houses = 150;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square illinois(String name, int pos) {
		int rent = 20;
		int oneH = 100;
		int twoH = 300;
		int threeH = 750;
		int fourH = 925;
		int hotel = 1100;
		int cost = 240;
		int houses = 150;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square atlantic(String name, int pos) {
		int rent = 22;
		int oneH = 110;
		int twoH = 330;
		int threeH = 800;
		int fourH = 975;
		int hotel = 1150;
		int cost = 260;
		int houses = 150;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square ventor(String name, int pos) {
		int rent = 22;
		int oneH = 110;
		int twoH = 330;
		int threeH = 800;
		int fourH = 975;
		int hotel = 1150;
		int cost = 260;
		int houses = 150;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square marvin(String name, int pos) {
		int rent = 24;
		int oneH = 120;
		int twoH = 360;
		int threeH = 850;
		int fourH = 1025;
		int hotel = 1200;
		int cost = 280;
		int houses = 150;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square pacific(String name, int pos) {
		int rent = 26;
		int oneH = 130;
		int twoH = 390;
		int threeH = 900;
		int fourH = 1100;
		int hotel = 1275;
		int cost = 300;
		int houses = 200;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square carolina(String name, int pos) {
		int rent = 26;
		int oneH = 130;
		int twoH = 390;
		int threeH = 900;
		int fourH = 1100;
		int hotel = 1275;
		int cost = 300;
		int houses = 200;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square pennsylvaniaAve(String name, int pos) {
		int rent = 28;
		int oneH = 150;
		int twoH = 450;
		int threeH = 1000;
		int fourH = 1200;
		int hotel = 1400;
		int cost = 320;
		int houses = 200;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square park(String name, int pos) {
		int rent = 35;
		int oneH = 175;
		int twoH = 500;
		int threeH = 1100;
		int fourH = 1300;
		int hotel = 1500;
		int cost = 350;
		int houses = 200;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square boardwalk(String name, int pos) {
		int rent = 50;
		int oneH = 200;
		int twoH = 600;
		int threeH = 1400;
		int fourH = 1700;
		int hotel = 2000;
		int cost = 400;
		int houses = 200;
		Square square = new Property(name, pos, rent, oneH, twoH,
		                             threeH, fourH, hotel, cost, houses);
		return square;
	}

	private Square parking(String name, int pos) {
		Square square = new Inactive(name, pos);
		return square;
	}

}