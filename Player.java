package monopoly;

import java.util.LinkedList;
import java.util.Queue;

public class Player
{
    public enum PlayerType
    {
        PLAYER_A, PLAYER_B, PLAYER_C, PLAYER_D,
        PLAYER_E, PLAYER_F, PLAYER_G, PLAYER_H, BANK;
    }

	private int money;
	private Queue<Square> properties;
	private int position;
	private PlayerType player;
	private String playerName;
	private final int BOARD_SIZE = 40;
	private boolean inJail;
	private int jailTurn;
	private int numJailFree;
	private final int TO_JAIL = 30;
	private final int IN_JAIL = 10;
	
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
	
	public void addProperty(int position)
	{
		Square square = new Square(position);
		if (!square.ownable())
		    throw new IllegalArgumentException("This property cannot "
		                                                   + "be purchased!");
		properties.add(square);
		Square.SquareType sqType = square.type();
		
		switch (sqType)
        {
        case JAIL:      break;
        case TAXES:     break;
        case CARDS:     break;
        case INACTIVE:  break;
        case PROPERTY:  ((Property) square.square()).purchase(this);
        break;
        case RAILROAD:  ((Railroad) square.square()).purchase(this);
        break;
        case UTILITY:   ((Utility) square.square()).purchase(this);
        break;
        default:        break;
        }
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
	
	public int getPos()
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
	
	public String getName()
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
	        queue.add(s.getPos());
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
        int cash = this.money;
        int props = 0;
        int houses = 0;
        for (Square s : properties)
        {
            switch(s.type())
            {
            case PROPERTY: props += getPropVal((Property) s.square());
                           houses += getHouseVal((Property) s.square());
                           break;
            case RAILROAD: props += getRailVal((Railroad) s.square());
                           break;
            case UTILITY:  props += getUtilVal((Utility) s.square());
                           break;
            default:       break;
            }
        }
        return cash + props + houses;
    }
    
    private int getPropVal(Property prop)
    {
        return prop.cost();
    }
    
    private int getRailVal(Railroad rail)
    {
        return rail.cost();
    }
    
    private int getUtilVal(Utility util)
    {
        return util.cost();
    }
    
    private int getHouseVal(Property prop)
    {
        int numHouses = prop.numHouses();
        int houseCost = prop.houseCost();
        
        return numHouses * houseCost;
    }
}