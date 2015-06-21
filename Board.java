package monopoly;

class Board {
	private final int N = 41;
	private final Square[] board; //representation of board
	private final Deck chance;
	private final Deck community;

	//constructor for a new board of squares
	public Board(Deck chance, Deck community, boolean deterministic) {
		board = new Square[N];
		this.chance = chance;
		this.community = community;
		//initialize board squares
		for (int i = 0; i < N; i++)
			board[i] = makeSquare(i, deterministic);

		makeGroups();
		makeRail();
		makeUtil();
	}

	public int size() {
		return N;
	}

	private Property property(String name) {
		for (Square sq : board) {
			if (sq instanceof Property && sq.name().equals(name))
				return (Property) sq;
		}

		return null;
	}

	public Square square(int pos) {
		return board[pos];
	}

	//return an array of the squares on the board
	public Square[] getBoard() {
		return board;
	}

	private Square makeSquare(int pos, boolean deterministic) {
		switch (pos) {
			case 0:
				return go(pos);
			case 1:
				return mediterranean(pos);
			case 2:
				return community(pos);
			case 3:
				return baltic(pos);
			case 4:
				return income(pos);
			case 5:
				return reading(pos);
			case 6:
				return oriental(pos);
			case 7:
				return chance(pos);
			case 8:
				return vermont(pos);
			case 9:
				return connecticut(pos);
			case 10:
				return visiting(pos);
			case 11:
				return charles(pos);
			case 12:
				return electric(pos, deterministic);
			case 13:
				return states(pos);
			case 14:
				return virginia(pos);
			case 15:
				return pennsylvaniaRR(pos);
			case 16:
				return james(pos);
			case 17:
				return community(pos);
			case 18:
				return tennessee(pos);
			case 19:
				return newYork(pos);
			case 20:
				return parking(pos);
			case 21:
				return kentucky(pos);
			case 22:
				return chance(pos);
			case 23:
				return indiana(pos);
			case 24:
				return illinois(pos);
			case 25:
				return bAndO(pos);
			case 26:
				return atlantic(pos);
			case 27:
				return ventor(pos);
			case 28:
				return water(pos, deterministic);
			case 29:
				return marvin(pos);
			case 30:
				return toJail(pos);
			case 31:
				return pacific(pos);
			case 32:
				return carolina(pos);
			case 33:
				return community(pos);
			case 34:
				return pennsylvaniaAve(pos);
			case 35:
				return shortLine(pos);
			case 36:
				return chance(pos);
			case 37:
				return park(pos);
			case 38:
				return luxury(pos);
			case 39:
				return boardwalk(pos);
			case 40:
				return jail(pos);
			default:
				return null;
		}
	}

	private void makeGroups() {
		makeGroup("Mediterranean Avenue", "Baltic Avenue");
		makeGroup("Oriental Avenue", "Vermont Avenue", "Connecticut Avenue");
		makeGroup("St. Charles Place", "States Avenue", "Virginia Avenue");
		makeGroup("St. James Place", "Tennessee Avenue", "New York Avenue");
		makeGroup("Kentucky Avenue", "Indiana Avenue", "Illinois Avenue");
		makeGroup("Atlantic Avenue", "Ventor Avenue", "Marvin Gardens");
		makeGroup("Pennsylvania Avenue", "North Carolina Avenue", "Pacific Avenue");
		makeGroup("Park Place", "Boardwalk");
	}

	private void makeRail() {
		Railroad a = (Railroad) square(5);
		Railroad b = (Railroad) square(15);
		Railroad c = (Railroad) square(25);
		Railroad d = (Railroad) square(35);

		a.createGroup(b, c, d);
		b.createGroup(a, c, d);
		c.createGroup(a, b, d);
		d.createGroup(a, b, c);
	}

	private void makeUtil() {
		Utility a = (Utility) square(12);
		Utility b = (Utility) square(28);

		a.setOther(b);
		b.setOther(a);
	}

