package monopoly;

public class Card {
	private CardType type;
	private CardAction action;
	private int value;
	private int travel;
	private int travelTo;
	private boolean nearestRail;
	private boolean passGo;
	private int house;
	private int hotel;
	private int eachPlayer;
	private boolean increased;
	private boolean outJailFree;
	private String textA;
	private String textB;
	private String textC;

	public Card(CardType type, int a) {
		if (!type.equals(CardType.CHANCE) && !type.equals(CardType.COMMUNITY))
			throw new IllegalArgumentException("Card type invalid!");
		if (type.equals(CardType.CHANCE))
			chance(a);
		else
			community(a);
	}

	private void community(int a) {
		type = CardType.COMMUNITY;
		switch (a) {
			case 0:
				income();
				break;
			case 1:
				street();
				break;
			case 2:
				inherit();
				break;
			case 3:
				opera();
				break;
			case 4:
				xmas();
				break;
			case 5:
				go();
				break;
			case 6:
				bank();
				break;
			case 7:
				jailFree();
				break;
			case 8:
				hospital();
				break;
			case 9:
				services();
				break;
			case 10:
				jail();
				break;
			case 11:
				school();
				break;
			case 12:
				doctor();
				break;
			case 13:
				stock();
				break;
			case 14:
				life();
				break;
			case 15:
				beauty();
				break;
			default:
				break;
		}
	}

	private void beauty() {
		action = CardAction.BANK_MONEY;
		textA = "You have won second prize in a";
		textB = "beauty contest!";
		textC = "Collect $10";
		value = 10;
	}

	private void life() {
		action = CardAction.BANK_MONEY;
		textA = "Life insurance matures";
		textB = "Collect $100";
		value = 100;
	}

	private void stock() {
		action = CardAction.BANK_MONEY;
		textA = "From sale of stock";
		textB = "You get $45";
		value = 45;
	}

	private void doctor() {
		action = CardAction.BANK_MONEY;
		textA = "Doctors Fee";
		textB = "Pay $50";
		value = -50;
	}

	private void school() {
		action = CardAction.BANK_MONEY;
		textA = "Pay School tax of $150";
		value = -150;
	}

	private void jail() {
		action = CardAction.MOVE_TO;
		textA = "Go to Jail";
		travelTo = 30;
	}

	private void services() {
		action = CardAction.BANK_MONEY;
		textA = "Receive for Services $25";
		value = 25;
	}

	private void hospital() {
		action = CardAction.BANK_MONEY;
		textA = "Pay hospital $100";
		value = -100;
	}

	private void jailFree() {
		action = CardAction.OUT_JAIL;
		textA = "Get out of jail free card";
		outJailFree = true;
	}

	private void bank() {
		action = CardAction.BANK_MONEY;
		textA = "Bank Error in your favor";
		textB = "Collect $200";
		value = 200;
	}

	private void go() {
		action = CardAction.MOVE_TO;
		textA = "Advance to Go";
		textB = "Collect $200";
		travelTo = 0;
		passGo = true;
	}

	private void xmas() {
		action = CardAction.BANK_MONEY;
		textA = "Xmas fund matures";
		textB = "Collect $100";
		value = 100;
	}

	private void opera() {
		action = CardAction.PLAYER_MONEY;
		textA = "Grand Opera Opening";
		textB = "collect $50 from every player";
		eachPlayer = 50;
	}

	private void inherit() {
		action = CardAction.BANK_MONEY;
		textA = "You inherit $100!";
		value = 100;
	}

	private void street() {
		action = CardAction.STREET_REPAIRS;
		textA = "You are assessed for street repairs";
		textB = "$40 per house";
		textC = "$115 per hotel";
		house = -40;
		hotel = -115;
	}

	private void income() {
		action = CardAction.BANK_MONEY;
		textA = "Income Tax Refund";
		textB = "Collect $20";
		value = 20;
	}

