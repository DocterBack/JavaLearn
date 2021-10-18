
public class ThreadArcSin extends Thread{
	private Sync sync;
	
	public ThreadArcSin(Sync sync) {
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
				while(sync.isSin)
				{
					try {
						sync.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				
				sync.x = Math.asin(sync.x);
				System.out.println(sync.x);
				
				sync.isSin = true;
				
				sync.notify();
			}
		}
	}
}
