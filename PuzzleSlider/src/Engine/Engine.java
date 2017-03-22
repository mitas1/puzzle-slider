package Engine;

import javax.xml.transform.Templates;

import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UnitionalizedGameException;

public class Engine {
	
	protected GameState mGameState;

	public static void main(String[] args) throws InvalidArgumentException {
		
	}
	
	public Engine(int size) throws InvalidArgumentException {
		mGameState = new GameState(size);
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

	/**
	 * Returns int[] {row, col} if found empty tile in surroiding tiles. Returns null otherwise.
	 */
	private int[] findEmptySurroundingTile(int selectedRow, int selectedCol) throws UnitionalizedGameException, InvalidArgumentException {
		int[] out = new int[2];
		for(int row = selectedRow - 1; row < selectedRow + 2; row++){
			for(int col = selectedCol -1; col < selectedCol + 2; col++){
				if(row == selectedRow && col == selectedCol){
					continue;
				}
				int foundTile = mGameState.getSpecificTile(row, selectedCol);
				if(foundTile == 0){
					
					out[0] = row;
					out[1] = col;
					return out;
				}
			}

		}

		
		return null;
	}
	

}
