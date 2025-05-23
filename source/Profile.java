import java.util.ArrayList;

public class Profile {
	public short xpos;
	public short ypos;
	private String name;
	private String description;
	private String race;
	private String cClass;
	private short strength;
	private short dexterity;
	private short intelligence;
	private short armor;
	protected short hitPoints;
	public Item armedWithOffense = new Item();
	public Item armedWithDefense = new Item();
	ArrayList<Item> inventory = new ArrayList<Item>();
	private short combative;
	
	public Profile() {}
	public Profile(short x, short y, String n, String d, String r, String c, short h, short s, short dx, short i, short a, short com) {
		xpos = x;
		ypos = y;
		name = n;
		description = d;
		race = r;
		cClass = c;
		hitPoints = h;
		strength = s;
		dexterity = dx;
		intelligence = i;
		armor = a;
		combative = com;
	}
	public boolean takeHit(int damage) {
		boolean alive = true;
		hitPoints -= damage;
		if(hitPoints <= 0) alive = false;
		return alive;
	}
	public boolean isHere(Profile p) {
		if(xpos == p.xpos && ypos == p.ypos) return true;
		return false;
	}
	public boolean hit(Profile p) {
		int roll = Die.roll(20);
		if(roll > p.getArmor()) return true;
		return false;
	}
	public void kill() {
		combative = 0;
		name = "Body of " + name;
		disarmAll();
		if(inventory.size() > 0) {
			for(int i = 0; i < inventory.size(); i++) {

			}
		}
	}
	public boolean isCombative() {
		if(combative == 1) return true;
		else return false;
	}
	public void arm(Item i) {
		boolean armed = false;
		strength += i.offense;
		armor += i.defense;
		if(i.offense > 0) {
			armed = true;
			armedWithOffense = i;
			System.out.println("You equipped " + i.name + " as your offensive weapon.");
		}
		if(i.defense > 0) {
			armed = true;
			armedWithDefense = i;
			System.out.println("You equipped " + i.name + " as your defensive tool.");
		}
		if(armed == false) System.out.println("You carn't arm with a " + i.name + "!");
	}
	/**Unequips all equipped items in the player's inventory, setting the values to null.
	**/
	public void disarmAll() {
		strength -= armedWithDefense.defense;
		armor -= armedWithOffense.offense;
		armedWithDefense = null;
		armedWithOffense = null;
	}
	public void disarmOff() {
		strength -= armedWithOffense.offense;
		armedWithOffense = null;
	}
	public void disarmDef() {
		armor -= armedWithDefense.defense;
		armedWithDefense = null;
	}
	public void setName(String n) {
		name = n;
	}
	public void setDesc(String d) {
		description = d;
	}
	public void setRace(String r) {
		race = r;
	}
	public void setcClass(String c) {
		cClass = c;
	}
	public void setHitPoints(short h) {
		hitPoints = h;
	}
	public void setStrength(short s) {
		strength = s;
	}
	public void setDexterity(short d) {
		dexterity = d;
	}
	public void setIntelligence(short i) {
		intelligence = i;
	}
	public void setArmor(short a) {
		armor = a;
	}
	public String getName() {
		return name;
	}
	public String getDesc() {
		return description;
	}
	public String getRace() {
		return race;
	}
	public String getcClass() {
		return cClass;
	}
	public short getHitPoints() {
		return hitPoints;
	}
	public short getStrength() {
		return strength;
	}
	public short getDexterity() {
		return dexterity;
	}
	public short getIntelligence() {
		return intelligence;
	}
	public short getArmor() {
		return armor;
	}
}