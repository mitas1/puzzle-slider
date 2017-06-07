package Animations;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

public class LinearSlideAnimation extends PositionChangeAnimation {
	
	public LinearSlideAnimation( double dstX, double dstY, long duration ) {
		super( dstX, dstY, duration);
	}
	
	@Override
	public void play(Node sourceObject, EventHandler<ActionEvent> onFinished) {
		KeyValue keyValueX = new KeyValue( sourceObject.layoutXProperty(), mDstX );
		KeyValue keyValueY = new KeyValue( sourceObject.layoutYProperty(), mDstY );
		
		Timeline animation = new Timeline( new KeyFrame( Duration.millis( mDuration ), keyValueX, keyValueY ) );
		animation.setCycleCount( 1 );
		animation.setOnFinished( onFinished );
		animation.play();
	}
	
}
