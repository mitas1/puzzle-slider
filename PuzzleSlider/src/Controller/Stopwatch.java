package Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Stopwatch {

	Timeline mWorker;
	long mElapsed;
	
	public void start( long startTime, int tickDelay, PuzzleSlider controller ) {
		mElapsed = startTime;
		
		mWorker = new Timeline( new KeyFrame( Duration.millis( tickDelay ), e -> {
			mElapsed += tickDelay;
			controller.updateUiTimer();
		} ) );
		
		resume();
	}
	
	public void start( int tickDelay, PuzzleSlider controller ) {
		start( 0, tickDelay, controller );
	}
	
	public void pause() {
		mWorker.stop();
	}
	
	public void resume() {
		mWorker.setCycleCount( Timeline.INDEFINITE );
		mWorker.play();
	}
	
	public long getElapsed() {
		return mElapsed;
	}
	
	public String getElapsedSecondsFormatted() {
		long seconds = millisToSeconds( mElapsed );
		return String.format( "%02d:%02d", seconds / 60, seconds % 60);
	}
	
	protected long millisToSeconds( long millis ) {
		return millis / 1000;
	}
	
}
