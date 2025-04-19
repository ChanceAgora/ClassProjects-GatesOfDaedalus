import java.util.ArrayList;

public class MapBlock {
	private String title;
	private String description;
	private int n;
	private int s;
	private int e;
	private int w;
	ArrayList<Item> itemsHere = new ArrayList<Item>();
	
	public MapBlock()
	{
		title = "VOID";
		n = 0;
		s = 0;
		e = 0;
		w = 0;
	}
	public MapBlock(String t, String d, int north, int south, int east, int west)
	{
		title = t;
		description = d;
		n = north;
		s = south;
		e = east;
		w = west;
	}
	public String getTitle()
	{
		return title;
	}
	public String getDescription()
	{
		return description;
	}
	public boolean go(direction d)
	{
		switch(d)
		{
		case NORTH:
			if (n == 1)
			{
				return true;
			}
			break;
		case SOUTH:
			if (s == 1)
			{
				return true;
			}
			break;
		case EAST:
			if (e == 1)
			{
				return true;
			}
			break;
		case WEST:
			if (w == 1)
			{
				return true;
			}
			break;
		default:
			System.out.println("OOPS - Should not be here");
			break;
		
		}
		
		return false;
	}
}