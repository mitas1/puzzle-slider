package Animations;

import java.util.LinkedList;
import java.util.Queue;

import Engine.Globals.GameMove;
import Engine.Globals.GridPoint;
import ExceptionHandling.InvalidArgumentException;
import Global.NumericalRepository;
import ImageProcessing.ImageSlicer;
import Renderer.JavaFxUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class EndGameTileAppearAnimation extends Animation {
	
	protected ImageView[][] mTiles;
	protected Thread mWorkerThread;
	
	protected final int DEFAULT_TILES = 45;
	
	public EndGameTileAppearAnimation( long duration, Image sourceImage ) {
		super(duration);
		prepareTiles( sourceImage, DEFAULT_TILES );
	}
	
	protected void prepareTiles( Image sourceImage, int tileCount ) {
		mWorkerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					mTiles = new ImageView[tileCount][tileCount];
					ImageSlicer slicer = new ImageSlicer();
					Image[] imageTiles = slicer.GetImageTiles( sourceImage,  tileCount );

					for ( int r = 0; r < tileCount; r++ ) {
						for ( int c = 0; c < tileCount; c++ ) {
							mTiles[r][c] = new ImageView( imageTiles[ r * tileCount + c ] );
						}
					}
				} catch (InvalidArgumentException e) {
					// shoudn't happen :)
					e.printStackTrace();
				}
			}
		} );

		mWorkerThread.start();
	}

	@Override
	public void play(Node parentPane, EventHandler<ActionEvent> onFinished) {
		try {
			mWorkerThread.join();
		} catch (InterruptedException e) {
			// that would be sad....
			e.printStackTrace();
		}
		
		GridPane animPane = prepareAnimPane( (Pane)parentPane );
		
		Queue<GridPoint> tileQueue = new LinkedList<GridPoint>();
		tileQueue.add( new GridPoint(0, 0) );
		
		ImageView[][] animationTiles = mTiles;
		
		Timeline animation = new Timeline();
		animation.getKeyFrames().add( 
			new KeyFrame(
				Duration.millis( mDuration ), 
				new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if ( !doAnimationStep( tileQueue, animPane, animationTiles ) ) {
						animation.stop();
					}
				}
			} )
		);
		
		animation.setCycleCount( Timeline.INDEFINITE );
		animation.setOnFinished( onFinished );
		animation.play();
	}
	
	protected GridPane prepareAnimPane( Pane parent ) {
		GridPane animPane = new GridPane();
		JavaFxUtils.setObjectsPosition( 
				animPane, 
				NumericalRepository.LAYOUT_GAME_CANVAS_OFFSET_X, 
				NumericalRepository.LAYOUT_GAME_CANVAS_OFFSET_Y
		);
		animPane.setPrefSize(NumericalRepository.LAYOUT_GAME_CANVAS_WIDTH, NumericalRepository.LAYOUT_GAME_CANVAS_HEIGHT);
		animPane.setMaxSize(NumericalRepository.LAYOUT_GAME_CANVAS_WIDTH,  NumericalRepository.LAYOUT_GAME_CANVAS_HEIGHT);
		animPane.setMinSize(NumericalRepository.LAYOUT_GAME_CANVAS_WIDTH, NumericalRepository.LAYOUT_GAME_CANVAS_HEIGHT);
		
		parent.getChildren().add( animPane );
		return animPane;
	}
	
	protected boolean doAnimationStep( Queue<GridPoint> tileQueue, GridPane animPane, ImageView[][] animationTiles ) {
		if ( tileQueue.isEmpty() ) {
			return false;
		}
		
		for ( int i = 0; i < tileQueue.size(); i++ ) {
			GridPoint tileViewCoords = tileQueue.poll();
			ImageView tileImgView = animationTiles[ tileViewCoords.row ][ tileViewCoords.column ];
			
			if ( tileImgView != null ) {
				animPane.add( tileImgView, tileViewCoords.column, tileViewCoords.row );
				animationTiles[ tileViewCoords.row ][ tileViewCoords.column ] = null;
				
				GridPoint rightTile = GameMove.GetPoint( GameMove.RIGHT, tileViewCoords );
				GridPoint bottomTile = GameMove.GetPoint( GameMove.BOTTOM, tileViewCoords );
				
				addTileCoords( tileQueue, rightTile );
				addTileCoords( tileQueue, bottomTile );
			}
		}
		
		return true;
	}
	
	protected void addTileCoords( Queue<GridPoint> queue, GridPoint coords ) {
		if ( (coords.row < mTiles.length) && (coords.column < mTiles[0].length) ) {
			queue.add( coords );
		}
	}

	
}
