

interface MathFunction
{
	double function(double x); 

}



public class Program
{

	public static double singlePoint(MathFunction f) 
	{
		double x = 1D;
		return f.function(x);
	}
	
	
	public static void main(String[] args)
	{
		
		final int k=2;
		// closure
		double y = singlePoint(
				new MathFunction()
				{
					public double function(double x) 
					{
						return k*Math.sin(x);
					};	
				}
		);
		System.out.println(y);

	}

}
