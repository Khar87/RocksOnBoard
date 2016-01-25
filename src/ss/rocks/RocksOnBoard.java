package ss.rocks;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

public class RocksOnBoard {

	public int resolve(int n, int m, int[] x, int[] y) {
		Board original = new Board(x, y);
		Set<Board> visited = new HashSet<>();
		Queue<Board> q = new LinkedList<>();
		q.offer(original);

		int result = 0;
		while (!q.isEmpty()) {
			Board current = q.poll();
			visited.add(current);
			result = current.rocks();
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < m; ++j) {
					Optional<Board> neighbour = current.checkAndPutRock(i, j);
					if (neighbour.isPresent()) {
						Board newBoard = neighbour.get();
						if (!visited.contains(newBoard)) {
							q.offer(newBoard);
						}
					}
				}
			}
		}

		return result - original.rocks();
	}

	public static void main(String[] args) {
		RocksOnBoard resolver = new RocksOnBoard();
		System.out.println(resolver.resolve(3, 3, new int[] { 0 }, new int[] { 0 }));
	}
}
