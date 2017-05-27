package Engine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import Engine.Globals.GameMove;
import Engine.Globals.GridPoint;
import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UninitializedGameException;

public class Engine {

	protected GameData mGameData;
	

	public Engine() throws InvalidArgumentException{
		mGameData = new GameData(4);
	}

	public Engine(int size) throws InvalidArgumentException {
		mGameData = new GameData(size);
		mGameData.initialize();
		shuffleNewGame(1000);
		mGameData.initCounters();
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
					mGameData.incrementMoveCount();
					mGameData.updateTimeCount();
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
	
	public boolean inProgress(){
		return mGameData.inProgress();
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
	
	public void saveGame(String fileName) throws FileNotFoundException, IOException{
		mGameData.saveDataToFile(fileName);
	}

	public void loadGame(String fileName) throws FileNotFoundException, ClassNotFoundException, IOException{
		GameData loadedGame = mGameData.loadDataFromFile(fileName);
		mGameData = loadedGame;
	}
	
	public GameData getGameData() {
		return mGameData;
	}
}
