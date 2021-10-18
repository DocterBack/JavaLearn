
public class SinThread extends Thread
{
	private Sync sync;
	
	public SinThread(Sync sync)
	{
		super();
		this.sync = sync;
	}

	@Override
	public void run()
	{
		sync.x = Math.sin(sync.x);
		System.out.println(sync.x);
	}

}