	private void makeGroup(String nameA, String nameB) {
		makeGroup(nameA, nameB, null);
	}

	private void makeGroup(String nameA, String nameB, String nameC) {
		Property propA = property(nameA);
		Property propB = property(nameB);
		Property propC = null;
		if (nameC != null)
			propC = property(nameC);

		if (propA == null || propB == null)
			throw new RuntimeException("Bad property");

		propA.setGroup(propB, propC);
		propB.setGroup(propA, propC);
		if (propC != null)
			propC.setGroup(propA, propB);
	}

	private Square luxury(int pos) {
		return new Taxes(pos, false);
	}

	private Square shortLine(int pos) {
		return new Railroad("Short Line", pos);
	}

	private Square toJail(int pos) {
		return new Jail("Go to Jail", pos, Jail.JailType.TO_JAIL);
	}

	private Square water(int pos, boolean deterministic) {
		return new Utility("Water Works", pos, deterministic);
	}

	private Square bAndO(int pos) {
		return new Railroad("B & O Railroad", pos);
	}

	private Square pennsylvaniaRR(int pos) {
		return new Railroad("Pennsylvania Railroad", pos);
	}

	private Square electric(int pos, boolean deterministic) {
		return new Utility("Electric Company", pos, deterministic);
	}

	private Square visiting(int pos) {
		return new Jail("Just Visiting", pos, Jail.JailType.VISITING);
	}

	private Square jail(int pos) {
		return new Jail("In Jail", pos, Jail.JailType.IN_JAIL);
	}

	private Square chance(int pos) {
		return new Cards("Chance", pos, Card.CardType.CHANCE, chance);
	}

	private Square reading(int pos) {
		return new Railroad("Reading Railroad", pos);
	}

	private Square income(int pos) {
		return new Taxes(pos, true);
	}

	private Square community(int pos) {
		return new Cards("Community Chest", pos, Card.CardType.COMMUNITY, community);
	}

	private Square go(int pos) {
		return new Inactive("Go", pos);
	}

