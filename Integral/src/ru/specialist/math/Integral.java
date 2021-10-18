package ru.specialist.math;

public class Integral
{
	public static int STEPS = 100000000;
	public static int THREADS = 50;
	
	private double summa = 0;
	
	private Integral()
	{}
	
	
	public static double singleThread(
			MathFunction f, double a, double b)
	{
		return singleThread(f, a, b, STEPS);
		
	}
	
	static double singleThread(
		MathFunction f, double a, double b, int steps)
	{
		double h = (b-a)/steps;
		double summa = 0D;
		
		for(int i=0; i < steps; i++)
		{
			double x = a+i*h+h/2;
			double y = f.function(x);
			summa += y*h;
		}
		
		return summa;
		
	}
	
	public static double multiThread(MathFunction f, double a, double b) 
			throws InterruptedException
	{
		Integral sync = new Integral();
		
		double h = (b-a)/THREADS;
		
		final int steps = STEPS/THREADS;
		
		Thread[] threads = new Thread[THREADS];
		for(int i=0; i <THREADS; i++)
		{
			double at = a+h*i;
			double bt = at+h;
			threads[i] = 
					new IntegralThread(f, at, bt, steps, sync);
		}
		for(int i=0; i <THREADS; i++)
			threads[i].start();
		
		for(int i=0; i <THREADS; i++)
			threads[i].join();
		
		return sync.summa;
		
	}
	
	
	static class IntegralThread extends Thread
	{
		private MathFunction f;
		private double a;
		private double b;
		private int steps;
		private Integral result;
		
		public IntegralThread(MathFunction f, double a, double b, int steps,
				Integral result)
		{
			this.f = f;
			this.a = a;
			this.b = b;
			this.steps = steps;
			this.result = result;
		}
		
		@Override
		public void run()
		{
			double r = Integral.singleThread(f, a, b, steps);
			
			synchronized (result)
			{
				result.summa += r;
			}
		}
		
		
		
		
		
		
	}

}
