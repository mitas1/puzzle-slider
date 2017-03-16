package EngineTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Engine.GameState;
import ExceptionHandling.InvalidArgumentException;

public class GameStateTest{


	@Before
	public void init(){

	}

	@After
	public void cleanUp(){

	}

	@Test(expected = InvalidArgumentException.class) 
	public void invalidConvstructorTest() throws InvalidArgumentException{
		GameState gameState = new GameState(3);
	}
	
	@Test
	public void validContstructorTest() throws InvalidArgumentException{
		GameState gameState = new GameState(4);
		gameState = new GameState(15);
		assertEquals(15, gameState.getmSize());
	}

}
