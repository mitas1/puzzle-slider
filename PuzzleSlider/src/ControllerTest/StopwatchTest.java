package ControllerTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Controller.PuzzleSlider;
import Controller.Stopwatch;
import Global.NumericalRepository;

public class StopwatchTest extends Stopwatch{
	
	private static final int DUMMY_DELAY = 99999;

	@Test
	public void elapsedTimeTest(){
		Stopwatch stopwatch = new Stopwatch();
		stopwatch.start(NumericalRepository.GAME_STOPWATCH_DELAY_MS, new PuzzleSlider());
		stopwatch.pause();
		stopwatch.resume();
		
		// where's da test? :D
	}
	
	@Test
	public void milisToSecondsTest(){
		assertEquals(5, millisToSeconds(5000));
		assertEquals(0, millisToSeconds(0));
	}
	
	@Test
	public void formattingTestWholeSeconds() {
		Stopwatch stopwatch = new Stopwatch();
		
		stopwatch.start(1000, DUMMY_DELAY, null);
		String oneSecStr = stopwatch.getElapsedSecondsFormatted();
		
		stopwatch.start(15000, DUMMY_DELAY, null);
		String fifteenSecStr = stopwatch.getElapsedSecondsFormatted();
		
		stopwatch.start(60000, DUMMY_DELAY, null);
		String minStr = stopwatch.getElapsedSecondsFormatted();
		
		stopwatch.pause();
		
		assertEquals( "00:01", oneSecStr );
		assertEquals( "00:15", fifteenSecStr );
		assertEquals( "01:00", minStr );
	}
	
	@Test
	public void formattingTest() {
		Stopwatch stopwatch = new Stopwatch();

		stopwatch.start( 9742, DUMMY_DELAY, null );
		String nineSec = stopwatch.getElapsedSecondsFormatted();
		
		stopwatch.start( 42185, DUMMY_DELAY, null );
		String fourtyTwoSec = stopwatch.getElapsedSecondsFormatted();
		
		stopwatch.start( 85993, DUMMY_DELAY, null );
		String minuteTwentyFifeSec = stopwatch.getElapsedSecondsFormatted();
		
		stopwatch.pause();
		
		assertEquals( "00:09", nineSec );
		assertEquals( "00:42", fourtyTwoSec );
		assertEquals( "01:25", minuteTwentyFifeSec );
	}
	
	@Test
	public void formattingTestOver99Minutes() {
		Stopwatch stopwatch = new Stopwatch();
		
		stopwatch.start( 9000000, DUMMY_DELAY, null );
		stopwatch.pause();
		
		assertEquals( "150:00", stopwatch.getElapsedSecondsFormatted() );
	}

}
