package EngineTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Engine.GameState;
import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UnitionalizedGameException;

public class GameStateTest extends GameState{



	@Test(expected = InvalidArgumentException.class) 
	public void invalidConvstructorTest() throws InvalidArgumentException{
		GameState gameState = new GameState(3);
	}
	
	@Test
	public void validContstructorTest() throws InvalidArgumentException{
		GameState gameState = new GameState(4);
		assertEquals(4, gameState.getmSize());
		gameState = new GameState(15);
		assertEquals(15, gameState.getmSize());
		assertEquals("GAME NOT INITIONALIZED", gameState.toString());
	}
	
	
	@Test(expected = InvalidArgumentException.class) 
	public void getSpecificTileTest_InvalidArgument() throws InvalidArgumentException, UnitionalizedGameException{
		GameState gameState = new GameState(4);
		gameState.initialize();
		assertEquals(0, gameState.getSpecificTile(3, 4));
	}
	
	@Test(expected = UnitionalizedGameException.class) 
	public void getSpecificTileTest_UnitionalizedGame() throws InvalidArgumentException, UnitionalizedGameException{
		GameState gameState = new GameState(4);
		assertEquals(0, gameState.getSpecificTile(3, 4));
	}
	
	@Test
	public void getSpecificTileTest() throws InvalidArgumentException, UnitionalizedGameException{
		GameState gameState = new GameState(4);
		gameState.initialize();
		assertEquals(1, gameState.getSpecificTile(0, 0));
		assertEquals(4, gameState.getSpecificTile(0, 3));
		assertEquals(5, gameState.getSpecificTile(1, 0));
		assertEquals(0, gameState.getSpecificTile(3, 3));
	}
	
	@Test
	public void switchTilesTest() throws InvalidArgumentException, UnitionalizedGameException{
		GameState gameState = new GameState(4);
		gameState.initialize();
		assertEquals(1, gameState.getSpecificTile(0, 0));
		gameState.switchTiles(0, 0, 0, 1);
		assertEquals(2, gameState.getSpecificTile(0, 0));
		gameState.switchTiles(0, 1, 0, 2);
		assertEquals(1, gameState.getSpecificTile(0, 2));
		
	}
	
	

}
