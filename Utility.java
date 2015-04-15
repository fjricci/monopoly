package monopoly;

public class Utility implements Square {
    //rent multiplier, given number of utilities owned by a player
	private final int ONE = 4;
	private final int TWO = 10;
	private final int COST = 150; //cost to purchase utility

    private Player.PlayerType ownerType; //stores utility owner name enum
    private Player owner;     //stores utility owner
    private boolean owned;  //is utility owned?
	
	private int numOwned; //number of utilities owned by a player
	private boolean mortgaged; //is property mortgaged?

    private String name;
    private int pos;

	//utility constructor
    public Utility(String name, int pos) {
        numOwned = 0;
		mortgaged = false;
        this.name = name;
        this.pos = pos;
    }

    public int position() {
        return pos;
    }

    public String name() {
        return name;
    }

    public boolean isOwnable() {
        return true;
    }
    
    //update status of property to owned
    public void purchase(Player player) {
        owned = true;
        owner = player;
        ownerType = player.getPlayer();
        numOwned = 1;
        player.properties().stream().filter(s -> s instanceof Utility).forEach(s -> numOwned++);
    }
	
	//update number of utilities owned by a player
	public void owned(int owned)
	{
		if (owned < 0)
			throw new IllegalArgumentException("Cannot own "
			                                        + "negative properties!");
		if (owned > 2)
			throw new IllegalArgumentException("Cannot own more "
			                                         + "than two utilities!");
		numOwned = owned;
	}
	
	//return rent on utility, given a roll
    public int rent(int roll) {
        if (roll < 2 || roll > 12)
			throw new IllegalArgumentException("Invalid Roll!");
		
		switch(numOwned)
		{
			case 1:	return ONE*roll;
			case 2: return TWO*roll;
			default: return 0;
		}
	}
	
	//return total utilities owned by player owning this utility
	public boolean isOwned()
	{
	    return owned;
	}
    
	//return owner name enum
    public Player.PlayerType ownerType()
    {
        return ownerType;
    }
    
    //return player object of owner
    public Player owner()
    {
        return owner;
    }

    //return cost to purchase utility
    public int cost() {
        return COST;
    }
    
    //mortgage property
    public int mortgage() {
        if (mortgaged)
        {
            mortgaged = false;
            return (int) Math.round((COST / 2) * 1.1);
        }
        else
        {
            mortgaged = true;
            return COST / 2;
        }
    }

    public boolean isMortgaged() {
        return mortgaged;
    }

    public int mortgageCost() {
        return COST / 2;
    }

}