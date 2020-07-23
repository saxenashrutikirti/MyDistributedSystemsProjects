package def;

import java.io.IOException;

public class Process3 extends UpdateMap {
	@SuppressWarnings("static-access")
	public static void main(String args[]) throws IOException, InterruptedException {
		int i;
		initializeCounter("Process 3");
		while(true)
		{
			Thread t = new Thread(new Server());
			t.setName("Process 3");
			String id = "Process 3 "+t.getId();
			addMap(id, false);
			t.start();
			t.sleep(1000);
			if(getCounter("Process 3")==5)
			{
				System.exit(0);
			}
		}
	}
}
