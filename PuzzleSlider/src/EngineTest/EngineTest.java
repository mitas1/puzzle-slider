package EngineTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Engine.Engine;
import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UnitionalizedGameException;

public class EngineTest extends Engine{
	
	public EngineTest() throws InvalidArgumentException {
		super(4);
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
	public void findEmptyTileTest() throws InvalidArgumentException, UnitionalizedGameException{
		EngineTest engineTest = new EngineTest();
		int[] tileCoords = engineTest.findEmptySurroundingTile(0, 0);
		assertNull(tileCoords);
		tileCoords = engineTest.findEmptySurroundingTile(3, 2);
		assertArrayEquals(new int[] {3, 3}, tileCoords);
	}
	
	
	

}
