import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*  TO DO: 
	* Graphical Map
	* Win
*/ 

public class Adventure {
	public static final int MAX_X = 10;
	public static final int MAX_Y = 10;
	public static int winX = -1;
	public static int winY = -1;
	public static String winItem = "";
	public static String winString = "";

	public static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Welcome to the game! At any time, enter ?, help, or just h for a list of helpful commands available to you! Good luck!\n\n");
		int maxProfiles = 20;
		int numChars = 0;
		String charFile = "../data/characters.csv";
		String itemFile = "../data/items.csv";
		Profile[] characters = new Profile[maxProfiles];
		String mapFile = "../data/map.csv";
		boolean playing = true;
		MapBlock[][] m = new MapBlock[MAX_X][MAX_Y];
		for(int i = 0; i < MAX_X; i++) {
			for(int j = 0; j < MAX_Y; j++) {
				m[i][j] = new MapBlock(); 
			}
		}
		init(mapFile, m);
		if(verify(m)) {
			numChars = init(charFile, characters, m);
			init(characters, m, itemFile);
			characters[0].disarmAll();
			while(playing) {
				moveChars(m, characters, numChars);
				showMap(m, characters);
				showChars(characters, numChars);
				showItems(m[characters[0].xpos][characters[0].ypos]);
				playing = npcCombat(characters, numChars);
				if(playing) {
					playing = actionHandler(m, characters, numChars);
				}
			}
		}
		System.out.println("Goodbye!");
		s.close();
	}
	private static boolean npcCombat(Profile[] c, int numChars) {
		boolean alive = true;
		for(int i = 1; i < numChars; i++) {
			if(c[0].isHere(c[i]) && c[i].isCombative()) {
				System.out.println("Rolling for initiative...");
				if(!rollForInitiative()) {
					System.out.println(c[i].getName() + " is attacking");
					if(c[i].hit(c[0])) {
						int damage = c[i].getStrength() + Die.roll(10);
						System.out.println(c[i].getName() + " hits with " + damage + " damage.");
						alive = c[0].takeHit(damage);
						System.out.println(c[0].getHitPoints() + " health.");
						if(!alive) {
							System.out.println("You are dead. Enjoy the afterlife!");
						}
					}
				}
			}
		}
		return alive;
	}
	private static void combat(Profile[] c, int numChars) {
		for(int i = 1; i < numChars; i++) {
			if(c[0].isHere(c[i])) {
				if(c[i].isCombative()) {
					boolean alive = true;
					System.out.println("You swing at " + c[i].getName() + "...");
					if(c[0].hit(c[i])) {
						int damage = c[0].getStrength() + 10;
						System.out.println("You hit with " + damage + " damage.");
						alive = c[i].takeHit(damage);
						System.out.println(c[i].getName() + " has " + c[i].getHitPoints() + " health left.");
						if(!alive) {
							System.out.print(c[i].getName() + " is dead!");
							c[i].kill();
							//Create function to drop items
						}
					}
				}
			}
		}
	}
	private static boolean rollForInitiative() {
		int myRoll = Die.roll(10);
		int npcRoll = Die.roll(10);

		if(myRoll >= npcRoll) {
			System.out.println("You have initiative!");
			return true;
		}return false;
	}
	private static void showItems(MapBlock mb) {
		for(int i = 0; i < mb.itemsHere.size(); i++) {
			System.out.println("There's a " + mb.itemsHere.get(i).name + " here.");
		}
	}
	private static void moveChars(MapBlock[][] m, Profile[] characters, int numChars) {
		for(int i = 1; i < numChars; i++) {
			if(characters[i] instanceof Moveable) {
				Moveable n = (Moveable)characters[i];
				n.move(m);
			}
		}
	}
	private static void showChars(Profile[] c, int numChars) {
		for(int idx = 1; idx < numChars; idx++) {
			if((c[0].xpos == c[idx].xpos) && (c[0].ypos == c[idx].ypos)) {
				System.out.println(c[idx].getName() + " is here.");
				if(c[idx].inventory.size() > 0) {
					for(int i = 0; i < c[idx].inventory.size(); i++) {
						System.out.println(c[idx].getName() + " has " + c[idx].inventory.get(i).name + ".");
					}
				}
			}
		}
	}
	public static void showMap(MapBlock[][] m, Profile[] characters) {
		System.out.println(m[characters[0].xpos][characters[0].ypos].getTitle()+ "\n" + m[characters[0].xpos][characters[0].ypos].getDescription());
	}
	public static boolean actionHandler(MapBlock[][] m, Profile[] characters, int numChars) {
		boolean playing = true;
		String words = "";
		String command = "";
		while(command.length() == 0) {
			System.out.print("Next move=> ");
			command = s.nextLine();
		}
		command = command.toLowerCase();
		int splitAt = command.indexOf(' ');
		if(splitAt > 0) {
			words = command.substring(splitAt + 1);
		}
		char cmd = command.charAt(0);
		switch(cmd) {
			case 'u':
				boolean hasItem = false;
				if(characters[0].inventory.size() == 0) System.out.println("You have nothing to equip.");
				else {
					for(int i = 0; i < characters[0].inventory.size(); i++) {
						if(characters[0].inventory.get(i).name.equalsIgnoreCase(words)) {
							hasItem = true;
							characters[0].arm(characters[0].inventory.get(i));
						}
					}
				}
				if(hasItem == false) System.out.println("You don't have " + words + ".");
				break;
			case 'h':
			case '?':
				listCommands();
				break;
			case 'i':
				Player p = (Player)characters[0];
				p.listInventory();
				break;
			case 'n':
				if(m[characters[0].xpos][characters[0].ypos].go(direction.NORTH)) {
					characters[0].ypos--;
				}else {
					System.out.println("Chant go that way...");
				}
				break;
			case 's':
				if(m[characters[0].xpos][characters[0].ypos].go(direction.SOUTH)) {
					characters[0].ypos++;
				}else {
					System.out.println("Chant go that way...");
				}
				break;
			case 'e':
				if(m[characters[0].xpos][characters[0].ypos].go(direction.EAST)) {
					characters[0].xpos++;
				}else {
					System.out.println("Chant go that way...");
				}
				break;
			case 'w':
				if(m[characters[0].xpos][characters[0].ypos].go(direction.WEST)) {
					characters[0].xpos--;
				}else {
					System.out.println("Chant go that way...");
				}
				break;
			case 'g':
				int itemGetIndex = -1;
				for(int i = 0; i < m[characters[0].xpos][characters[0].ypos].itemsHere.size(); i++) {
					if(words.equalsIgnoreCase(m[characters[0].xpos][characters[0].ypos].itemsHere.get(i).name)){
						itemGetIndex = i;
					}
				}
				getItem(characters[0], m[characters[0].xpos][characters[0].ypos], itemGetIndex);
				break;
			case 'd':
				int itemDropIndex = -1;
				for(int i = 0; i < characters[0].inventory.size(); i++) {
					if(words.equalsIgnoreCase(characters[0].inventory.get(i).name)) {
						itemDropIndex = i;
					}
				}
				dropItem(characters[0], m[characters[0].xpos][characters[0].ypos], itemDropIndex);
				break;
			case 'a':
				combat(characters, numChars);
				break;
			case 't':
				steal(characters, numChars, words);
				break;
			case 'x':
				playing = false;
				break;
			default:
				System.out.println("Not a valid command");
				break;
		}
		System.out.println("\n\n");
		return playing;
	}
	private static void dropItem(Profile c, MapBlock mb, int itemIndex) {
		if(itemIndex == -1) {
			System.out.println("You aren't holding that.");
			return;
		}
		mb.itemsHere.add(c.inventory.remove(itemIndex));
	}
	private static void getItem(Profile c, MapBlock mb, int itemIndex) {
		if(itemIndex == -1) {
			System.out.println("You can't find that here.");
			return;
		}
		c.inventory.add(mb.itemsHere.remove(itemIndex));
	}
	public static int init(String filename, Profile[] c, MapBlock[][] map) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String splitBy = ",";
			int pCount = 1;
			String[] data;
			
			while((line = br.readLine()) != null) {
				data = line.split(splitBy);
				short xpos = Short.parseShort(data[0]);
				short ypos = Short.parseShort(data[1]);
				short pType = Short.parseShort(data[11]);
				if(map[xpos][ypos].getTitle().equalsIgnoreCase("VOID")) {
					System.out.print("ERROR: Character " + data[2] + "'s position " + xpos + ", " + ypos + " is out of bounds.");
				}else {
					switch(pType) {
						case 0:
							c[pCount++] = new Immoveable(xpos, ypos, data[2], data[3], data[4], data[5], Short.parseShort(data[6]), Short.parseShort(data[7]), Short.parseShort(data[8]), Short.parseShort(data[9]), Short.parseShort(data[10]), Short.parseShort(data[12]));
							break;
						case 1:
							c[pCount++] = new Moveable(xpos, ypos, data[2], data[3], data[4], data[5], Short.parseShort(data[6]), Short.parseShort(data[7]), Short.parseShort(data[8]), Short.parseShort(data[9]), Short.parseShort(data[10]), Short.parseShort(data[12]));
							break;
						case 2:
							c[0] = new Player(xpos, ypos, data[2], data[3], data[4], data[5], Short.parseShort(data[6]), Short.parseShort(data[7]), Short.parseShort(data[8]), Short.parseShort(data[9]), Short.parseShort(data[10]), Short.parseShort(data[12]));
							break;
						default:
							System.out.println("Invalid player type: " + data[2]);
							break;
					}
				}
			}
			br.close();
			fr.close();
			return pCount;
		}catch(IOException e) {
			System.out.println("File Error: " + filename);
			e.printStackTrace();
		}
		return -1;
	}
	public static void init(Profile[] c, MapBlock[][] map, String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String splitBy = ",";
			String[] data;
			
			while((line = br.readLine()) != null) {
				data = line.split(splitBy);
				short first = Short.parseShort(data[0]);
				String name = data[2];
				String desc = data[3];
				short offense = Short.parseShort(data[4]);
				short defense = Short.parseShort(data[5]);
				double value = Double.parseDouble(data[6]);
				Item i = new Item(name, desc, offense, defense, value);
				if(first == -1) {
					short playerNum = Short.parseShort(data[1]);
					c[playerNum].inventory.add(i);
				}else {
					short xpos = Short.parseShort(data[0]);
					short ypos = Short.parseShort(data[1]);
					map[xpos][ypos].itemsHere.add(i);
				}
			}
			br.close();
			fr.close();
		}catch(IOException e) {
			System.out.println("File Error: " + filename);
			e.printStackTrace();
		}
	}
	public static void init(String filename, MapBlock[][] m) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String splitBy = ",";
			String[] data;
			
			while((line = br.readLine()) != null) {
				data = line.split(splitBy);
				int xpos = Integer.parseInt(data[0]);
				int ypos = Integer.parseInt(data[1]);
				m[xpos][ypos] = new MapBlock(data[2], data[3], Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]));
			}
			br.close();
			fr.close();
		}catch(IOException e) {
			System.out.println("File Error: " + filename);
			e.printStackTrace();
		}
	}
	public static boolean verify(MapBlock[][] m) {
		boolean result = true;
		for(int x = 0; x < MAX_X; x++) {
			for(int y = 0; y < MAX_Y; y++) {
				if(m[x][y].go(direction.NORTH)) {
					if(y == 0) {
						System.out.println("Map Block [" + x + "][" + y + "] - NORTH from " + m[x][y].getTitle() + " does not exist.");
						result =  false;
					}else if(m[x][y-1].getTitle().equalsIgnoreCase("VOID")) {
						System.out.println("Map Block [" + x + "][" + y + "] - NORTH from " + m[x][y].getTitle() + " does not exist.");
						result = false;
					}
				}
				if(m[x][y].go(direction.SOUTH)) {
					if(y == MAX_Y - 1) {
						System.out.println("Map Block [" + x + "][" + y + "] - SOUTH from " + m[x][y].getTitle() + " does not exist.");
						result =  false;
					}else if(m[x][y+1].getTitle().equalsIgnoreCase("VOID")) {
						System.out.println("Map Block [" + x + "][" + y + "] - SOUTH from " + m[x][y].getTitle() + " does not exist.");
						result = false;
					}
				}
				if(m[x][y].go(direction.EAST)) {
					if(x == MAX_X - 1) {
						System.out.println("Map Block [" + x + "][" + y + "] - EAST from " + m[x][y].getTitle() + " does not exist.");
						result = false;
					}else if(m[x+1][y].getTitle().equalsIgnoreCase("VOID")) {
						System.out.println("Map Block [" + x + "][" + y + "] - EAST from " + m[x][y].getTitle() + " does not exist.");
						result = false;
					}
				}
				if(m[x][y].go(direction.WEST)) {
					if(x == 0) {
						System.out.println("Map Block [" + x + "][" + y + "] - WEST from " + m[x][y].getTitle() + " does not exist.");
						result = false;
					}else if(m[x-1][y].getTitle().equalsIgnoreCase("VOID")) {
						System.out.println("Map Block [" + x + "][" + y + "] - WEST from " + m[x][y].getTitle() + " does not exist.");
						result = false;
					}
				}
			}
		}
		return result;
	}
	public static void listCommands() {
		System.out.println("Here is a list of all available commands:");
		System.out.println("N/S/E/W - Goe north, south, east, or west respectively. Note that if there are obstructions in the map or if a direction is impassible, it may not be possible to go in certain directions.");
		System.out.println("A - Attack. This will attack any hostile characters in the same location.");
		System.out.println("I - This will list what you are carrying in your inventory.");
		System.out.println("G - Enter G followed by the space and the name of an item to grab it from the environment. Things that do not exist or are not in your location cannot be grabbed.");
		System.out.println("D - Enter D followed by the name of an item in your inventory to drop it. It will be left in the location you drop it.");
		System.out.println("T - You can attempt to steal items from characters in the game. You may not be sucessful though...");
		System.out.println("U - Equip a specified item in your inventory for use.");
		System.out.println("X - Enterint X at any time will quit the game.");
	}
	public static void steal(Profile[] characters, int numChars, String itemName) {
		for(int i = 1; i < numChars; i++) {
			if(!(characters[0].isHere(characters[i])) || characters[i].inventory.size() == 0) continue;
			for(int j = 0; j < characters[i].inventory.size(); j++) {
				if(characters[i].inventory.get(j).name.equalsIgnoreCase(itemName)) {
					System.out.println("\t\t" + characters[i].getName() + " has the item.");
					if(Die.roll(20) + characters[0].getDexterity() > characters[i].getIntelligence()) {
						if(characters[i].inventory.get(j).name == characters[i].armedWithOffense.name) characters[i].disarmOff();
						if(characters[i].inventory.get(j).name == characters[i].armedWithDefense.name) characters[i].disarmDef();
						characters[0].inventory.add(characters[i].inventory.remove(j));
						System.out.println("You got it!");
					}else {
						System.out.print("You weren't able to steal it...");
					}
				}
			}
		}
	}
	public static boolean winCondition(Profile[] c) {
		if(winX == -1) {
			try { 
				FileReader check = new FileReader("../data/win.csv");
				BufferedReader getCheck = new BufferedReader(check);
				String temp = getCheck.readLine();
				String[] vals = temp.split(",");
				winX = Integer.parseInt(vals[0]);
				winY = Integer.parseInt(vals[1]);
				winItem = vals[2];
				winString = vals[3];
				check.close();
				getCheck.close();
			}catch(IOException e) {
				System.out.println("Failed to find or read from file.");
				e.printStackTrace();
			}
		}else {
			if(c[0].inventory.size() == 0 ) return false;
			if(c[0].xpos == winX && c[0].ypos == winY) {
				for(int i = 0; i < c[0].inventory.size9); i++) {
					if(c[0].inventory.get(i).name.equalsIgnoreCase(winItem)) return true;
				}
			}
		}
		return false;
	}
}