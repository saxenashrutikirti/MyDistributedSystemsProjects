package def;

import java.io.IOException;

public class Process2 extends UpdateMap {

	@SuppressWarnings("static-access")
	public static void main(String args[]) throws IOException, InterruptedException {
		int i;
		initializeCounter("Process 2");
		while(true)
		{
			Thread t = new Thread(new Server());
			t.setName("Process 2");
			String id = "Process 2 "+t.getId();
			addMap(id, false);
			t.start();
			t.sleep(1000);
			if(getCounter("Process 2")==5)
			{
				System.exit(0);
			}
		}
	}

}
