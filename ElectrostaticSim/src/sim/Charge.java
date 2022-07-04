package sim;

import java.util.ArrayList;
import java.util.List;

public abstract class Charge {
	public static final float k = 8990000000f; // not exact but meh
	
	public float charge;
	public static List<Charge> charges = new ArrayList<Charge>();

	
	public Charge(float charge) {
		this.charge = charge;
		charges.add(this);
	}
	
	public abstract Vector2 electricField(Vector2 position);
	
	public abstract float electricPotential(Vector2 position);
	
	public static Vector2 netElectricField(Vector2 position) {
		Vector2 netEF = new Vector2();
		for (Charge charge : charges) {
			netEF.add(charge.electricField(position));
		}
		return netEF;
	}
	
	public static float netElectricPotential(Vector2 position) {
		float netEP = 0;
		for (Charge charge : charges) {
			netEP += charge.electricPotential(position);
		}
		return netEP;
	}
}
