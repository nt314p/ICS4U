package zigzag;

public class Interval {
	
	private int start;
	private int end;
	private int[] data;
	
	public Interval(int start, int end, int[] data) {
		this.start = start;
		this.end = end;
		this.data = data;
	}
	
	public Interval combine(Interval other) { // this.end+1 == other.start
		
		int[] a = data;
		int[] b = other.data;
		if (start > other.start) { // swap if greater
			a = other.data;
			b = data;
		}
		
		int[] newData = new int[a.length + b.length];
		for (int i = 0; i < a.length; ++i) {
			newData[i] = a[i];
		}
		for (int i = 0; i < b.length; ++i) {
			newData[i + a.length] = b[i];
		}
		return new Interval(Math.min(start, other.start), Math.max(end, other.end), newData);
	}
	
	

}
