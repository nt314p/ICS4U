package sim;

import processing.core.PApplet;

public class ElectroMain extends PApplet {

	static float deltaTime;
	float stepSize = 0.4f;
	int spacing = 1;
	int iterations = 20000;

	boolean freeze = false;

	int box = 200;
	int windowSize = 1200;
	Vector2 min = new Vector2(-box, -box);
	Vector2 max = new Vector2(box, box);
	float scale = windowSize / (2 * box);
	Vector2 offset = new Vector2(windowSize - scale * box, windowSize - scale * box);

	PointCharge q0 = new PointCharge(new Vector2(100, 100), 0.0000000f);
	PointCharge q1 = new PointCharge(new Vector2(100, -100), 0.000001f);
	PointCharge q2 = new PointCharge(new Vector2(-100, -100), -0.000001f);
	//LineCharge q3 = new LineCharge(
	//		new Line(new Vector2(-150, 150), new Vector2(150, 150)), -0.00000001f);

	public static void main(String[] args) {
		ElectroMain em = new ElectroMain();
		PApplet.runSketch(new String[] { "ElectroMain" }, em);
	}

	public void setup() {
		frameRate(60);
		stroke(255);
	}

	public void settings() {
		size(windowSize, windowSize);
	}

	public void keyPressed() {
		if (key == 32) {
			freeze = !freeze;
		}
	}

	public void draw() {
		if (mouseX != pmouseX || mouseY != pmouseY) {
			Vector2 pos = new Vector2((mouseX - offset.x) / scale, (mouseY - offset.x) / -scale);
			System.out.println("\nPosition: " + pos);
			System.out.println("Electric Potential: " + PointCharge.netElectricPotential(pos));
			System.out.println("Electric Field: " + PointCharge.netElectricField(pos));
			System.out.println("Electric Field Magnitude: " + PointCharge.netElectricField(pos).magnitude());

			if (!freeze) {
				background(0);
				q0.position = pos;
				long startTime = System.currentTimeMillis();
				drawField();
				long endTime = System.currentTimeMillis();
				System.out.println("Electric field took " + (endTime - startTime) + " ms");

				startTime = System.currentTimeMillis();
				// drawIsopotentialLines();
				endTime = System.currentTimeMillis();
				System.out.println("Isopotential lines took " + (endTime - startTime) + " ms");

				startTime = System.currentTimeMillis();
				drawFieldLines();
				endTime = System.currentTimeMillis();
				System.out.println("Electric field lines took " + (endTime - startTime) + " ms");
			}
			// drawFieldLines();
		}
	}

	public void drawField() {
		colorMode(HSB, 359, 99, 99);
		deltaTime = 1.0f / frameRate;
		for (float x = min.x; x < max.x; x += 1 / scale) {
			for (float y = min.y; y < max.y; y += 1 / scale) {
				Vector2 position = new Vector2(x, y);
				drawPoint(position, PointCharge.netElectricField(position).magnitude());
			}
		}
		colorMode(RGB, 255, 255, 255);
	}

	public void drawIsopotentialLines() {
		int step = 10;
		int clr = color(255, 255, 255);
		for (float x = min.x; x < max.x; x += 0.999 / scale) {
			for (float y = min.y; y < max.y; y += 0.999 / scale) {
				Vector2 p = new Vector2(x, y);
				float voltage = PointCharge.netElectricPotential(p);
				for (int i = -200; i < 200; i += step) {
					if (Math.abs(voltage - i) < 0.05f) {
						set((int) (p.x * scale + offset.x) - 1, (int) (-p.y * scale + offset.y) - 1, clr);
					}
				}
			}
		}
	}

	public void drawFieldLines() {
		stroke(255);
		fill(255);
		float dVLen = 0.01f;

		Vector2 p = q1.position.clone();

		for (int i = 0; i < 360; i += 10) {
			double rad = i * Math.PI / 180;
			Vector2 dV = new Vector2(dVLen * (float) Math.cos(rad), dVLen * (float) Math.sin(rad));
			Vector2 offset = p.clone();
			offset.add(dV);
			drawFieldLine(offset);
		}
	}

	public void drawFieldLine(Vector2 initialPosition) {
		int clr = color(255, 255, 255);
		Vector2 tracer = initialPosition.clone();
		for (int i = 0; i < iterations; i++) {
			Vector2 slope = PointCharge.netElectricField(tracer);
			slope.normalize();
			slope.multiply(stepSize);
			Vector2 prevTracer = tracer.clone();
			tracer.add(slope);
			if (prevTracer.sqrDistance(tracer) < 0.1f) {
				break;
			}
			if (i % spacing == 0)
				set((int) (tracer.x * scale + offset.x), (int) (-tracer.y * scale + offset.y), clr);
		}
	}

	public void drawPoint(Vector2 p, float magnitude) {
		double value = 360 / ((magnitude) + 1);
		int clr = (color((int) (value), 99, 99));
		set((int) (p.x * scale + offset.x) - 1, (int) (-p.y * scale + offset.y) - 1, clr);
	}
}