	private Square mediterranean(int pos) {
		int rent = 2;
		int oneH = 10;
		int twoH = 30;
		int threeH = 90;
		int fourH = 160;
		int hotel = 250;
		int cost = 60;
		int houses = 50;
		return new Property("Mediterranean Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square baltic(int pos) {
		int rent = 4;
		int oneH = 20;
		int twoH = 60;
		int threeH = 180;
		int fourH = 320;
		int hotel = 450;
		int cost = 60;
		int houses = 50;
		return new Property("Baltic Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square oriental(int pos) {
		int rent = 6;
		int oneH = 30;
		int twoH = 90;
		int threeH = 270;
		int fourH = 400;
		int hotel = 550;
		int cost = 100;
		int houses = 50;
		return new Property("Oriental Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square vermont(int pos) {
		int rent = 6;
		int oneH = 30;
		int twoH = 90;
		int threeH = 270;
		int fourH = 400;
		int hotel = 550;
		int cost = 100;
		int houses = 50;
		return new Property("Vermont Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square connecticut(int pos) {
		int rent = 8;
		int oneH = 40;
		int twoH = 100;
		int threeH = 300;
		int fourH = 450;
		int hotel = 600;
		int cost = 120;
		int houses = 50;
		return new Property("Connecticut Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square charles(int pos) {
		int rent = 10;
		int oneH = 50;
		int twoH = 150;
		int threeH = 450;
		int fourH = 625;
		int hotel = 750;
		int cost = 140;
		int houses = 100;
		return new Property("St. Charles Place", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square states(int pos) {
		int rent = 10;
		int oneH = 50;
		int twoH = 150;
		int threeH = 450;
		int fourH = 625;
		int hotel = 750;
		int cost = 140;
		int houses = 100;
		return new Property("States Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square virginia(int pos) {
		int rent = 12;
		int oneH = 60;
		int twoH = 180;
		int threeH = 500;
		int fourH = 700;
		int hotel = 900;
		int cost = 160;
		int houses = 100;
		return new Property("Virginia Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square james(int pos) {
		int rent = 14;
		int oneH = 70;
		int twoH = 200;
		int threeH = 550;
		int fourH = 750;
		int hotel = 950;
		int cost = 180;
		int houses = 100;
		return new Property("St. James Place", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square tennessee(int pos) {
		int rent = 14;
		int oneH = 70;
		int twoH = 200;
		int threeH = 550;
		int fourH = 750;
		int hotel = 950;
		int cost = 180;
		int houses = 100;
		return new Property("Tennessee Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square newYork(int pos) {
		int rent = 16;
		int oneH = 80;
		int twoH = 220;
		int threeH = 600;
		int fourH = 800;
		int hotel = 1000;
		int cost = 200;
		int houses = 100;
		return new Property("New York Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square kentucky(int pos) {
		int rent = 18;
		int oneH = 90;
		int twoH = 250;
		int threeH = 700;
		int fourH = 875;
		int hotel = 1050;
		int cost = 220;
		int houses = 150;
		return new Property("Kentucky Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square indiana(int pos) {
		int rent = 18;
		int oneH = 90;
		int twoH = 250;
		int threeH = 700;
		int fourH = 875;
		int hotel = 1050;
		int cost = 220;
		int houses = 150;
		return new Property("Indiana Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square illinois(int pos) {
		int rent = 20;
		int oneH = 100;
		int twoH = 300;
		int threeH = 750;
		int fourH = 925;
		int hotel = 1100;
		int cost = 240;
		int houses = 150;
		return new Property("Illinois Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square atlantic(int pos) {
		int rent = 22;
		int oneH = 110;
		int twoH = 330;
		int threeH = 800;
		int fourH = 975;
		int hotel = 1150;
		int cost = 260;
		int houses = 150;
		return new Property("Atlantic Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square ventor(int pos) {
		int rent = 22;
		int oneH = 110;
		int twoH = 330;
		int threeH = 800;
		int fourH = 975;
		int hotel = 1150;
		int cost = 260;
		int houses = 150;
		return new Property("Ventor Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square marvin(int pos) {
		int rent = 24;
		int oneH = 120;
		int twoH = 360;
		int threeH = 850;
		int fourH = 1025;
		int hotel = 1200;
		int cost = 280;
		int houses = 150;
		return new Property("Marvin Gardens", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square pacific(int pos) {
		int rent = 26;
		int oneH = 130;
		int twoH = 390;
		int threeH = 900;
		int fourH = 1100;
		int hotel = 1275;
		int cost = 300;
		int houses = 200;
		return new Property("Pacific Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square carolina(int pos) {
		int rent = 26;
		int oneH = 130;
		int twoH = 390;
		int threeH = 900;
		int fourH = 1100;
		int hotel = 1275;
		int cost = 300;
		int houses = 200;
		return new Property("North Carolina Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square pennsylvaniaAve(int pos) {
		int rent = 28;
		int oneH = 150;
		int twoH = 450;
		int threeH = 1000;
		int fourH = 1200;
		int hotel = 1400;
		int cost = 320;
		int houses = 200;
		return new Property("Pennsylvania Avenue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square park(int pos) {
		int rent = 35;
		int oneH = 175;
		int twoH = 500;
		int threeH = 1100;
		int fourH = 1300;
		int hotel = 1500;
		int cost = 350;
		int houses = 200;
		return new Property("Park Place", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square boardwalk(int pos) {
		int rent = 50;
		int oneH = 200;
		int twoH = 600;
		int threeH = 1400;
		int fourH = 1700;
		int hotel = 2000;
		int cost = 400;
		int houses = 200;
		return new Property("Boardwalk", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Square parking(int pos) {
		return new Inactive("Free Parking", pos);
	}

}