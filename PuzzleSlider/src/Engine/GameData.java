package Engine;

import java.io.Serializable;
import java.util.Arrays;

import Engine.Globals.GameState;
import Engine.Globals.GridPoint;
import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UninitializedGameException;
import ExceptionHandling.ExceptionMessages;

public class GameData implements Serializable {
	
	// TODO: refactor to use GridPoint instead of "row/col" variables (by F)
	// TODO: correctTiles counter + end game checks
	
	
	// TODO: ....refactor it completely....
	//			- int mCorrectTiles
	//			- GridPoint mBlankTile
	//			- etc.

	private static final long serialVersionUID = 9121546843854872241L;
	protected int[][] mTiles;
	protected GameState mState;
	protected int mSize;

	public GameData(int size) throws InvalidArgumentException{
		if (size < 4) {
			throw new InvalidArgumentException( ExceptionMessages.GameDimensions(size) );
		}
		
		mState = GameState.UNINITIALIZED;
		mSize = size;
	}
	
	
	public void switchTiles(int firstRow, int firstCol, int secondRow, int secondCol) throws UninitializedGameException, InvalidArgumentException {
		if(mState == GameState.UNINITIALIZED){
			throw new UninitializedGameException();
		}
		
		if (firstRow >= mSize || firstCol >= mSize || secondCol >= mSize || secondRow >= mSize) {
			throw new InvalidArgumentException();
		}
		
		int tmp = mTiles[firstRow][firstCol];
		mTiles[firstRow][firstCol] = mTiles[secondRow][secondCol];
		mTiles[secondRow][secondCol] = tmp;
	}

	public void initialize(){
		mTiles = new int[mSize][mSize];
		for (int row = 0; row < mTiles.length; row++) {
			for (int col = 0; col < mTiles.length; col++) {
				mTiles[row][col] = row*mSize + col + 1;
			}
		}
		
		mTiles[mSize-1][mSize-1] = 0;
		mState = GameState.IN_PROGRESS;
	}
	

	@Override
	public String toString() {
		if(mState == GameState.UNINITIALIZED){
			return "GAME NOT INITIALIZED";
		}
		
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < mTiles.length; i++) {
//			TODO rework with String.format() to make it 'clean' looking
			out.append( Arrays.toString(mTiles[i]));
			out.append(System.lineSeparator());
		}
		return out.toString();
	}

	public int[][] getTiles() {
		return mTiles;
	}

	public int getTile(int row, int col) throws UninitializedGameException, InvalidArgumentException {
		if(mState == GameState.UNINITIALIZED){
			throw new UninitializedGameException();
		}

		if (row >= mSize || col >= mSize || row < 0 || col < 0) {
			throw new InvalidArgumentException();
		}

		return mTiles[row][col];
	}

	public GameState getState() {
		return mState;
	}

	public void setState(GameState state) {
		this.mState = state;
	}

	public int getSize() {
		return mSize;
	}

	public GridPoint getEmptyTile() {
		for ( int r = 0; r < mSize; r++) {
			for ( int c = 0; c < mSize; c++ ) {
				if ( mTiles[r][c] == 0 ) {
					return new GridPoint(r, c);
				}
			}
		}
		
		return null;
	}
}

