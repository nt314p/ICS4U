package kingknights;

public class Solver {
	static int board[][];
	static int n = 10;
	static PathNode root;

	static Point kkMoves[] = {
			// knight
			new Point(2, 1), 
			new Point(1, 2), 
			new Point(-2, -1), 
			new Point(-1, -2), 
			new Point(-2, 1), 
			new Point(-1, 2), 
			new Point(2, -1), 
			new Point(1, -2), 
			
			// king
			new Point(1, 0),
			new Point(0, 1),
			new Point(0, -1),
			new Point(-1, 0),
			new Point(1, 1),
			new Point(-1, -1),
			new Point(1, -1),
			new Point(-1, 1),
	};


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		board = new int[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				board[i][j] = Integer.MAX_VALUE;
			}
		}
		Point start = new Point(1, 2);
		Point end = new Point(3, 5);
		int moves = 4;
		
		root = new PathNode(start);
		
		PathNode sol = root;
		kingknights(start, end, moves);
		System.out.println("Solution: " + sol.tails());
		//System.out.print(sol.toString()); // uncomment for cool trace of paths
	}
	
	public static void kingknights(Point start, Point end, int moves) {
		solve(start, end, moves, 0, root);
	}
	
	public static boolean solve(Point start, Point end, int moves, int currMoves, PathNode path) {
		
		if (currMoves == moves) { // final move
			if (start.equals(end)) {
				return true; // path has been found
			} // else no path found
			return false;
		}
		// uncomment for "optimized" moves, gives incorrect solution
//		if (currMoves < board[start.getX()][start.getY()]) { // current move is better than other moves
//			board[start.getX()][start.getY()] = currMoves;
//		} else {
//			return false; // else current move count is worse, kill recursive
//		}
		boolean found = false;
		for (int i = 0; i < kkMoves.length; i++) { // iterate through all possible moves
			Point newMove = start.add(kkMoves[i]);
			if (newMove.isValid(n)) {
				PathNode newPath = new PathNode(newMove);
				path.add(newPath);
				boolean pathFound = solve(newMove, end, moves, currMoves+1, newPath);
				found |= pathFound;
				if (!pathFound) {
					path.remove(newPath); // path not found, trim from list
				}
			}
		}
		return found;
	}
}