	private void chance(int a) {
		type = CardType.CHANCE;
		switch (a) {
			case 0:
				reading();
				break;
			case 1:
				dividend();
				break;
			case 2:
				illinois();
				break;
			case 3:
				loan();
				break;
			case 4:
				jailFree();
				break;
			case 5:
				repairs();
				break;
			case 6:
				railroad();
				break;
			case 7:
				poor();
				break;
			case 8:
				boardwalk();
				break;
			case 9:
				charles();
				break;
			case 10:
				chairman();
				break;
			case 11:
				utility();
				break;
			case 12:
				back();
				break;
			case 13:
				go();
				break;
			case 14:
				jail();
				break;
			case 15:
				railroad();
				break;
			default:
				break;
		}
	}

	private void back() {
		action = CardAction.MOVE;
		textA = "Go back 3 spaces";
		travel = -3;
		passGo = false;
		increased = false;
	}

	private void utility() {
		action = CardAction.MOVE_NEAREST;
		textA = "Advance token to nearest utility";
		textB = "If unowned, you may buy it from the bank";
		textC = "If owned, throw the dice and pay owner a total of "
				+ "10 times the amount thrown";
		nearestRail = false;
		increased = true;
		passGo = false;
	}

	private void chairman() {
		action = CardAction.PLAYER_MONEY;
		textA = "You have been elected chariman of";
		textB = "the board";
		textC = "Pay each player $50";
		eachPlayer = -50;
	}

	private void charles() {
		action = CardAction.MOVE_TO;
		textA = "Advance to St. Charles Place";
		travelTo = 11;
		passGo = true;
		increased = false;
	}

	private void boardwalk() {
		action = CardAction.MOVE_TO;
		textA = "Take a walk on the Boardwalk";
		travelTo = 39;
		passGo = false;
		increased = false;
	}

	private void poor() {
		action = CardAction.BANK_MONEY;
		textA = "Pay poor tax of $15";
		value = -15;
	}

	private void railroad() {
		action = CardAction.MOVE_NEAREST;
		textA = "Advance token to nearest railroad and pay the owner";
		textB = "twice the rental to which he is otherwise entitled.";
		textC = "If railroad is unowned, you may buy it from the bank.";
		nearestRail = true;
		increased = true;
		passGo = false;
	}

	private void repairs() {
		action = CardAction.STREET_REPAIRS;
		textA = "Make general repairs on all your property";
		textB = "Pay $25 for each house";
		textC = "Pay $100 for each hotel";
		house = -25;
		hotel = -100;
	}

	private void loan() {
		action = CardAction.BANK_MONEY;
		textA = "Your building and loan matures";
		textB = "Collect $150";
		value = 150;
	}

	private void illinois() {
		action = CardAction.MOVE_TO;
		textA = "Advance to Illinois Avenue";
		travelTo = 24;
		passGo = false;
		increased = false;
	}

	private void dividend() {
		action = CardAction.BANK_MONEY;
		textA = "Bank pays you dividend of $50";
		value = 50;
	}

	private void reading() {
		action = CardAction.MOVE_TO;
		textA = "Take a ride on the Reading";
		textB = "Railroad";
		textC = "If you pass go collect $200";
		travelTo = 5;
		passGo = true;
		increased = false;
	}

	public int value() {
		return value;
	}

	public int travel() {
		return travel;
	}

	public int travelTo() {
		return travelTo;
	}

	public boolean travelRail() {
		return nearestRail;
	}

	public boolean passGo() {
		return passGo;
	}

	public int house() {
		return house;
	}

	public int hotel() {
		return hotel;
	}

	public int eachPlayer() {
		return eachPlayer;
	}

	public boolean increased() {
		return increased;
	}

	public boolean outJailFree() {
		return outJailFree;
	}

	public String textA() {
		return textA;
	}

	public String textB() {
		return textB;
	}

	public String textC() {
		return textC;
	}

	public CardType type() {
		return type;
	}

	public CardAction action() {
		return action;
	}

	public enum CardType {
		COMMUNITY, CHANCE
	}

	public enum CardAction {
		BANK_MONEY, PLAYER_MONEY, MOVE, MOVE_TO,
		MOVE_NEAREST, STREET_REPAIRS, OUT_JAIL
	}
}