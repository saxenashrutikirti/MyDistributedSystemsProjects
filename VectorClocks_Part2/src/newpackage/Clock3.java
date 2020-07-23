package newpackage;

import java.util.ArrayList;

public class Clock3 {
	static ArrayList<Integer> clock3 = new ArrayList<Integer>();
	public void updateClock()
	{
		clock3.set(2, clock3.get(2)+1);
	}

}
