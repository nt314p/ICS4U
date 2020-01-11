package kingknights;

public class Point {
	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Point add(Point other) {
		return new Point(this.x + other.x, this.y + other.y);
	}

	public Point multiply(Point other) {
		return new Point(this.x * other.x, this.y * other.y);
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public int[] toArr() {
		return new int[] {x, y};
	}

	public boolean equals(Point other) {
		return this.x == other.x && this.y == other.y;
	}

	public boolean isValid(int n) {
		return x >= 0 && x < n && y >= 0 && y < n;
	}
}
