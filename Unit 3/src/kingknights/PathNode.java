package kingknights;

import java.util.ArrayList;
import java.util.List;

public class PathNode {
	private List<PathNode> paths;
	private Point point;

	public PathNode(Point point) {
		paths = new ArrayList<PathNode>();
		this.point = point;
	}

	public void add(PathNode p) {
		paths.add(p);
	}

	public int branches() {
		return paths.size();
	}

	public PathNode getPath(int index) {
		return paths.get(index);
	}

	public int paths() {
		return pathsR(this, 0);
	}

	private int pathsR(PathNode p, int paths) {
		for (int i = 0; i < p.paths.size(); ++i) {
			paths += pathsR(p.paths.get(i), 0);
		}
		return paths;
	}

	public String toString() {
		return "      +-" + toStringR(0);
	}

	private String toStringR(int n) {
		if (paths.size() > 0) {
			String ret = "";
			for (int i = paths.size()-1; i >= 0; --i) {
				if (i != paths.size()-1) {
					for (int j = 0; j < n; ++j) {
						ret += "      | ";
					}
						ret += "      +-";
					
				}
				ret += point.toString() + ", " + paths.get(i).toStringR(n + 1);

			}
			return ret;
		}
		return point.toString() + "\n";
	}

	public void remove(PathNode p) {
		paths.remove(p);
	}

}
