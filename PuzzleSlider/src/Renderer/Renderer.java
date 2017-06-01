package Renderer;

import java.io.File;
import java.util.Optional;

import Engine.Globals.GridPoint;
import Global.NumericalRepository;
import Global.StringRepository;
import Layouts.GameScreenLayout;
import Layouts.MainMenuLayout;
import Layouts.NewGameDialogLayout;
import Layouts.ScreenLayout;
import Layouts.WinDialogLayout;
import UiObjects.GlobalUiObjects;
import UiObjects.NewGameDialogUiObjects;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class Renderer extends Pane{
    
    protected Pane mRootPane;
    protected AnimationEngine mAnimationEngine;
    
    public Renderer() {
    	Font.loadFont(Renderer.class.getResource("/resources/fonts/PermanentMarker.ttf").toExternalForm(), 10);
    }
    
    public void drawRootWindow( GlobalUiObjects uiObjects ) {
    	mRootPane = new Pane();
    	Scene rootScene = new Scene( 
    			mRootPane, 
    			NumericalRepository.LAYOUT_GAME_WINDOW_WIDTH, 
    			NumericalRepository.LAYOUT_GAME_WINDOW_HEIGHT 
    	);
    	
    	rootScene.getStylesheets().add(getClass().getResource("GUI_styles.css").toExternalForm());
    	
    	uiObjects.root.setScene( rootScene );
    	uiObjects.root.show();
    	
    	mAnimationEngine = new AnimationEngine();
    }
    
    public void drawMenu( GlobalUiObjects uiObjects ) {
    	mRootPane.getChildren().clear();

    	ScreenLayout layout = new MainMenuLayout();
    	layout.setLayout(uiObjects, mRootPane );	
    }
    
    public Optional<ButtonType> drawNewGameDialog( NewGameDialogUiObjects dialogUiObjects, Window parent ) {
    	Dialog<ButtonType> newGameDialog = new Dialog<>();
    	newGameDialog.initOwner( parent );
        newGameDialog.setResizable(true);
        newGameDialog.setTitle( StringRepository.HEADING_NEW_GAME_DIALOG );
        
    	NewGameDialogLayout layout = new NewGameDialogLayout();
    	layout.setLayout( dialogUiObjects, newGameDialog );
    	
    	return newGameDialog.showAndWait();
    }
    
    public File drawOpenFileDialog( Window parent, String heading, ExtensionFilter filter ) {
    	final FileChooser fileDialog = getFileDialog(heading, filter);
    	return fileDialog.showOpenDialog( parent );
    }
    
    public File drawSaveFileDialog( Window parent, String heading, ExtensionFilter filter ) {
    	final FileChooser fileDialog = getFileDialog(heading, filter);
    	return fileDialog.showSaveDialog( parent );
    }

	protected FileChooser getFileDialog(String heading, ExtensionFilter filter) {
		FileChooser fileDialog = new FileChooser();
    	fileDialog.setTitle( heading );
    	fileDialog.getExtensionFilters().setAll( filter );
		return fileDialog;
	}
    
    public void drawGameWindow( GlobalUiObjects uiObjects ) {
    	mRootPane.getChildren().clear();
    	
    	GameScreenLayout layout = new GameScreenLayout();
    	layout.setLayout( uiObjects, mRootPane );
    }
    
    public void updateGameTime( GlobalUiObjects uiObjects, String timeString ) {
    	uiObjects.elapsedTimeLabel.setText( timeString );
    }
    
    public void updateMoveCount( GlobalUiObjects uiObjects, int moveCount ) {
		uiObjects.moveCounterLabel.setText( String.format("%d", moveCount) );
	}
    
    public void redrawTiles( GlobalUiObjects uiObjects, int[][] ordering ) {
    	uiObjects.gamePane.getChildren().clear();
    	
    	for ( int row = 0; row < ordering.length; row++ ) {
    		for ( int col = 0; col < ordering[row].length; col++ ) { 
    			int index = ordering[row][col];
    			if ( index == 0 ) {
    				index = ordering[0].length * ordering.length - 1;
    			} else {
    				index--;
    			}
    			
    			Node javaFxTileObject = uiObjects.gameTiles[ index ].getJavaFxObject();
    			uiObjects.gamePane.add( javaFxTileObject, col, row );
    		}
    	}
    }
    
    public Optional<ButtonType> drawWinDialog( Window parent ) {
    	Dialog<ButtonType> dialog = new Dialog<>();
    	dialog.initOwner( parent );
    	
    	WinDialogLayout layout = new WinDialogLayout();
    	layout.setupLayout( dialog );
    	
    	return dialog.showAndWait();
    }
    
    public void onValidMove( GlobalUiObjects uiObjects, GridPoint emptyTilePos, GridPoint tileToMovePos ) {
    	GridPane gamePane = uiObjects.gamePane;
    	Node tile = getTileOnGrid( gamePane, tileToMovePos );
    	Node emptyPos = getTileOnGrid( gamePane, emptyTilePos );
    	
    	EventHandler<ActionEvent> onFinishedAnimation = new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			mAnimationEngine.removeOverlayParent( mRootPane );
    			gamePane.add( tile, emptyTilePos.column, emptyTilePos.row );
    			
    			gamePane.getChildren().remove( emptyPos );
    			gamePane.add( emptyPos, tileToMovePos.column, tileToMovePos.row );
    		}
    	};
    	
    	mAnimationEngine.setOverlayParent( mRootPane );
    	mAnimationEngine.playTileSlide( tile, emptyPos.getLayoutX(), emptyPos.getLayoutY(), onFinishedAnimation );
    }
    
    protected Node getTileOnGrid( GridPane grid, GridPoint gridPt ) {
    	for ( Node tile: grid.getChildrenUnmodifiable() ) {
    		if ( ( GridPane.getRowIndex( tile ) == gridPt.row ) && ( GridPane.getColumnIndex( tile ) == gridPt.column ) ) {
    			return tile;
    		}
    	}
    	
    	return null;
    }

}
