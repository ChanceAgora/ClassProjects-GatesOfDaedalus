import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadMap {
	
	public static final int MAX_X = 10;
	public static final int MAX_Y = 10;
	
	
	public static void main(String[] args)
	{
		String filename = "../data/map.csv";
		
		MapBlock[][] m = new MapBlock[MAX_X][MAX_Y];
		
		for( int i = 0; i < MAX_X; i++)
		{
			for(int j = 0; j < MAX_Y; j++)
			{
				m[i][j] = new MapBlock();
			}
		}
	
		init(filename, m);
		
		
		
		System.out.println(m[0][0].getTitle());
	}
	
	public static void init(String filename, MapBlock[][] m)
	{
		try
		{
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			String splitBy = ",";
			
			String[] data;
			
			while((line = br.readLine()) != null)
			{
				data = line.split(splitBy);
				
				int xpos = Integer.parseInt(data[0]);
				int ypos = Integer.parseInt(data[1]);
				
				m[xpos][ypos] = new MapBlock(data[2],data[3],Integer.parseInt(data[4]),Integer.parseInt(data[5]),Integer.parseInt(data[6]),Integer.parseInt(data[7])); 
			}
			
			br.close();
		}
		catch(IOException e)
		{
			System.out.println("File Error: " + filename);
			e.printStackTrace();
		}
	}

}
