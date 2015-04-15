package monopoly;

import java.util.LinkedList;
import java.util.Queue;

public class Player
{
	private final int BOARD_SIZE = 40;
	private final int TO_JAIL = 30;
	private final int IN_JAIL = 10;
	private int money;
	private Queue<Square> properties;
	private int position;
	private PlayerType player;
	private String playerName;
	private boolean inJail;
	private int jailTurn;
	private int numJailFree;
	public Player(PlayerType player, String playerName)
	{
		money = 1500;
		properties = new LinkedList<Square>();
		position = 0;
		this.player = player;
		this.playerName = playerName;
		inJail = false;
		numJailFree = 0;
	}

	public void addProperty(Square square)
	{
		if (!square.isOwnable())
			throw new IllegalArgumentException("This property cannot be purchased!");
		properties.add(square);
		square.purchase(this);
	}
	
	public void move(int numSpaces)
	{
	    position += numSpaces;
	    if (position >= BOARD_SIZE)
	    {
	        position -= BOARD_SIZE;
	        excMoney(200);
	    }

	    if (position == TO_JAIL)
	    {
	        position = IN_JAIL;
	        toJail();
	    }
	}
	
	public void moveTo(int pos)
	{
	    position = pos;

        if (position == TO_JAIL)
        {
            position = IN_JAIL;
            toJail();
        }
	}

	public int position()
	{
	    return position;
	}
	
	public int numProps()
	{
	    return properties.size();
	}
	
	public Queue<Square> properties()
	{
	    Queue<Square> tempProp = new LinkedList<Square>();
	    for (Square s : properties)
	        tempProp.add(s);
	    return tempProp;
	}

	public String name()
	{
	    return playerName;
	}
	
	public PlayerType getPlayer()
	{
	    return player;
	}
	
	public Queue<Integer> propIDs()
    {
	    Queue<Integer> queue = new LinkedList<Integer>();
	    for (Square s : properties)
		    queue.add(s.position());
	    return queue;
    }

	public int getMoney()
	{
	    return money;
	}

	public void excMoney(int money)
	{
	    this.money += money;
	}
	
	public void toJail()
	{
	    inJail = true;
	    jailTurn = 0;
	}
	
	public boolean stayJail()
	{
	    jailTurn++;
	    if (jailTurn == 3)
	    {
	        inJail = false;
	        return false;
	    }
	    return true;
	}
	
	public void leaveJail()
	{
	    inJail = false;
	}
	
	public boolean inJail()
	{
	    return inJail;
	}
	
	public void addJailFree()
	{
	    numJailFree++;
	}
	
	public void useJailFree()
	{
	    if (numJailFree < 1)
	        throw new IllegalArgumentException("You do not have any cards!");
	    else
	        numJailFree--;
	}
	
	public int numJailFree()
	{
	    return numJailFree;
	}

	public int getAssets()
    {
	    int assets = this.money;
	    for (Square s : properties)
        {
	        assets += s.cost();
	        if (s instanceof Property)
		        assets += getHouseVal((Property) s);
        }
	    return assets;
    }

	private int getHouseVal(Property prop)
    {
        int numHouses = prop.numHouses();
        int houseCost = prop.houseCost();

	    return numHouses * houseCost;
    }

	public enum PlayerType {
		PLAYER_A, PLAYER_B, PLAYER_C, PLAYER_D,
		PLAYER_E, PLAYER_F, PLAYER_G, PLAYER_H, BANK
    }
}