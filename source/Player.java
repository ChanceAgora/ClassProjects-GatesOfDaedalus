public class Player extends Profile {
	public Player() {
		// TODO Auto-generated constructor stub
	}
	public Player(short x, short y, String n, String d, String r, String c, short h, short s, short dx, short i, short a, short com) {
		super(x, y, n, d, r, c, h, s, dx, i, a, com);
		// TODO Auto-generated constructor stub
	}
	public void listInventory() {
		if(inventory.size() == 0) {
			System.out.println("You aren't carrying anything.");
			return;
		}
		for(int i = 0; i < inventory.size(); i++) {
			System.out.println("You hava a " + inventory.get(i).name + ".");
		}
		if(armedWithDefense != null && armedWithDefense.name != "null") System.out.println("You have " + armedWithDefense.name + " equipped as defensive equipment.");
		if(armedWithOffense != null && armedWithOffense.name != "null") System.out.println("You have " + armedWithOffense.name + " equipped as offensive equipment.");
	}
}