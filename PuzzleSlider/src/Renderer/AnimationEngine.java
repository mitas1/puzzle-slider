package Renderer;

import Animations.Animation;
import Animations.LinearSlideAnimation;
import Animations.EndGameTileAppearAnimation;
import Global.NumericalRepository;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class AnimationEngine {
	
	protected final long DEFAULT_TILE_SLIDE_DURATION_MS = 200;
	public static final long DEFAULT_SNAPSHOT_SLIDE_DURATION_MS = 400;
	
	protected Pane mOverlay;
	
	protected final long END_GAME_ANIMATION_TICK = 10;
	protected EndGameTileAppearAnimation mEndGameImageAnimation;
	
	public AnimationEngine() {
		mOverlay = new Pane();
		
		JavaFxUtils.setObjectsPosition( 
				mOverlay,
				NumericalRepository.LAYOUT_GAME_CANVAS_OFFSET_X,
				NumericalRepository.LAYOUT_GAME_CANVAS_OFFSET_Y
		);
		mOverlay.setPrefSize( NumericalRepository.LAYOUT_GAME_CANVAS_WIDTH, NumericalRepository.LAYOUT_GAME_CANVAS_HEIGHT );
	}
	
	public void setOverlayParent( Pane parent ) {
		parent.getChildren().add( mOverlay );
	}
	
	public void removeOverlayParent( Pane parent ) {
		parent.getChildren().remove( mOverlay );
	}
	
	public void clearOverlay() {
		mOverlay.getChildren().clear();
	}
	
	public void playTileSlide( Node tile, double dstX, double dstY, EventHandler<ActionEvent> onFinished ) {
		mOverlay.getChildren().add( tile );
		
		Animation animation = new LinearSlideAnimation( dstX, dstY, DEFAULT_TILE_SLIDE_DURATION_MS );
		animation.play( tile, onFinished );
	}
	
	public void playSlideNoOverlay( Node object, double dstX, double dstY, long durationMs, EventHandler<ActionEvent> onFinished ) {
		Animation animation = new LinearSlideAnimation( dstX, dstY, durationMs );
		animation.play( object, onFinished );
	}
	
	
	public void createEngGameImageAnimation( Image tileImage ) {
		mEndGameImageAnimation = new EndGameTileAppearAnimation( END_GAME_ANIMATION_TICK , tileImage );
	}
	
	public void playEngGameImageAnimation( Pane parent, EventHandler<ActionEvent> onFinished ) {
		if ( mEndGameImageAnimation != null ) {
			mEndGameImageAnimation.play( parent, onFinished );
		}
	}

}
