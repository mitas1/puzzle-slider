package Engine;


import java.util.Random;

import Engine.Globals.GameMove;
import Engine.Globals.GridPoint;
import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UninitializedGameException;

public class Engine {

	protected GameData mGameData;
	
	protected Engine(){
		//For Testing purposes
	}

	public Engine(int size) throws InvalidArgumentException {
		mGameData = new GameData(size);
		mGameData.initialize();
		shuffleNewGame(1000);
	}
	
	@Override
	public String toString() {
		return mGameData.toString();
	}


	/**
	 * Returns true if change was accessible, therefore applied. False returned otherwise (any exception, wrong arguments, etc.).
	 */
	public boolean move(int row, int col) {
		return move( new GridPoint(row, col) );
	}
	
	protected boolean move( GridPoint point ) {
		try {
			int selectedTile = mGameData.getTile( point );
			if ( selectedTile > 0 ) {
				GridPoint emptyTile = findEmptyTileAround( point );
				if ( emptyTile != null ) {
					mGameData.switchTiles( emptyTile, point );
					return true;
				}
			}
		}
		catch ( UninitializedGameException e ) {} 
		catch ( InvalidArgumentException e ) {}
		
		return false;
	}

	public void shuffleNewGame(int moveCount) {
		GridPoint blankSpot = new GridPoint(mGameData.mSize - 1, mGameData.mSize - 1);
		Random rng = new Random();
		
		for ( int i = 0; i < moveCount; i++ ) {
			GameMove randomMove = GameMove.values()[ rng.nextInt(4)];
			GridPoint spot = GameMove.GetPoint(randomMove, blankSpot);
			if ( move(spot) == true ) {
				blankSpot = spot;
			}
		}
	}
	
	public boolean isFinished() {
		return mGameData.isFinished();
	}

	protected int getManhattanDistance( GridPoint pt1, GridPoint pt2 ) {
		GridPoint dist = new GridPoint( pt2.row - pt1.row, pt2.column - pt1.column );
		return Math.abs(dist.row) + Math.abs(dist.column);
	}
	
	protected GridPoint findEmptyTileAround( GridPoint point ) {
		GridPoint emptyTilePoint = mGameData.getEmptyTile();
		if ( getManhattanDistance( point, emptyTilePoint ) == 1 ) {
			return emptyTilePoint;
		}
		
		return null;
	}


}
