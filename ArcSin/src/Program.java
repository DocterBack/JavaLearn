
class Sync
{
	public double x = 1;
}


public class Program
{

	public static void main(String[] args)
	{
		Sync s = new Sync();
		
		Thread t1 = new SinThread(s);
		Thread t2 = new ArcSinThread(s, t1);
		
		t1.start();
		t2.start();
		

	}

}
