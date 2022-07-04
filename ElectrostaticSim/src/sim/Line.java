package sim;

public class Line {
	public Vector2 start; // don't change start or end after init
	public Vector2 end;
	public Vector2 direction;
	public Vector2 normal;
	public float length;
	
	public Line (Vector2 start, Vector2 end) {
		this.start = start;
		this.end = end;
		
		this.direction = end.clone();
		this.direction.sub(start);
		this.length = direction.magnitude();
		this.direction.normalize();
		this.normal = new Vector2(-direction.y, direction.x);
	}
	
	public Vector2 lerp(float t) { // between 0 and 1
		float y = (end.y - start.y) * t + start.y;
		float x = (end.x - start.x) * t + start.x;
		return new Vector2(x, y);
	}
	
	public float distanceTo(Vector2 position) {
		Vector2 delta = start.clone(); // r = a + n(t)
		delta.sub(position); // (a - p)
		float dot = Vector2.dot(delta, direction); // (a - p)o(n)
		Vector2 n = direction.clone();
		n.multiply(dot);
		delta.sub(n);
		return delta.magnitude();
	}
}
