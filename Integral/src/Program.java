import ru.specialist.math.*;


public class Program
{
	
	public static void main(String[] args) 
			throws InterruptedException
	{
		
		long t1 = System.currentTimeMillis();
		double r1 = Integral.singleThread(
			new MathFunction()
			{
				public double function(double x)
				{
					return Math.sin(x);
				}
			}
			, 0D, Math.PI/2D);
		long t2 = System.currentTimeMillis();
		
		System.out.printf("Single thread: %f Time: %d\n", r1, t2-t1);
		
		long t3 = System.currentTimeMillis();
		double r2 = Integral.multiThread(
			new MathFunction()
			{
				public double function(double x)
				{
					return Math.sin(x);
				}
			}
			, 0D, Math.PI/2D);
		long t4 = System.currentTimeMillis();
		
		System.out.printf("Multi thread: %f Time: %d\n", r2, t4-t3);
	}

}
