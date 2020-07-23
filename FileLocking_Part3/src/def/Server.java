package def;

import java.io.*;
import java.util.*;

public class Server extends UpdateMap implements Runnable {
	File file = new File("output.txt");
	long clientId;
	long randomNumber;

	public void run() {
		clientId = Thread.currentThread().getId();
		try {
			long id = clientId;
			String clId="";
			//addMap(id, false);
			System.out.println(clientId + " connected successfully!");

			if (checkMap() == true) {
				System.out.println("Access granted to " + clientId + " and thread name is "+ Thread.currentThread().getName());
				clId = Thread.currentThread().getName()+" "+id;
				//deleteMap(id);
				addMap(clId, true);
				BufferedReader br = new BufferedReader(new FileReader(file));
				String str = br.readLine();
				int val = Integer.parseInt(str);
				val++;
				br.close();
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write(Integer.toString(val));
				bw.flush();
				bw.close();
				try {
					updateCounter(Thread.currentThread().getName());
					Thread.currentThread().sleep(7500);
					deleteMap(clId);
					//addMap(id, false);
					System.out.println("Unlocked from client " + clientId);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				System.out.println("Access not granted to " + clientId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
