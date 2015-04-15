package monopoly;

import monopoly.Player.PlayerType;

public class Property implements Square {
	//costs of rent for all possible property states
	private int rent;
	private int oneH;
	private int twoH;
	private int threeH;
	private int fourH;
	private int hotel;
	
	private int value; //cost to purchase property
	private int houses; //cost to purchase one house on property
	
	private int buildings;  //building status
	private boolean monopoly; //does one player own all properties in set?
	private boolean owned;  //is property owned?
	private boolean mortgaged; //is property mortgaged
	private PlayerType ownerType;
	private Player owner;

	private int pos;
	private String name;
	
	//construct property, given its rents
	public Property(String name, int pos, int rent, int oneH, int twoH,
	                int threeH, int fourH, int hotel, int value, int houses)
	{
		this.rent = rent;
		this.oneH = oneH;
		this.twoH = twoH;
		this.threeH = threeH;
		this.fourH = fourH;
		this.hotel = hotel;
		this.value = value;
		this.houses = houses;
		buildings = 0;
		monopoly = false;
		owned = false;

		this.pos = pos;
		this.name = name;
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
	public void purchase(Player player)
	{
		owned = true;
		owner = player;
		ownerType = player.getPlayer();
	}
	
	public boolean isOwned()
	{
	    return owned;
	}
	
	//update building status by integer input
	public void build(int a)
	{
		buildings += a;
		if (buildings > 5)
			throw new IllegalArgumentException("Cannot build past hotel!");
		if (buildings < 0)
			throw new IllegalArgumentException("Cannot build negative "
			                                                 + "buildings!");
	}
	
	//switch status of monopoly
	public void monopoly()
	{
		monopoly = !monopoly;
	}
	
	//cost to purchase property
	public int cost()
	{
		return value;
	}
	
	//return cost to purchase a given number of houses
	public int buyHouses(int n)
	{
		return n*houses;
	}
	
	//return cost from selling a given number of houses
	public int sellHouses(int n)
	{
		return n*houses/2;
	}
	
	//return number of buildings owned
	public int numHouses()
	{
	    return buildings;
	}
	
	//return cost to purchase one house
	public int houseCost()
	{
	    return houses;
	}
	
	//return amount owed
	public int rent(int val)
	{
		if (!owned)
			return 0;
		switch(buildings)
		{
			case 0: if (monopoly) return 2*rent;
					return rent;
			case 1:	return oneH;
			case 2: return twoH;
			case 3: return threeH;
			case 4: return fourH;
			case 5: return hotel;
			default: return 0;
		}
	}

	public PlayerType ownerType() {
        return ownerType;
    }

	public Player owner() {
		return owner;
    }

    //mortgage property
	public int mortgage() {
		if (mortgaged)
        {
            mortgaged = false;
            return (int) Math.round((value / 2) * 1.1);
        } else
        {
            mortgaged = true;
            return value / 2;
        }
    }

	public boolean isMortgaged() {
		return mortgaged;
    }

	public int mortgageCost() {
		return value / 2;
    }
}