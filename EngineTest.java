package EngineTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Engine.Engine;
import Engine.GameData;
import Engine.Globals.GridPoint;
import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UninitializedGameException;
import Global.NumericalRepository;

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
		public boolean moveOverride(int row, int col) { return move(row, col); }
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
		engine = new Engine(-1);
		engine = new Engine(10000);
	}
	
	@Test
	public void validConvstructorTest() throws InvalidArgumentException{
		for (int i = NumericalRepository.GAME_SIZE_MIN; i < NumericalRepository.GAME_SIZE_MAX;i++){
			Engine engine = new Engine(i);
		}
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
	
	@Test
	public void manhattanDistanceTest3() throws InvalidArgumentException {
		EngineOverride engine = new EngineOverride();
		GridPoint pt0 = new GridPoint(5, 5);
		
		assertEquals(0, engine.manhattanDistance(pt0, pt0));
	}
	
	@Test
	public void ValidMove() throws InvalidArgumentException{
		EngineOverride engine = new EngineOverride();
		assertTrue(engine.moveOverride(new GridPoint(4, 3)));
		
		engine = new EngineOverride();
		assertTrue(engine.move(4, 3));
	}
	
	@Test
	public void ValidMoveWithCheck() throws InvalidArgumentException, UninitializedGameException{
		EngineOverride engine = new EngineOverride();
		assertTrue(engine.moveOverride(new GridPoint(3, 4)));
		assertEquals(0, engine.getGameData().getTile(new GridPoint(3,4)));
		assertEquals(20, engine.getGameData().getTile(new GridPoint(4,4)));
		
		engine = new EngineOverride();
		assertTrue(engine.moveOverride(3,4));
		assertEquals(0, engine.getGameData().getTile(new GridPoint(3,4)));
		assertEquals(20, engine.getGameData().getTile(new GridPoint(4,4)));
	}
	
	@Test
	public void InvalidMoveOnGrid() throws InvalidArgumentException{
		EngineOverride engine = new EngineOverride();
		assertFalse(engine.moveOverride(new GridPoint(1, 0)));
		
		engine = new EngineOverride();
		assertFalse(engine.moveOverride(1,0));
		assertFalse(engine.moveOverride(1,1));
		assertFalse(engine.moveOverride(2,1));
		assertFalse(engine.moveOverride(5,1));
	}
	
	@Test
	public void InvalidMoveDiagonal() throws InvalidArgumentException{
		EngineOverride engine = new EngineOverride();
		assertFalse(engine.moveOverride(new GridPoint(3, 3)));
		assertFalse(engine.moveOverride(new GridPoint(4, 4)));
		
		engine = new EngineOverride();
		assertFalse(engine.moveOverride(3,3));
		assertFalse(engine.moveOverride(4,4));
	}
	
	@Test
	public void InvalidMoveToEmpty() throws InvalidArgumentException{
		EngineOverride engine = new EngineOverride();
		assertFalse(engine.moveOverride(new GridPoint(4, 4)));
		assertFalse(engine.moveOverride(new GridPoint(2, 2)));
		
		engine = new EngineOverride();
		assertFalse(engine.moveOverride(4, 4));
		assertFalse(engine.moveOverride(2, 2));
	}
	
	@Test
	public void InvalidMoveAdjacentNotOnGrid() throws InvalidArgumentException{
		EngineOverride engine = new EngineOverride();
		assertFalse(engine.moveOverride(new GridPoint(4, 5)));
		assertFalse(engine.moveOverride(new GridPoint(5, 4)));
		
		engine = new EngineOverride();
		assertFalse(engine.moveOverride(5,4));
		assertFalse(engine.moveOverride(4,5));
	}
	
	@Test
	public void InvalidMoveNotOnGrid() throws InvalidArgumentException{
		EngineOverride engine = new EngineOverride();
		assertFalse(engine.moveOverride(new GridPoint(12, 3)));
		
		engine = new EngineOverride();
		for (int i = NumericalRepository.GAME_SIZE_MAX; i < 100;i++){
			assertFalse(engine.moveOverride(i, 3));
		}
		
	}
	
	@Test
	public void InvalidMoveNotOnGrid2() throws InvalidArgumentException{
		EngineOverride engine = new EngineOverride();
		assertFalse(engine.moveOverride(new GridPoint(12, -47)));
		
		engine = new EngineOverride();
		for (int i = -100; i < NumericalRepository.GAME_SIZE_MIN;i++){
			assertFalse(engine.moveOverride(new GridPoint(12, -47)));
		}
	}
	
}
