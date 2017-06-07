package Animations;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

public abstract class Animation {

	protected long mDuration;
	
	public Animation( long duration ) {
		mDuration = duration;
	}
	
	public abstract void play( Node sourceObject, EventHandler<ActionEvent> onFinished );
	
}
