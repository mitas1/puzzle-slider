package ControllerTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Controller.PuzzleSlider;
import Controller.Stopwatch;
import Global.NumericalRepository;

public class StopwatchTest extends Stopwatch{
	
	@Test
	public void elapsedTimeTest(){
		Stopwatch stopwatch = new Stopwatch();
		stopwatch.start(NumericalRepository.GAME_STOPWATCH_DELAY_MS, new PuzzleSlider());
		stopwatch.pause();
		stopwatch.resume();
		
	}
	
	@Test
	public void milisToSecondsTest(){
		assertEquals(5, millisToSeconds(5000));
		assertEquals(0, millisToSeconds(0));
		
	}
	

}
