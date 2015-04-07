package monopoly;

public class Square
{
	private String name;
	private int position;
	private Object square;
	private boolean ownable;
	private SquareType type;
	public Square(int position)
	{
		this.position = position;
		switch (position)
		{
			case 0:		go();
						break;
			case 1:		mediterranean();
						break;
			case 2:		community();
						break;
			case 3:		baltic();
						break;
			case 4:		income();
						break;
			case 5: 	reading();
						break;
			case 6:		oriental();
						break;
			case 7:		chance();
						break;
			case 8: 	vermont();
						break;
			case 9: 	connecticut();
						break;
			case 10:	jail();
						break;
			case 11:	charles();
						break;
			case 12:	electric();
						break;
			case 13:	states();
						break;
			case 14:	virginia();
						break;
			case 15: 	pennsylvaniaRR();
						break;
			case 16:	james();
						break;
			case 17:	community();
						break;
			case 18: 	tennessee();
						break;
			case 19: 	newYork();
						break;
			case 20:	parking();
						break;
			case 21:	kentucky();
						break;
			case 22:	chance();
						break;
			case 23:	indiana();
						break;
			case 24:	illinois();
						break;
			case 25: 	bAndO();
						break;
			case 26:	atlantic();
						break;
			case 27:	ventor();
						break;
			case 28: 	water();
						break;
			case 29: 	marvin();
						break;
			case 30:	toJail();
						break;
			case 31:	pacific();
						break;
			case 32:	carolina();
						break;
			case 33:	community();
						break;
			case 34:	pennsylvaniaAve();
						break;
			case 35: 	shortLine();
						break;
			case 36:	chance();
						break;
			case 37:	park();
						break;
			case 38: 	luxury();
						break;
			case 39: 	boardwalk();
						break;
			default:	break;
		}
	}

	public int getPos()
	{
	    return position;
	}

	private void luxury()
	{
		name = "Luxury Tax";
		square = new Taxes(75);
		ownable = false;
		type = SquareType.TAXES;
	}

	private void shortLine()
	{
		name = "Short Line";
		square = new Railroad();
		ownable = true;
        type = SquareType.RAILROAD;
	}

	private void toJail()
	{
		name = "Go to Jail";
		square = new Jail(Jail.JailType.TO_JAIL);
		ownable = false;
        type = SquareType.JAIL;
	}

	private void water()
	{
		name = "Water Works";
		square = new Utility();
		ownable = true;
        type = SquareType.UTILITY;
	}

	private void bAndO()
	{
		name = "B & O Railroad";
		square = new Railroad();
		ownable = true;
        type = SquareType.RAILROAD;
	}

	private void pennsylvaniaRR()
	{
		name = "Pennsylvania Railroad";
		square = new Railroad();
		ownable = true;
        type = SquareType.RAILROAD;
	}

	private void electric()
	{
		name = "Electric Company";
		square = new Utility();
		ownable = true;
        type = SquareType.UTILITY;
	}

	private void jail()
	{
		name = "Jail";
		square = new Jail(Jail.JailType.VISITING);
		ownable = false;
        type = SquareType.JAIL;
	}

	private void chance()
	{
		name = "Chance";
		square = new Cards(Card.CardType.CHANCE);
		ownable = false;
        type = SquareType.CARDS;
	}

	private void reading()
	{
		name = "Reading Railroad";
		square = new Railroad();
		ownable = true;
        type = SquareType.RAILROAD;
	}

	private void income()
	{
		name = "Income Tax";
		square = new Taxes(200, 10);
		ownable = false;
        type = SquareType.TAXES;
	}

	private void community()
	{
		name = "Community Chest";
		square = new Cards(Card.CardType.COMMUNITY);
		ownable = false;
        type = SquareType.CARDS;
	}

	private void go()
	{
		name = "Go";
		square = new Inactive();
		ownable = false;
        type = SquareType.INACTIVE;
	}

