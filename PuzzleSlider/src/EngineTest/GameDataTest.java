package EngineTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Engine.GameData;
import Engine.Globals.GridPoint;
import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UninitializedGameException;

public class GameDataTest extends GameData{


	public GameDataTest() throws InvalidArgumentException {
		super(4);
	}

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
	
	

}
