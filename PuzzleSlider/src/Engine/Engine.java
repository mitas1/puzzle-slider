package Engine;


import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UnitionalizedGameException;

public class Engine {

	protected GameState mGameState;

	public static void main(String[] args) throws InvalidArgumentException {

	}

	public Engine(){
		//not used
	}

	public Engine(int size) throws InvalidArgumentException {
		mGameState = new GameState(size);
		mGameState.initialize();
	}


	/**
	 * Returns true if change was accessible, therefore applied. False returned otherwise (any exception, wrong arguments, etc.).
	 */
	public boolean move(int row, int col) throws UnitionalizedGameException, InvalidArgumentException{
		//		TODO maybe change to try-catch instead of throws construction 
		int selectedTile = mGameState.getSpecificTile(row, col);
		if(selectedTile == 0){
			return false;
		}
		int[] emptyTileCoords =  findEmptySurroundingTile(row, col);
		if(emptyTileCoords == null){
			return false;
		}
		mGameState.switchTiles(row, col, emptyTileCoords[0], emptyTileCoords[1]);
		return true;
	}


	public void randomize(int degree){
		// TODO filip je cierny
	}

	/**
	 * Returns int[] {row, col} if found empty tile in surroiding tiles. Returns null otherwise.
	 */
	protected int[] findEmptySurroundingTile(int selectedRow, int selectedCol) throws UnitionalizedGameException, InvalidArgumentException {
		int[] out = new int[2];
		if (selectedRow - 1 >= 0) {
			if (mGameState.getSpecificTile(selectedRow - 1, selectedCol) == 0) {
				out[0] = selectedRow - 1;
				out[1] = selectedCol;
				return out;
			}
		}
		if (selectedRow + 1 < mGameState.getmSize()) {
			if (mGameState.getSpecificTile(selectedRow + 1, selectedCol) == 0) {
				out[0] = selectedRow + 1;
				out[1] = selectedCol;
				return out;
			}
		}
		if (selectedCol - 1 >= 0) {
			if (mGameState.getSpecificTile(selectedRow, selectedCol - 1) == 0) {
				out[0] = selectedRow;
				out[1] = selectedCol - 1;
				return out;
			}
		}
		if (selectedCol + 1 < mGameState.getmSize()) {
			if (mGameState.getSpecificTile(selectedRow, selectedCol + 1) == 0) {
				out[0] = selectedRow;
				out[1] = selectedCol + 1;
				return out;
			}
		}
		return null;
	}


}
