package monopoly;

public class Dice
{
	private final int N; //number of dice
	private final int SIDES; //number of sides per die
	private double[] doubleProb;

	//single six-sided die
	public Dice()
	{
		N = 1;
		SIDES = 6;
	}
	
	//num of six-sided dice
	public Dice(int num)
	{
		N = num;
		SIDES = 6;
		if (N == 2)
		    setDouble();
	}
	
	//num of dice with given number of sides
	public Dice(int num, int sides)
	{
		N = num;
		SIDES = sides;
	}
	
	private void setDouble()
	{
	    double TOT = 43.0;
	    double D = 36.0;
	    double T = 1296.0;
	    int MAX = 36;
	    
	    int[] ways = new int[MAX];
	    int[] doubleWays = new int[MAX];
	    int[] tripleWays = new int[MAX];
	    
	    doubleProb = new double[MAX];
	    
	    for (int i = 0; i < MAX; i++)
	    {
	        ways[i] = 0;
	        doubleWays[i] = 0;
	        tripleWays[i] = 0;
	    }
	    
	    ways[2] = 1;
        ways[3] = 2;
        ways[4] = 3;
        ways[5] = 4;
        ways[6] = 5;
        ways[7] = 6;
        ways[8] = 5;
        ways[9] = 4;
        ways[10] = 3;
        ways[11] = 2;
        ways[12] = 1;
        
        doubleWays[4] = 1;
        doubleWays[5] = 2;
        doubleWays[6] = 4;
        doubleWays[7] = 6;
        doubleWays[8] = 9;
        doubleWays[9] = 12;
        doubleWays[10] = 14;
        doubleWays[11] = 16;
        doubleWays[12] = 17;
        doubleWays[13] = 18;
        doubleWays[14] = 18;
        doubleWays[15] = 18;
        doubleWays[16] = 17;
        doubleWays[17] = 16;
        doubleWays[18] = 14;
        doubleWays[19] = 12;
        doubleWays[20] = 9;
        doubleWays[21] = 6;
        doubleWays[22] = 4;
        doubleWays[23] = 2;
        doubleWays[24] = 1;
        
        tripleWays[0] = 216;
        tripleWays[7] = 2;
        tripleWays[8] = 2;
        tripleWays[9] = 8;
        tripleWays[10] = 8;
        tripleWays[11] = 20;
        tripleWays[12] = 18;
        tripleWays[13] = 36;
        tripleWays[14] = 30;
        tripleWays[15] = 54;
        tripleWays[16] = 42;
        tripleWays[17] = 72;
        tripleWays[18] = 54;
        tripleWays[19] = 86;
        tripleWays[20] = 62;
        tripleWays[21] = 92;
        tripleWays[22] = 62;
        tripleWays[23] = 86;
        tripleWays[24] = 54;
        tripleWays[25] = 72;
        tripleWays[26] = 42;
        tripleWays[27] = 54;
        tripleWays[28] = 30;
        tripleWays[29] = 36;
        tripleWays[30] = 18;
        tripleWays[31] = 20;
        tripleWays[32] = 8;
        tripleWays[33] = 8;
        tripleWays[34] = 2;
        tripleWays[35] = 2;
	    
        for (int i = 0; i < MAX; i++)
            doubleProb[i] = (ways[i]/1.0 + doubleWays[i]/D
                                                   + tripleWays[i]/T)/TOT;
	}
	
	//return number of dice
	public int numDice()
	{
		return N;
	}
	
	//return sides per die
	public int sides()
	{
		return SIDES;
	}
	
	public int roll()
	{
		int roll = 0;
		
		for (int i = 0; i < N; i++)
		{
	        double rand = Math.random();
	        int randInt = (int) (rand*SIDES);
	        roll += randInt;
		}
		
		return roll;
	}
	
    public int[] rollDouble()
    {
        int OVER = 4;
        int[] roll = new int[OVER];
        for (int i = 0; i < OVER - 1; i++)
            roll[i] = 0;
        roll[OVER - 1] = -1;
        int rollA, rollB;
        
        for (int i = 0; i < OVER; i++)
        {
            double rand = Math.random();
            rollA = (int) (rand * SIDES) + 1;
            rand = Math.random();
            rollB = (int) (rand * SIDES) + 1;
            roll[i] = rollA + rollB;
            if (rollA != rollB)
                return roll;
        }        
        return roll;
    }
    
    public double doubleProb(int roll)
    {
        if (roll < 2 || roll > 35)
            return 0.0;
        return doubleProb[roll];
    }
    
    public double jailProb()
    {
        return doubleProb[0];
    }
	
	//return probability of getting a given total
	public double prob(int total)
	{
		if (total < N || total > N*SIDES)
			return 0.0;
		double numWays = numWays(total);
		double prob = numWays / Math.pow(SIDES,N);
		return prob;
	}
	
	//return number of ways to get a given total
	private double numWays(int total)
	{
		int k, j;
		int numWays = 0;
		
		k = 0;
		while (true)
		{
			j = 0;
			while (true)
			{
				if (SIDES * k + j == total - N)
				{
					numWays += Prob.combi(N, k) * Prob.combi(-N, j)
														* Math.pow(-1, k+j);
				}
				if (j > total - N)
					break;
				j++;
			}
			if (6 * k > total - N)
				break;
			k++;
		}
		
		return numWays;
	}

	//method testing
	public static void main(String[] args)
	{
		Dice dice = new Dice(2);
		System.out.println("number of dice = " + dice.numDice());
		System.out.println("sides per die = " + dice.sides());
		for (int i = 0; i <= 36; i++)
		{
			System.out.println("i = " + i + " prob = " + dice.doubleProb(i));
		}
	}

}