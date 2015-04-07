package monopoly;

public class Prob
{
	//return combination of (a choose b)
	public static double combi(int a, int b)
	{
		if (a < 0)
			return negCombi(a, b);
		double numerator, denominator, combi;
		numerator = factorial(a);
		denominator = factorial(b) * factorial(a-b);
		combi = numerator / denominator;
		return combi;
	}
	
	//return combination where a is negative
	private static double negCombi(int a, int b)
	{
		double numerator, denominator, combi;
		int x = a - b + 1;
		numerator = pochhammer(x, b);
		denominator = factorial(b);
		combi = numerator / denominator;
		return combi;
	}
	
	//calculate factorial of given integer
	public static long factorial(int n)
	{
		if (n < 0)
			throw new IllegalArgumentException("Factorial is negative!");
		return factorial(n, 1);
	}
	
	//calculate factorial of given integer - recursive
	private static long factorial(int n, long product)
	{
		if (n == 0)
			return product;
		return factorial(n - 1, n * product);
	}
	
	//calculate pochhammer function given two integers
	public static long pochhammer(int a, int b)
	{
		long numerator, denominator;
		a = Math.abs(a);
		if (b % 2 == 0)
			numerator = factorial(a);
		else
			numerator = -1 * factorial(a);
		denominator = factorial(a - b);
		return numerator / denominator;
	}
	
	public static void main(String[] args)
	{
		for (int i = 0; i < 15; i++)
		{
			System.out.println("i = " + i + " F = " + factorial(i));
		}
	}
}