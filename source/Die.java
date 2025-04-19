public class Die {
	public static int roll(int die) {
		return getRand(1,die);
	}
	public static int getRand(int min, int max) {
		if (min >= max) {
			System.out.println("Range Error: min greater than max");
			return -1;
		}
		int r = (int)(Math.random() * ((max - min) + 1)) + min;
		return r;
	}	
}
