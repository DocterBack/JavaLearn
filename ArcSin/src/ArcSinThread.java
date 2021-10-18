
public class ArcSinThread extends Thread
{
	private Sync sync;
	private Thread t;

	public ArcSinThread(Sync sync, Thread t)
	{
		this.sync = sync;
		this.t = t;
		
	}
	public ArcSinThread(Sync sync)
	{
		this(sync, null);
	}
	
	
	@Override
	public void run()
	{
		if (t != null)
			try
			{
				t.join();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		sync.x = Math.asin(sync.x);
		System.out.println(sync.x);
	}
	

}
