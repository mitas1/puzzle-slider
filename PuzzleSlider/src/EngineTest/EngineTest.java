package EngineTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Engine.Engine;
import Engine.GameData;
import Engine.Globals.GridPoint;
import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UninitializedGameException;

public class EngineTest extends Engine{
	
	public EngineTest() throws InvalidArgumentException {
		super();
		
	}

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
		EngineTest engineTest = new EngineTest();
		engineTest.mGameData = new GameData(4);
		engineTest.mGameData.initialize();
		engineTest.shuffleNewGame(1000);
		
		GridPoint emptyTile = engineTest.mGameData.getEmptyTile();
		int r = (emptyTile.row < 3) ? 4 : 1;
		int c = 1;
		GridPoint tileCoords = engineTest.findEmptyTileAround(new GridPoint(r, c));
		assertNull(tileCoords);
		
		r = (emptyTile.row == 1) ? 2 : emptyTile.row - 1;
		c = emptyTile.column;
		tileCoords = engineTest.findEmptyTileAround(new GridPoint(r, c));
		assertEquals(emptyTile, tileCoords);
	}
	
	@Test
	public void isFinishedTest()  throws InvalidArgumentException, UninitializedGameException{
		EngineTest engineTest = new EngineTest();
		engineTest.mGameData = new GameData(4);
		engineTest.mGameData.initialize();
		engineTest.shuffleNewGame(1000);
		assertFalse(engineTest.isFinished());
//		TODO otestovat actually finished game... napady su vitane 
	}
	
	
	

}
