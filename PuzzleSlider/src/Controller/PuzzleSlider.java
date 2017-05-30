package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.util.Optional;

import Engine.Engine;
import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UninitializedGameException;
import Global.FileExtension;
import Global.NumericalRepository;
import Global.StringRepository;
import ImageProcessing.ImageSlicer;
import Renderer.JavaFxUtils;
import Renderer.Renderer;
import Tiles.ImageTile;
import Tiles.NumberedTile;
import Tiles.Tile;
import UiObjects.GlobalUiObjects;
import UiObjects.NewGameDialogUiObjects;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PuzzleSlider extends Application {

	Engine mEngine;
	Renderer mRenderer;
	GlobalUiObjects mUiObjects;
	Stopwatch mGameElapsedTime;
	SimpleBooleanProperty mGamePaused;
	
	NewGameProperties mCurrentGameProperties;
	String mImagePathTemp;

	public static void main(String[] args) {
		launch(args);
	}

	//------------------------------------------------------------
	//	Controller main systems
	//------------------------------------------------------------
	
	@Override
	public void start(Stage primaryStage) {
		initializeSubsystems();
		
		mUiObjects.root = primaryStage;
		drawRootWindow();
		
		initializeMenu( primaryStage );
		drawMenu();
	}
	
	private void initializeSubsystems() {
		try {
			mEngine = new Engine();
		} catch (InvalidArgumentException e) {}
		
		mRenderer = new Renderer();
		mUiObjects = new GlobalUiObjects();
		mGameElapsedTime = new Stopwatch();
		mGamePaused = new SimpleBooleanProperty(false);
	}
	
	private void drawRootWindow() {
		mRenderer.drawRootWindow( mUiObjects );
	}
	
	//------------------------------------------------------------
	//	Main menu
	//------------------------------------------------------------
	
	private void initializeMenu( Stage root ) {
		initialzieNewGameButton();
		initializeResumeGameButton();
		initializeSaveGameButton();
		initializeLoadGameButton();
		initializeExitButton();
	}
	
	private void drawMenu() {
		mRenderer.drawMenu( mUiObjects );
	}

	private void initialzieNewGameButton() {
		mUiObjects.newGameButton = new Button();
		mUiObjects.newGameButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showNewGameDialog();
			}
		});
	}
	
	private void initializeResumeGameButton() {
		mUiObjects.resumeGameButton = new Button();
		mUiObjects.resumeGameButton.disableProperty().bind( getPausedProperty().not() );
		mUiObjects.resumeGameButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				resumeGame();
			}
		});
	}
	
	protected void resumeGame() {
		switchPausedState();
		mRenderer.drawGameWindow( mUiObjects );
		mRenderer.updateGameTime( mUiObjects, mGameElapsedTime.getElapsedSecondsFormatted() );
		mRenderer.updateMoveCount( mUiObjects, mEngine.getGameData().getMoveCount() );
		mGameElapsedTime.resume();
	}
	
	private void initializeSaveGameButton() {
		mUiObjects.saveGameButton = new Button();
		mUiObjects.saveGameButton.disableProperty().bind( getPausedProperty().not() );
		mUiObjects.saveGameButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				saveGame();
			}
		});
	}
	
	protected void saveGame() {
		File saveFile = mRenderer.drawSaveFileDialog(
				mUiObjects.root,
				StringRepository.LABEL_SAVELOAD_DIALOG_HEADER,
				JavaFxUtils.getImageExtensionFilter( new FileExtension( StringRepository.FORMAT_SAVE_LABEL, StringRepository.FORMAT_SAVE_EXT ) )
		);
		
		if ( saveFile != null ) {
			try {
				ObjectOutputStream stream = new ObjectOutputStream( new FileOutputStream( saveFile ) );	
				updateCurrentGameProperties();
				stream.writeObject( mCurrentGameProperties );
				mEngine.saveGame( stream );
			} catch (IOException e) {
				e.printStackTrace();
				// TODO: error window
			}
		}
	}
	
	protected void updateCurrentGameProperties() {
		mCurrentGameProperties.elapsedTime = mGameElapsedTime.getElapsed();
	}
	
	private void initializeLoadGameButton() {
		mUiObjects.loadGameButton = new Button();
		mUiObjects.loadGameButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loadGame();
			}
		});
	}
	
	protected void loadGame() {
		File gameFile = mRenderer.drawOpenFileDialog(
				mUiObjects.root, 
				StringRepository.LABEL_SAVELOAD_DIALOG_HEADER, 
				JavaFxUtils.getImageExtensionFilter( new FileExtension(StringRepository.FORMAT_SAVE_LABEL, StringRepository.FORMAT_SAVE_EXT) ) 
		);
		
		if ( gameFile != null && gameFile.canRead() ) {
			try {
				ObjectInputStream stream = new ObjectInputStream( new FileInputStream( gameFile ) );
				mCurrentGameProperties = (NewGameProperties)stream.readObject();
				mEngine.loadGame( stream );
				
				initializeGameScreen( mCurrentGameProperties );
				initializeAndDrawTiles( mCurrentGameProperties );
				
				mGameElapsedTime.pause();
				mGameElapsedTime.start( mCurrentGameProperties.elapsedTime, NumericalRepository.GAME_STOPWATCH_DELAY_MS, this);
				mRenderer.updateGameTime( mUiObjects, mGameElapsedTime.getElapsedSecondsFormatted() );
				mRenderer.updateMoveCount( mUiObjects, mEngine.getGameData().getMoveCount() );
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				// TODO: invalid file error window
			} catch ( IOException | InvalidArgumentException | UninitializedGameException e ) {
				e.printStackTrace();
				// TODO: unknown error window
			}
		}
	}
	
	private void initializeExitButton() {
		mUiObjects.exitButton = new Button();
		mUiObjects.exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		} );
	}
	
	//------------------------------------------------------------
	// New Game dialog
	//------------------------------------------------------------
	
	private void showNewGameDialog() {
		NewGameDialogUiObjects dialogObjects = new NewGameDialogUiObjects();
		initializeLoadPictureButton( dialogObjects.imageChooserButton, dialogObjects );
		
		Optional<ButtonType> dialogResult = mRenderer.drawNewGameDialog( dialogObjects, mUiObjects.root );
		processNewGameDialogResult( dialogResult, dialogObjects );
	}

	private void initializeLoadPictureButton( Button button, NewGameDialogUiObjects uiObjects ) {
		button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	final File imageFile = mRenderer.drawOpenFileDialog( 
            		mUiObjects.root, 
            		StringRepository.HEADING_IMAGE_FILE_DIALOG,  
            		JavaFxUtils.getImageExtensionFilter( new FileExtension( StringRepository.IMG_FORMATS, StringRepository.IMG_FORMATS_EXT ) )
            	);
            
                if ( imageFile != null && imageFile.canRead() ) {
                    uiObjects.imageChosen.set(true);
                    JavaFxUtils.setObjectLabel( uiObjects.imageChooserButton, imageFile.getName() );
                    
                    try {
						mImagePathTemp = imageFile.toURI().toURL().toExternalForm();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
                    
                }
            }
        });
	
	}
	
	protected void processNewGameDialogResult( Optional<ButtonType> result, NewGameDialogUiObjects dialogObjects ) {
		if ( result.isPresent() ) {
			if ( result.get().getButtonData() == ButtonData.OK_DONE ) {
				mCurrentGameProperties = getNewGameProperties( dialogObjects );
				startNewGame( mCurrentGameProperties );
			}
			mImagePathTemp = null;
		}
	}
	
	protected NewGameProperties getNewGameProperties( NewGameDialogUiObjects dialogObjects ) {
		NewGameProperties properties = new NewGameProperties();
		properties.gameSize = (int)dialogObjects.gameSizeSlider.getValue();
		properties.hasImageTiles = dialogObjects.imageTilesRB.isSelected();
		properties.imagePath = mImagePathTemp;
		return properties;
	}

	
	//------------------------------------------------------------
	// 	New Game
	//------------------------------------------------------------
	
	protected void startNewGame( NewGameProperties properties ) {
		try {
			mEngine = new Engine( properties.gameSize );
			initializeGameScreen( properties );
			initializeAndDrawTiles( properties );
		}
		catch ( InvalidArgumentException | UninitializedGameException e) {
			e.printStackTrace();
		}
	}

	protected void initializeGameScreen( NewGameProperties properties ) {
		initializeGameWindow();
		mRenderer.drawGameWindow( mUiObjects );
		mGamePaused.set(false);
	}
	
	protected void initializeAndDrawTiles( NewGameProperties properties ) throws InvalidArgumentException, UninitializedGameException {
		initializeTiles( properties );
		mRenderer.redrawTiles( mUiObjects, mEngine.getGameData().getTiles() );
	}
	
	protected void initializeGameWindow() {
		initializeGamePane();
		initializeBackToMenuButton();
		initializeGameScreenLabels();
	}
	
	protected void initializeGamePane() {
		mUiObjects.gamePane = new GridPane();
		mUiObjects.gamePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int tileSize = NumericalRepository.LAYOUT_GAME_CANVAS_WIDTH / mEngine.getGameData().getSize();
				int row = (int)( event.getSceneY() - NumericalRepository.LAYOUT_GAME_CANVAS_OFFSET_Y )  / tileSize;
				int col = (int)( event.getSceneX() - NumericalRepository.LAYOUT_GAME_CANVAS_OFFSET_X ) / tileSize;
				
				if ( mEngine.move(row, col) ) {
					mRenderer.redrawTiles( mUiObjects, mEngine.getGameData().getTiles() );
					mRenderer.updateMoveCount( mUiObjects, mEngine.getGameData().getMoveCount() );
					
					if ( mEngine.isFinished() ) {
						endGame();
					}
				}
			};
		});
	}
	
	protected void initializeBackToMenuButton() {
		mUiObjects.returnToMenuButton = new Button();
		mUiObjects.returnToMenuButton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				pauseGame();
			}
		});
	}
	
	protected void pauseGame() {
		switchPausedState();
		mGameElapsedTime.pause();
		mRenderer.drawMenu( mUiObjects );
	}
	
	protected void initializeGameScreenLabels() {
		mUiObjects.elapsedTimeLabel = new Label();
		mUiObjects.moveCounterLabel = new Label();
		mGameElapsedTime.start( NumericalRepository.GAME_STOPWATCH_DELAY_MS, this );
	}
	
	public void updateUiTimer() {
		String formattedTime = mGameElapsedTime.getElapsedSecondsFormatted();
		mRenderer.updateGameTime( mUiObjects, formattedTime );
	}
	
	protected void initializeTiles( NewGameProperties properties ) throws InvalidArgumentException, UninitializedGameException {
		mUiObjects.gameTiles = new Tile[ properties.gameSize * properties.gameSize ];
		int tileSize = NumericalRepository.LAYOUT_GAME_CANVAS_WIDTH / properties.gameSize;
		
		if ( properties.hasImageTiles ) {
			initializePictureTiles( properties, tileSize );
		} else {
			initializeNumberedTiles( properties, tileSize );
		}
		
		mUiObjects.gameTiles[mUiObjects.gameTiles.length - 1].setToEmpty();
	}
	
	protected void initializeNumberedTiles( NewGameProperties properties, int tileSize ) {
		for ( int i = 0; i < properties.gameSize * properties.gameSize; i++ ) {
			mUiObjects.gameTiles[i] = new NumberedTile(tileSize, tileSize, i+1);
		}
	}
	
	protected void initializePictureTiles( NewGameProperties properties, int tileSize ) throws InvalidArgumentException {
		Image sourceImage = new Image( properties.imagePath );
		
		ImageSlicer slicer = new ImageSlicer();
		sourceImage = slicer.getBiggestSquare( sourceImage, NumericalRepository.LAYOUT_GAME_CANVAS_WIDTH );
		Image[] tileImages = slicer.GetImageTiles( sourceImage, properties.gameSize );
		
		for ( int i = 0; i < tileImages.length; i++ ) {
			mUiObjects.gameTiles[i] = new ImageTile( tileSize, tileSize, tileImages[i] );
		}
	}
	
	protected SimpleBooleanProperty getPausedProperty() {
		return mGamePaused;
	}
	
	protected void switchPausedState() {
		mGamePaused.set( mGamePaused.not().get() );
	}
	
	protected void endGame() {
		mGameElapsedTime.pause();
	
		Optional<ButtonType> result = mRenderer.drawWinDialog( mUiObjects.root );
		
		if ( result.isPresent() ) {
			switch ( result.get().getText() ) {
			case StringRepository.NEW_GAME:
				showNewGameDialog();
				break;
			case StringRepository.MENU_LABEL:
				mRenderer.drawMenu( mUiObjects );
				break;
			}
		}
	}
	
}
