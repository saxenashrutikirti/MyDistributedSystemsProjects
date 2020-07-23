package newpackage;
import java.util.*;
public class Clock1 
{
	static ArrayList<Integer> clock1 = new ArrayList<Integer>();
	public void updateClock()
	{
		clock1.set(0, clock1.get(0)+1);
	}

}
