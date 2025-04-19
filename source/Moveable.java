public class Moveable extends Profile {
	public Moveable() {
		// TODO Auto-generated constructor stub
	}
	public Moveable(short x, short y, String n, String d, String r, String c, short h, short s, short dx, short i, short a, short com) {
		super(x, y, n, d, r, c, h, s, dx, i, a, com);
		// TODO Auto-generated constructor stub
	}
	public void move(MapBlock[][] m) {
		direction d = pickDirection();
		while(!m[xpos][ypos].go(d)) {
			d = pickDirection();
		}
		if(hitPoints > 0) {
			switch(d) {
				case direction.NORTH:
					ypos-=1;
					break;
				case direction.SOUTH:
					ypos+=1;
					break;
				case direction.EAST:
					xpos+=1;
					break;
				case direction.WEST:
					xpos-=1;
					break;
			}
		}
	}
	public direction pickDirection() {
		//int dir = getRand(1,4);
		int dir = Die.roll(4);
		switch(dir) {
		case 1:
			return direction.NORTH;
		case 2:
			return direction.SOUTH;
		case 3:
			return direction.EAST;
		case 4:
			return direction.WEST;
		}	
		return direction.NORTH;
	}
	public static int getRand(int min, int max) {
		if (min >= max) {
			System.out.println("Range Error: min greater than max");
			return -1;
		}
		int r = (int)(Math.random() * ((max - min) + 1)) + min;    //.32445768764468876545
		return r;
	}
}