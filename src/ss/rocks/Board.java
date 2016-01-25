package ss.rocks;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Board {
	private Set<Field> rocks = new HashSet<>();
	private Set<Field> blocked = new HashSet<>();

	public Board(int[] x, int[] y) {
		if (x.length != y.length) {
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < x.length; ++i) {
			if (!checkAndPutRockInternal(x[i], y[i])) {
				throw new IllegalArgumentException();
			}
		}
	}

	private Board(Board source) {
		for (Field rock : source.rocks) {
			if (!checkAndPutRockInternal(rock.x, rock.y)) {
				throw new IllegalArgumentException();
			}
		}
	}

	private boolean checkAndPutRockInternal(int x, int y) {
		final Field newRock = new Field(x, y);
		boolean canPut = !rocks.contains(newRock) && !blocked.contains(newRock);
		if (canPut) {
			rocks.add(newRock);
			blocked.addAll(newRock.neighbours());
		}
		return canPut;
	}

	public Optional<Board> checkAndPutRock(int x, int y) {
		Board newBoard = new Board(this);

		Optional<Board> result;
		if (newBoard.checkAndPutRockInternal(x, y)) {
			result = Optional.of(newBoard);
		} else {
			result = Optional.empty();
		}
		return result;
	}

	public int rocks() {
		return rocks.size();
	}

	class Field {
		int x;
		int y;

		public Field(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Collection<Field> neighbours() {
			Set<Field> neighbours = new HashSet<>();
			neighbours.add(new Field(x - 1, y - 1));
			neighbours.add(new Field(x - 1, y));
			neighbours.add(new Field(x - 1, y + 1));
			neighbours.add(new Field(x, y - 1));
			neighbours.add(new Field(x, y + 1));
			neighbours.add(new Field(x + 1, y - 1));
			neighbours.add(new Field(x + 1, y));
			neighbours.add(new Field(x + 1, y + 1));
			return neighbours;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Field other = (Field) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		private Board getOuterType() {
			return Board.this;
		}

	}
}
