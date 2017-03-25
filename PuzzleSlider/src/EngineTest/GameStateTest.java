package EngineTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Engine.GameData;
import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UninitializedGameException;

public class GameStateTest extends GameData{



	@Test(expected = InvalidArgumentException.class) 
	public void invalidConvstructorTest() throws InvalidArgumentException{
		GameData gameState = new GameData(3);
	}
	
	@Test
	public void validContstructorTest() throws InvalidArgumentException{
		GameData gameState = new GameData(4);
		assertEquals(4, gameState.getmSize());
		gameState = new GameData(15);
		assertEquals(15, gameState.getmSize());
		assertEquals("GAME NOT INITIONALIZED", gameState.toString());
	}
	
	
	@Test(expected = InvalidArgumentException.class) 
	public void getSpecificTileTest_InvalidArgument() throws InvalidArgumentException, UninitializedGameException{
		GameData gameState = new GameData(4);
		gameState.initialize();
		assertEquals(0, gameState.getSpecificTile(3, 4));
	}
	
	@Test(expected = UninitializedGameException.class) 
	public void getSpecificTileTest_UnitionalizedGame() throws InvalidArgumentException, UninitializedGameException{
		GameData gameState = new GameData(4);
		assertEquals(0, gameState.getSpecificTile(3, 4));
	}
	
	@Test
	public void getSpecificTileTest() throws InvalidArgumentException, UninitializedGameException{
		GameData gameState = new GameData(4);
		gameState.initialize();
		assertEquals(1, gameState.getSpecificTile(0, 0));
		assertEquals(4, gameState.getSpecificTile(0, 3));
		assertEquals(5, gameState.getSpecificTile(1, 0));
		assertEquals(0, gameState.getSpecificTile(3, 3));
	}
	
	@Test
	public void switchTilesTest() throws InvalidArgumentException, UninitializedGameException{
		GameData gameState = new GameData(4);
		gameState.initialize();
		assertEquals(1, gameState.getSpecificTile(0, 0));
		gameState.switchTiles(0, 0, 0, 1);
		assertEquals(2, gameState.getSpecificTile(0, 0));
		gameState.switchTiles(0, 1, 0, 2);
		assertEquals(1, gameState.getSpecificTile(0, 2));
		
	}
	
	

}
