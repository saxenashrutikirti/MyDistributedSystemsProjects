package newpackage;

import java.util.ArrayList;

public class Clock2 {
	static ArrayList<Integer> clock2 = new ArrayList<Integer>();
	public void updateClock()
	{
		clock2.set(1, clock2.get(1)+1);
	}

}
