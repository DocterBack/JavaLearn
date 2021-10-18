
public class MyThread extends Thread
{
	private Sync sync;
	
	public MyThread(String name, Sync sync)
	{
		super(name);
		this.sync = sync;
	}

	@Override
	public void run()
	{
		
		synchronized(sync)
		{
			while (sync.getCounter() < 50)
			{
				System.out.println("waiting....");
				try
				{
					sync.wait(10);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//this.join();
		for(int i=1; i <= 100; i++)
		{
			System.out.printf("%s - %d\n",
				getName(), i);
			
//			Program.counter++;
			//Program.counter = Program.counter+1;
			sync.increment();
			
			Program.Counter.getAndIncrement();
			
			/*synchronized (sync)
			{
				sync.counter++;
			}*/
			
			
		}
	}

}
