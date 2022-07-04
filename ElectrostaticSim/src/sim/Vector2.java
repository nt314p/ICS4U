package sim;

public class Vector2 {
	public float x;
	public float y;

	public Vector2() {
		x = 0;
		y = 0;
	}

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2 clone() {
		return new Vector2(this.x, this.y);
	}

	public float magnitude() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float distance(Vector2 other) {
		float dX = this.x - other.x;
		float dY = this.y - other.y;
		return (float) Math.sqrt(dX * dX + dY * dY);
	}

	public float sqrDistance(Vector2 other) {
		float dX = this.x - other.x;
		float dY = this.y - other.y;
		return dX * dX + dY * dY;
	}

	public void add(Vector2 other) {
		this.x += other.x;
		this.y += other.y;
	}

	public void sub(Vector2 other) {
		this.x -= other.x;
		this.y -= other.y;
	}

	public void multiply(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
	}

	public void normalize() {
		float mag = magnitude();
		this.x /= mag;
		this.y /= mag;
	}

	public float angleTo(Vector2 other) {
		return (float) (Math.atan2(this.y, other.x) - Math.atan2(this.y, other.x));
	}

	public static float dot(Vector2 a, Vector2 b) {
		return a.x * b.x + a.y * b.y;
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
