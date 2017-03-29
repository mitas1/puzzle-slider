package EngineTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Engine.Engine;
import Engine.GameData;
import Engine.Globals.GridPoint;
import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UninitializedGameException;

public class EngineTest{
	
	private class EngineOverride extends Engine {
		
		public EngineOverride() throws InvalidArgumentException {
			super();
			mGameData = new GameData(5);
			mGameData.initialize();
		}
		
		public EngineOverride( int sz ) throws InvalidArgumentException {
			super(sz);
		}
		
		public GameData getGameData() { return mGameData; }
		public boolean moveOverride( GridPoint point ) { return move(point); }
		public int manhattanDistance( GridPoint pt1, GridPoint pt2 ) { return getManhattanDistance(pt1, pt2); }
		public GridPoint findEmptyTileAroundOverride( GridPoint point ) { return findEmptyTileAround(point); }
		
	}
	
	
	// --------------------------------
	// To Test
	// --------------------------------

	// Maybe, maybe not necessary: public boolean move(int row, int col);
	// protected boolean move( GridPoint point );

	// --------------------------------

	@Test(expected = InvalidArgumentException.class) 
	public void invalidConvstructorTest() throws InvalidArgumentException{
		Engine engine = new Engine(3);
	}
	
	@Test
	public void validConvstructorTest() throws InvalidArgumentException{
		Engine engine = new Engine(4);
//		TODO moar tests
	}
	
	@Test
	public void findEmptyTileTest() throws InvalidArgumentException, UninitializedGameException{
		EngineOverride engineTest = new EngineOverride(4);
		
		GridPoint emptyTile = engineTest.getGameData().getEmptyTile();
		int r = (emptyTile.row < 3) ? 4 : 1;
		int c = 1;
		GridPoint tileCoords = engineTest.findEmptyTileAroundOverride(new GridPoint(r, c));
		assertNull(tileCoords);
		
		r = (emptyTile.row == 1) ? 2 : emptyTile.row - 1;
		c = emptyTile.column;
		tileCoords = engineTest.findEmptyTileAroundOverride(new GridPoint(r, c));
		assertEquals(emptyTile, tileCoords);
	}
	
	@Test
	public void shuffleTest()  throws InvalidArgumentException, UninitializedGameException{
		EngineOverride engine = new EngineOverride();
		engine.shuffleNewGame(1000);
		assertFalse( engine.isFinished() ); // there is slight change that it will not shuffle properly, cos random :)
	}
	
	@Test
	public void manhattanDistanceTest1() throws InvalidArgumentException {
		EngineOverride engine = new EngineOverride();
		GridPoint pt0 = new GridPoint(5, 5);
		GridPoint pt1 = new GridPoint(4, 5);
		GridPoint pt2 = new GridPoint(6, 5);
		GridPoint pt3 = new GridPoint(5, 4);
		GridPoint pt4 = new GridPoint(5, 6);
		
		assertEquals(1, engine.manhattanDistance(pt0, pt1));
		assertEquals(1, engine.manhattanDistance(pt0, pt2));
		assertEquals(1, engine.manhattanDistance(pt0, pt3));
		assertEquals(1, engine.manhattanDistance(pt0, pt4));	
	}
	
	@Test
	public void manhattanDistanceTest2() throws InvalidArgumentException {
		EngineOverride engine = new EngineOverride();
		GridPoint pt0 = new GridPoint(5, 5);
		GridPoint pt1 = new GridPoint(4, 4);
		GridPoint pt2 = new GridPoint(10, 5);
		
		assertEquals(2, engine.manhattanDistance(pt0, pt1));
		assertEquals(5, engine.manhattanDistance(pt0, pt2));
	}
}
