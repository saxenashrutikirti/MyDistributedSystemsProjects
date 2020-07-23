package def;

import java.io.IOException;

public class Process1 extends UpdateMap {

	@SuppressWarnings("static-access")
	public static void main(String args[]) throws IOException, InterruptedException {
		int i;
		initializeCounter("Process 1");
		while(true)
		{
			Thread t = new Thread(new Server());
			t.setName("Process 1");
			String id = "Process 1 "+t.getId();
			addMap(id, false);
			t.start();
			t.sleep(1000);
			if(getCounter("Process 1")==5)
			{
				System.exit(0);
			}
		}
	}

}
