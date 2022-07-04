package sim;

public class LineCharge extends Charge {

	public Line line;

	public LineCharge(Line line, float charge) {
		super(charge);
		this.line = line;
	}

	public Vector2 electricField(Vector2 position) {
		float step = 0.001f;
		Vector2 ef = new Vector2();
		for (float t = 0; t <= 1.0001; t += step) {
			Vector2 pos = line.lerp(t);
			Vector2 r_u = pos.clone();
			r_u.sub(position);
			r_u.normalize();
			r_u.multiply(-k * charge / (pos.sqrDistance(position)));
			ef.add(r_u);
		}
		return ef;
	}

	public float electricPotential(Vector2 position) {
		float step = 0.001f;
		float ep = 0;
		for (float t = 0; t <= 1.0001; t += step) {
			ep += k * charge / line.lerp(t).distance(position);
		}
		return ep;
	}

}
