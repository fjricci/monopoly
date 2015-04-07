package monopoly;

public class Jail
{

    public enum JailType
    {
        VISITING, TO_JAIL
    }
    
    private JailType type;

	public Jail(JailType type)
	{
		if (type != JailType.VISITING && type != JailType.TO_JAIL)
			throw new IllegalArgumentException("Jail type invalid!");
		this.type = type;
	}
	
	public JailType getType()
	{
	    return type;
	}
}