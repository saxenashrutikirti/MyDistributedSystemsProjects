package def;
import java.util.*;
public class UpdateMap {
	static HashMap<Long, Boolean> map = new HashMap<Long, Boolean>();
	static HashMap<String, Integer> counterMap = new HashMap<String, Integer>();
	static HashMap<String, Boolean> lockMap = new HashMap<String, Boolean>();
	public void updateMap(String id, boolean flag)
	{
		
	}
	public static void addMap(String id, boolean flag)
	{
		lockMap.put(id, flag);
	}
	public void getMap(String id)
	{
		
	}
	public void deleteMap(String id)
	{
		lockMap.remove(id);
	}
	public boolean checkMap()
	{
		boolean isLocked = false;
		for(String i : lockMap.keySet())
		{
			System.out.println("Key "+ i + " Value "+lockMap.get(i));
			if(lockMap.get(i)==true)
			{
				isLocked = true;
			}
		}
		if(isLocked == true)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public static void initializeCounter(String name)
	{
		counterMap.put(name, 0);
	}
	public void getCounter()
	{
		
	}
	public void updateCounter(String name)
	{
		int val = counterMap.get(name);
		val++;
		counterMap.remove(name);
		counterMap.put(name, val);
	}
	public static int getCounter(String name)
	{
		return counterMap.get(name);
	}

}
