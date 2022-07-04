package sim;

import java.util.ArrayList;
import java.util.List;

public class PointCharge extends Charge {
	
	public Vector2 position;
	public static List<PointCharge> charges = new ArrayList<PointCharge>();

	public PointCharge(Vector2 position) {
		super(0);
		this.position = position;
	}

	public PointCharge(Vector2 position, float charge) {
		super(charge);
		this.position = position;
	}

	public Vector2 electricField(Vector2 position) { // E = kq/r^2 r_u (unit vector between charges)
		Vector2 r_u = this.position.clone();
		r_u.sub(position);
		r_u.normalize();
		r_u.multiply(-k * charge / (this.position.sqrDistance(position)));
		return r_u;
	}

	public float electricPotential(Vector2 position) { // V = kq/r
		return k * charge / this.position.distance(position);
	}

}
