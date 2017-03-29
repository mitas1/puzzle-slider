package EngineTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Engine.GameData;
import Engine.Globals.GridPoint;
import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UninitializedGameException;

public class GameDataTest {

	@SuppressWarnings("serial")
	private class GameDataOverride extends GameData {

		public GameDataOverride(int size) throws InvalidArgumentException {
			super(size);
		}
		
		public boolean isValidPoint( GridPoint gPoint ) {
			return checkValidPoint(gPoint);
		}
	}
	
	// --------------------------------
	// To Test
	// --------------------------------
	
	// Maybe: protected void checkScoreChange( GridPoint posBefore, GridPoint posAfter )
	// Maybe: protected void moveBlankTile( GridPoint pos1, GridPoint pos2 ) 
	
	// --------------------------------
	

	@Test(expected = InvalidArgumentException.class) 
	public void invalidConvstructorTest() throws InvalidArgumentException{
		GameData gameData = new GameData(3);
	}
	
	@Test
	public void validContstructorTest() throws InvalidArgumentException{
		GameData gameData = new GameData(4);
		assertEquals(4, gameData.getSize());
		gameData = new GameData(15);
		assertEquals(15, gameData.getSize());
		assertEquals("GAME NOT INITIALIZED", gameData.toString());
	}
	
	
	@Test
	public void initializeTest() throws InvalidArgumentException {
		int sz = 4;
		GameData gameData = new GameData(sz);
		
		gameData.initialize();
		int[][] tiles = gameData.getTiles();
		
		for ( int r = 0; r < sz; r++ ) {
			for ( int c = 0; c < sz; c++ ) {
				if ( (r == (sz - 1)) && (c == (sz - 1)) ) {
					assertEquals(0, tiles[r][c]);
				} else {
					assertEquals( r * sz + c + 1, tiles[r][c]);
				}
			}
		}
		
		assertTrue( new GridPoint(sz-1, sz-1).compareTo( gameData.getEmptyTile() ) == 0 );
	}
	
	@Test
	public void gridPointCheckValidTest() throws InvalidArgumentException {
		GameDataOverride gameData = new GameDataOverride(4);
		GridPoint gp1 = new GridPoint(0, 0);
		GridPoint gp2 = new GridPoint(2, 3);
		
		assertTrue( gameData.isValidPoint( gp1 ) );
		assertTrue( gameData.isValidPoint( gp2 ) );
	}
	
	@Test
	public void gridPointCheckInvalidTest() throws InvalidArgumentException {
		GameDataOverride gameData = new GameDataOverride(4);
		GridPoint gp1 = new GridPoint(-1, 0);
		GridPoint gp2 = new GridPoint(4, 3);
		GridPoint gp3 = new GridPoint(23456, 12313);
		
		assertFalse( gameData.isValidPoint( gp1 ) );
		assertFalse( gameData.isValidPoint( gp2 ) );
		assertFalse( gameData.isValidPoint( gp3 ) );
	}
	
	@Test(expected = InvalidArgumentException.class) 
	public void getSpecificTileTest_InvalidArgument() throws InvalidArgumentException, UninitializedGameException{
		GameData gameData = new GameData(4);
		gameData.initialize();
		assertEquals(0, gameData.getTile(new GridPoint(4, 4)));
	}
	
	@Test(expected = UninitializedGameException.class) 
	public void getSpecificTileTest_UnitionalizedGame() throws InvalidArgumentException, UninitializedGameException{
		GameData gameData = new GameData(4);
		gameData.getTile(new GridPoint(4, 4));
	}
	
	@Test
	public void getSpecificTileTest() throws InvalidArgumentException, UninitializedGameException{
		GameData gameData = new GameData(4);
		gameData.initialize();
		
		assertEquals(1, gameData.getTile(new GridPoint(0, 0)));
		assertEquals(4, gameData.getTile(new GridPoint(0, 3)));
		assertEquals(5, gameData.getTile(new GridPoint(1, 0)));
		assertEquals(0, gameData.getTile(new GridPoint(3, 3)));
	}
	
	@Test
	public void switchTilesTest() throws InvalidArgumentException, UninitializedGameException{
		GameData gameData = new GameData(4);
		gameData.initialize();
		assertEquals(1, gameData.getTile(new GridPoint(0, 0)));
		gameData.switchTiles(new GridPoint(0, 0), new GridPoint(0, 1));
		assertEquals(2, gameData.getTile(new GridPoint(0, 0)));
		gameData.switchTiles(new GridPoint(0, 1), new GridPoint(0, 2));
		assertEquals(1, gameData.getTile(new GridPoint(0, 2)));
		
	}
	
	@Test( expected = UninitializedGameException.class )
	public void switchTilesTest_ThrowsUGE() throws UninitializedGameException, InvalidArgumentException {
		GameData gData = new GameData( 5 );
		GridPoint pt1 = new GridPoint(2, 3);
		GridPoint pt2 = new GridPoint(2, 4);
		
		gData.switchTiles(pt1, pt2);
	}
	
	@Test( expected = InvalidArgumentException.class )
	public void switchTilesTest_ThrowsInvalidArg1() throws UninitializedGameException, InvalidArgumentException {
		GameData gameData = new GameData(400);
		GridPoint pt1 = new GridPoint(-1, 12);
		GridPoint pt2 = new GridPoint(399, -4);
		
		gameData.initialize();
		
		gameData.switchTiles(pt1, pt2);
	}
	
	@Test( expected = InvalidArgumentException.class )
	public void switchTilesTest_ThrowsInvalidArg2() throws UninitializedGameException, InvalidArgumentException {
		GameData gameData = new GameData(400);
		GridPoint pt1 = new GridPoint(3, 18);
		GridPoint pt2 = new GridPoint(3422, 45349);
		
		gameData.initialize();
		
		gameData.switchTiles(pt1, pt2);
	}
	
	@Test
	public void isFinishedTestStart() throws InvalidArgumentException {
		GameData gameData = new GameData(6);
		
		gameData.initialize();
		
		assertTrue( gameData.isFinished() );
	}
	
	@Test
	public void isFinishedTest() throws InvalidArgumentException, UninitializedGameException {
		GameData gameData = new GameData( 7 );
		GridPoint pt1 = new GridPoint(1, 2);
		GridPoint pt2 = new GridPoint(2, 2);
		
		gameData.initialize();
		gameData.switchTiles(pt1, pt2);
		
		assertFalse( gameData.isFinished() );
		
		gameData.switchTiles( pt1, pt2);
		
		assertTrue( gameData.isFinished() );
	}
	
}
