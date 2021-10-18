
public class ThreadSin extends Thread
{
	private Sync sync;
	
	public ThreadSin(Sync sync) {
		super();
		this.sync = sync;
	}

	@Override
	public void run() 
	{
		for (int i=0; i < 100; i++)
		{
			synchronized (sync)
			{
				while(!sync.isSin)
				{
					try {
						sync.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				
				sync.x = Math.sin(sync.x);
				System.out.println(sync.x);

				sync.isSin = false;
				sync.notify();
			}
		}
		
		
		
		
	}

}
