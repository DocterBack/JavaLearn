import static java.lang.System.out;
import java.util.concurrent.atomic.*;


class MySuperThread implements Runnable
{

	private Sync sync;
	
	public MySuperThread(Sync sync)
	{
		
		this.sync = sync;
	}
	
	@Override
	public void run()
	{
		for(int i=1; i <= 100; i++)
		{
			System.out.printf("%s - %d\n",
				Thread.currentThread().getName(), i);
			
			sync.increment();
			if (i == 50)
			{
				synchronized(sync)
				{
					//sync.notify();
					sync.notifyAll();
				}
			}
			//Program.counter++;
			
			/*
			synchronized(sync)
			{
				sync.counter++;
			}*/
		}
	}
	
}


class Sync2
{
	public int counter = 0;

}

class Sync 
{
	private int counter = 0;
	
	
	
	public synchronized int getCounter()
	{
		return counter;
	}
	
	public synchronized void increment()
	{
		counter++;
	}

}


public class Program
{

	public static int counter = 0;
	
	public static AtomicInteger Counter = 
		new AtomicInteger(0);
	
	public static void main(String[] args) 
			throws InterruptedException
	{
		Sync sync = new Sync();
		
		Thread a = new MyThread("A", sync);
		Thread b = new MyThread("B", sync);
		//Thread c = new MyThread("C", sync);
		//a.setPriority(Thread.MAX_PRIORITY);
		//b.setPriority(Thread.MIN_PRIORITY);
		//b.setDaemon(true);
		
		
		a.start();
		b.start();
		//c.start();
		
		//(new Thread(new MySuperThread())).start();
		Thread c = new Thread(new MySuperThread(sync));
		c.start();
		/*
		(new Thread(
			new Runnable()
			{
				@Override
				public void run()
				{
					for(int i=1; i <= 100; i++)
					{
						System.out.printf("%s - %d\n",
							Thread.currentThread().getName(), i);
						
						Program.counter++;
					}
				}
			}
		)).start();
		
		
		(new Thread()
		{
			@Override
			public void run() {
				for(int i=1; i <= 100; i++)
				{
					System.out.printf("%s - %d\n",
						Thread.currentThread().getName(), i);
					Program.counter++;
				}
			};
		}).start();*/
		
		
		
		//Thread.sleep(10000);
		
		b.join();
		a.join();
		c.join();
		
		//
		//Thread.currentThread().join();
		
		
		out.println(Thread.currentThread().getName());
		//out.println(sync.counter);
		out.println(sync.getCounter());
		

	}

}