	private void mediterranean()
	{
		name = "Mediterranean Avenue";
		int rent = 2;
		int oneH = 10;
		int twoH = 30;
		int threeH = 90;
		int fourH = 160;
		int hotel = 250;
		int cost = 60;
		int houses = 50;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void baltic()
	{
		name = "Baltic Avenue";
		int rent = 4;
		int oneH = 20;
		int twoH = 60;
		int threeH = 180;
		int fourH = 320;
		int hotel = 450;
		int cost = 60;
		int houses = 50;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void oriental()
	{
		name = "Oriental Avenue";
		int rent = 6;
		int oneH = 30;
		int twoH = 90;
		int threeH = 270;
		int fourH = 400;
		int hotel = 550;
		int cost = 100;
		int houses = 50;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void vermont()
	{
		name = "Vermont Avenue";
		int rent = 6;
		int oneH = 30;
		int twoH = 90;
		int threeH = 270;
		int fourH = 400;
		int hotel = 550;
		int cost = 100;
		int houses = 50;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void connecticut()
	{
		name = "Connecticut Avenue";
		int rent = 8;
		int oneH = 40;
		int twoH = 100;
		int threeH = 300;
		int fourH = 450;
		int hotel = 600;
		int cost = 120;
		int houses = 50;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void charles()
	{
		name = "St. Charles Place";
		int rent = 10;
		int oneH = 50;
		int twoH = 150;
		int threeH = 450;
		int fourH = 625;
		int hotel = 750;
		int cost = 140;
		int houses = 100;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void states()
	{
		name = "States Avenue";
		int rent = 10;
		int oneH = 50;
		int twoH = 150;
		int threeH = 450;
		int fourH = 625;
		int hotel = 750;
		int cost = 140;
		int houses = 100;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void virginia()
	{
		name = "Virginia Avenue";
		int rent = 12;
		int oneH = 60;
		int twoH = 180;
		int threeH = 500;
		int fourH = 700;
		int hotel = 900;
		int cost = 160;
		int houses = 100;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}
	
	private void james()
	{
		name = "St. James Place";
		int rent = 14;
		int oneH = 70;
		int twoH = 200;
		int threeH = 550;
		int fourH = 750;
		int hotel = 950;
		int cost = 180;
		int houses = 100;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}
	
	private void tennessee()
	{
		name = "Tennessee Avenue";
		int rent = 14;
		int oneH = 70;
		int twoH = 200;
		int threeH = 550;
		int fourH = 750;
		int hotel = 950;
		int cost = 180;
		int houses = 100;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}
	
	private void newYork()
	{
		name = "New York Avenue";
		int rent = 16;
		int oneH = 80;
		int twoH = 220;
		int threeH = 600;
		int fourH = 800;
		int hotel = 1000;
		int cost = 200;
		int houses = 100;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void kentucky()
	{
		name = "Kentucky Avenue";
		int rent = 18;
		int oneH = 90;
		int twoH = 250;
		int threeH = 700;
		int fourH = 875;
		int hotel = 1050;
		int cost = 220;
		int houses = 150;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void indiana()
	{
		name = "Indiana Avenue";
		int rent = 18;
		int oneH = 90;
		int twoH = 250;
		int threeH = 700;
		int fourH = 875;
		int hotel = 1050;
		int cost = 220;
		int houses = 150;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void illinois()
	{
		name = "Illinois Avenue";
		int rent = 20;
		int oneH = 100;
		int twoH = 300;
		int threeH = 750;
		int fourH = 925;
		int hotel = 1100;
		int cost = 240;
		int houses = 150;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void atlantic()
	{
		name = "Atlantic Avenue";
		int rent = 22;
		int oneH = 110;
		int twoH = 330;
		int threeH = 800;
		int fourH = 975;
		int hotel = 1150;
		int cost = 260;
		int houses = 150;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void ventor()
	{
		name = "Ventor Avenue";
		int rent = 22;
		int oneH = 110;
		int twoH = 330;
		int threeH = 800;
		int fourH = 975;
		int hotel = 1150;
		int cost = 260;
		int houses = 150;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void marvin()
	{
		name = "Marvin Gardens";
		int rent = 24;
		int oneH = 120;
		int twoH = 360;
		int threeH = 850;
		int fourH = 1025;
		int hotel = 1200;
		int cost = 280;
		int houses = 150;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void pacific()
	{
		name = "Pacific Avenue";
		int rent = 26;
		int oneH = 130;
		int twoH = 390;
		int threeH = 900;
		int fourH = 1100;
		int hotel = 1275;
		int cost = 300;
		int houses = 200;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void carolina()
	{
		name = "North Carolina Avenue";
		int rent = 26;
		int oneH = 130;
		int twoH = 390;
		int threeH = 900;
		int fourH = 1100;
		int hotel = 1275;
		int cost = 300;
		int houses = 200;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void pennsylvaniaAve()
	{
		name = "Pennsylvania Avenue";
		int rent = 28;
		int oneH = 150;
		int twoH = 450;
		int threeH = 1000;
		int fourH = 1200;
		int hotel = 1400;
		int cost = 320;
		int houses = 200;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void park()
	{
		name = "Park Place";
		int rent = 35;
		int oneH = 175;
		int twoH = 500;
		int threeH = 1100;
		int fourH = 1300;
		int hotel = 1500;
		int cost = 350;
		int houses = 200;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void boardwalk()
	{
		name = "Boardwalk";
		int rent = 50;
		int oneH = 200;
		int twoH = 600;
		int threeH = 1400;
		int fourH = 1700;
		int hotel = 2000;
		int cost = 400;
		int houses = 200;
		square = new Property(rent, oneH, twoH, threeH, fourH,
											hotel, cost, houses);
		ownable = true;
        type = SquareType.PROPERTY;
	}

	private void parking()
	{
		name = "Free Parking";
		square = new Inactive();
		ownable = false;
        type = SquareType.INACTIVE;
	}
	
	public int position()
	{
		return position;
	}
	
	//return square defensively
	public Object square()
	{
		Object newSquare = square;
		return newSquare;
	}
	
	public String getName()
	{
	    return name;
	}
	
	public boolean ownable()
	{
	    return ownable;
	}
	
	public boolean isOwned()
	{
	    boolean owned = false;
	    switch(type)
	    {
	    case JAIL:      break;
        case TAXES:     break;
        case CARDS:     break;
        case INACTIVE:  break;
        case PROPERTY:  Property prop = (Property) square;
                        owned = prop.isOwned();
                        break;
        case RAILROAD:  Railroad rail = (Railroad) square;
                        owned = rail.isOwned();
                        break;
        case UTILITY:   Utility util = (Utility) square;
                        owned = util.isOwned();
                        break;
        default:        break;
	    }

		return owned;
	}
	
	public SquareType type()
	{
	    return type;
	}
	
	public String toString()
	{
	    String string = name;
	    string = string.concat("\n" + "Position: " + position);

		switch (type) {
			case JAIL:      break;
	    case TAXES:	    break;
	    case CARDS:	    break;
        case INACTIVE:  break;
	    case PROPERTY:  string = string.concat("\n" + propString());
	    break;
	    case RAILROAD:  string = string.concat("\n" + railString());
	    break;
	    case UTILITY:   string = string.concat("\n" + utilString());
	    break;
	    default:        break;
	    }

		return string;
	}
	
	private String propString()
	{
	    Property prop = (Property) square;
	    if (!prop.isOwned())
	        return("Unowned");
	    String string = "Owned by ";
	    string = string.concat(prop.owner().toString());
	    return string;
	}
	
	private String railString()
	{
	    Railroad rail = (Railroad) square;
        if (!rail.isOwned())
            return("Unowned");
        String string = "Owned by ";
        string = string.concat(rail.owner().toString());
        return string;
	}
	
	private String utilString()
	{
	    Utility util = (Utility) square;
        if (!util.isOwned())
            return("Unowned");
        String string = "Owned by ";
        string = string.concat(util.owner().toString());
        return string;
	}

	public enum SquareType {
		JAIL, TAXES, CARDS, PROPERTY, RAILROAD, UTILITY, INACTIVE
	}
}