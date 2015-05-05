package monopoly;

import monopoly.Player.PlayerType;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class Property implements Square {
	//costs of rent for all possible property states
	private final int rent;
	private final int oneH;
	private final int twoH;
	private final int threeH;
	private final int fourH;
	private final int hotel;

	private final int value; //cost to purchase property
	private final int houses; //cost to purchase one house on property
	private final int pos;
	private final String name;
	private int buildings;  //building status
	private boolean monopoly; //does one player own all properties in set?
	private boolean owned;  //is property owned?
	private boolean mortgaged; //is property mortgaged
	private PlayerType ownerType;
	private Player owner;
	private Property groupA;
	private Property groupB;

	//construct property, given its rents
	public Property(String name, int pos, int rent, int oneH, int twoH, int threeH, int fourH,
	                int hotel, int value, int houses)
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

	public void setGroup(Property groupA, Property groupB) {
		this.groupA = groupA;
		this.groupB = groupB;
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

		updateMonopoly(player);
	}

	private void updateMonopoly(Player player) {
		boolean a = false;
		boolean b = false;

		if (groupB == null)
			b = true;

		Queue<Property> props = player.properties().stream().filter(square -> square instanceof Property).map(
				square -> (Property) square).collect(Collectors.toCollection(LinkedList::new));

		for (Property prop : props) {
			if (prop.name().equals(groupA.name()))
				a = true;
			if (groupB != null && prop.name().equals(groupB.name()))
				b = true;
		}

		if (a && b) {
			setMonopoly();
			groupA.setMonopoly();
			if (groupB != null)
				groupB.setMonopoly();
		} else {
			breakMonopoly();
			groupA.breakMonopoly();
			if (groupB != null)
				groupB.breakMonopoly();
		}
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
			throw new IllegalArgumentException("Cannot build negative buildings!");
	}
	
	//switch status of monopoly
	public boolean monopoly()
	{
		return monopoly;
	}
	
	//cost to purchase property
	public int cost()
	{
		return value;
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

	public void setMonopoly() {
		monopoly = true;
	}

	public void breakMonopoly() {
		monopoly = false;
	}

	public boolean groupBuild() {
		int aDiff = groupA.numHouses() - numHouses();
		boolean aOkay = aDiff == 0 || aDiff == 1;
		if (groupB == null)
			return aOkay;

		int bDiff = groupB.numHouses() - numHouses();
		boolean bOkay = bDiff == 0 || bDiff == 1;

		return aOkay && bOkay;
	}

	public boolean groupSell() {
		int aDiff = groupA.numHouses() - numHouses();
		boolean aOkay = aDiff == 0 || aDiff == -1;
		if (groupB == null)
			return aOkay;

		int bDiff = groupB.numHouses() - numHouses();
		boolean bOkay = bDiff == 0 || bDiff == -1;

		return aOkay && bOkay;
	}
}