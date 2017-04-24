package Engine;

import java.io.Serializable;

public class Globals {
	// TODO: move to global package
	
	public static class GridPoint implements Comparable<GridPoint>, Serializable {
		
		private static final long serialVersionUID = -7640285918433682206L;
		public int row;
		public int column;
		
		public GridPoint( int r, int c) {
			row = r;
			column = c;
		}

		@Override
		public int compareTo(GridPoint o) {
			return ( row - o.row ) + ( column - o.column );
		}
		
	}
	
	public static enum GameState {
		UNINITIALIZED,
		IN_PROGRESS,
		FINISHED;
	}
	
	public static enum GameMove {
		LEFT, TOP, RIGHT, BOTTOM;
		
		public static GridPoint GetPoint( GameMove move, GridPoint moveCoords ) {
			switch (move) {
			case LEFT:
				return new GridPoint( moveCoords.row, moveCoords.column - 1 );
			case TOP:
				return new GridPoint( moveCoords.row - 1, moveCoords.column );
			case RIGHT:
				return new GridPoint( moveCoords.row, moveCoords.column + 1 );
			case BOTTOM:
				return new GridPoint( moveCoords.row + 1, moveCoords.column );
			default:
				return null;
			}
		}
		
	}

}
