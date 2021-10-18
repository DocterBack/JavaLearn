
public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Sync sync = new Sync();
		Thread tSin = new ThreadSin(sync);
		Thread tArcSin = new ThreadArcSin(sync);
		
		tSin.start();
		tArcSin.start();
		
		tSin.join();
		tArcSin.join();

	}

}
