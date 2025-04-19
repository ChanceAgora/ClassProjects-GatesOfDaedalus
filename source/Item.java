public class Item {
	String name;
	String description;
	short offense = 0;
	short defense = 0;
	double value = 0.0;
	public Item() {}
	public Item(String n, String desc, short o, short d, double v) {
		name = n;
		description = desc;
		offense = o;
		defense = d;
		value = v;
	}
}
