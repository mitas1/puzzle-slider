package Engine;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Arrays;

import Engine.Globals.GameState;
import Engine.Globals.GridPoint;
import ExceptionHandling.ExceptionMessages;
import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UninitializedGameException;
import Global.NumericalRepository;

public class GameData implements Serializable {

	private static final long serialVersionUID = 854452780857028700L;
	
	protected int[][] mTiles;
	protected GridPoint mBlankSpot;
	protected int mSize;
	
	protected GameState mState;
	protected int mCorrectTiles;
	
	protected int mMoveCount;
	
	public GameData( int size ) throws InvalidArgumentException {
		if ( size < NumericalRepository.GAME_SIZE_MIN ) {
			throw new InvalidArgumentException( ExceptionMessages.GameDimensions( size ) );
		}
		else if (size > NumericalRepository.GAME_SIZE_MAX) {
			throw new InvalidArgumentException( ExceptionMessages.GameDimensions( size ) );
		}
		mState = GameState.UNINITIALIZED;
		mSize = size;
	}
	
	@Override
	public String toString() {
		if(mState == GameState.UNINITIALIZED){
			return "GAME NOT INITIALIZED";
		}
		
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < mTiles.length; i++) {
			out.append( Arrays.toString(mTiles[i]));
			out.append(System.lineSeparator());
		}
		out.append("Moves: " + mMoveCount);
		out.append(System.lineSeparator());
		return out.toString();
	}
	
	public void initialize() {
		mTiles = new int[mSize][mSize];
		for (int row = 0; row < mTiles.length; row++) {
			for (int col = 0; col < mTiles.length; col++) {
				mTiles[row][col] = row*mSize + col + 1;
			}
		}
		
		mTiles[mSize-1][mSize-1] = 0;
		mBlankSpot = new GridPoint( mSize-1, mSize-1);
		mCorrectTiles = mSize * mSize - 1;
		mState = GameState.IN_PROGRESS;
	}
	
	public void initCounters(){
		mMoveCount = 0;
	}
	
	protected boolean checkValidPoint( GridPoint point ) {
		return ( point.row >= 0 ) && ( point.row < mSize ) && ( point.column >= 0 ) && ( point.column < mSize );
	}
	
	protected void checkScoreChange( GridPoint posBefore, GridPoint posAfter ) {
		int tileValue = mTiles[posAfter.row][posAfter.column];
		if ( tileValue == (posBefore.row * mSize + posBefore.column + 1) ) {
			mCorrectTiles--;
		} else if ( tileValue == (posAfter.row * mSize + posAfter.column + 1) ) {
			mCorrectTiles++;
		}
	}
	
	protected void moveBlankTile( GridPoint pos1, GridPoint pos2 ) {
		if ( pos1.compareTo(mBlankSpot) == 0 ) {
			mBlankSpot = pos2;
		} else {
			mBlankSpot = pos1;
		}
	}
	
	public void switchTiles(GridPoint tile1, GridPoint tile2) throws UninitializedGameException, InvalidArgumentException {
		if(mState == GameState.UNINITIALIZED){
			throw new UninitializedGameException();
		}
		
		if ( !checkValidPoint(tile1) || !checkValidPoint(tile2) ) {
			throw new InvalidArgumentException();
		}
		
		int tmp = mTiles[tile1.row][tile1.column];
		mTiles[tile1.row][tile1.column] = mTiles[tile2.row][tile2.column];
		mTiles[tile2.row][tile2.column] = tmp;
		
		moveBlankTile(tile1, tile2);
		checkScoreChange(tile1, tile2);
		checkScoreChange(tile2, tile1);
	}
	
	public int[][] getTiles() {
		return mTiles;
	}

	public int getTile(GridPoint coords) throws UninitializedGameException, InvalidArgumentException {
		if(mState == GameState.UNINITIALIZED){
			throw new UninitializedGameException();
		}

		if ( !checkValidPoint(coords) ) {
			throw new InvalidArgumentException();
		}

		return mTiles[coords.row][coords.column];
	}
	
	public void saveDataToFile( OutputStream stream ) throws IOException {
		ObjectOutputStream os = new ObjectOutputStream( stream );
		os.writeObject(this);
		os.close();
	}
	
	public GameData loadDataFromFile( InputStream stream ) throws IOException, ClassNotFoundException {
		ObjectInputStream is = new ObjectInputStream( stream );
		GameData loadedData =  (GameData) is.readObject();
		is.close();
		return loadedData;
	}
	
	public GameState getState() {
		return mState;
	}
	
	public int getSize() {
		return mSize;
	}
	
	public GridPoint getEmptyTile() {
		return mBlankSpot;
	}
	
	public int getMoveCount() {
		return mMoveCount;
	}
	
	public void incrementMoveCount(){
		mMoveCount++;
	}
	
	protected void checkFinished() {
		if ( mCorrectTiles == (mSize * mSize - 1) ) {
			mState = GameState.FINISHED;
		}
	}
	
	public boolean isFinished() {
		checkFinished();
		return mState == GameState.FINISHED;
	}
	
	public boolean inProgress(){
		return mState == GameState.IN_PROGRESS;
	}
}
