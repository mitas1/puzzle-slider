package Controller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Engine.Engine;
import Engine.Globals.GridPoint;
import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UninitializedGameException;
import Renderer.Renderer;
import Renderer.Tile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PuzzleSlider extends Application {

	Engine mEngine;
	Renderer mRenderer;
	Timeline timeCounter;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			mEngine = new Engine();
		} catch (InvalidArgumentException e) {}
		mRenderer = new Renderer(primaryStage, this);
	}

	public void setOnClickListener(Canvas canvas){

		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int size = mEngine.getGameData().getSize();
				int tileSize = mRenderer.canvasWidth/size;
				int row = (int)(event.getX()/tileSize);
				int col = (int)(event.getY()/tileSize);
				GridPoint emptyTile_temp = mEngine.findEmptyTileAround(new GridPoint(row,col));
				if (mEngine.canMove(row, col)) {
					List<Tile> tiles = new ArrayList<>();
					try {
						connectTiles(tiles);
					} catch (UninitializedGameException | InvalidArgumentException e) {
						e.printStackTrace();
					}
					Tile clickedTile =  null;
					Tile emptyTile = null;
					try {
						clickedTile = new Tile(row,col,tileSize,mEngine.getGameData().getTile(new GridPoint(row, col)));
						emptyTile = new Tile(emptyTile_temp.row, emptyTile_temp.column, tileSize,mEngine.getGameData().getTile(new GridPoint(emptyTile_temp.row,emptyTile_temp.column)));
					} catch (UninitializedGameException |InvalidArgumentException e) {
						e.printStackTrace();
					}

					//mRenderer.drawTiles(tiles);
					if (clickedTile != null && emptyTile != null){
						mRenderer.animateTiles(clickedTile, emptyTile);
					}
					mEngine.move(row, col);
					mRenderer.updateMoves(mEngine.getGameData().getMoveCount());
				}
			}
		});
	}

	public void setNewGameListener(Button newGameBtn) {
		newGameBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mRenderer.newGameDialog.showAndWait();
				//TODO Check
			}
		});
	}


	private void connectTiles(List<Tile> tiles) throws UninitializedGameException, InvalidArgumentException {
		int size = mEngine.getGameData().getSize();
		int tileSize = mRenderer.canvasWidth/size;
		for (int i = 0; i < size; i++){
			for (int j = 0;j < size; j++){
				tiles.add(new Tile(j,i,tileSize,mEngine.getGameData().getTile(new GridPoint(j, i))));
			}
		}
	}

	public void setMenuButtonListener(Button menuBtn) {
		menuBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mRenderer.showMenu();
				stopClock();
			}
		});
	}

	public void setQuitGameListener(Button quitGameBtn) {
		quitGameBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}

		});
	}

	public void setSaveGameListener(Button saveGameBtn) {
		saveGameBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					File saveFile;
					FileChooser file = new FileChooser();

					file.setTitle("Save file");
					file.setInitialDirectory(new File(System.getProperty("user.dir")));
					file.getExtensionFilters().add(new FileChooser.ExtensionFilter("Saves", "*.sav"));
					
					saveFile = file.showSaveDialog(null);
					
					if (saveFile != null){
						mEngine.saveGame(saveFile);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setLoadGameListener(Button loadGameBtn) {
		loadGameBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					File loadFile;
					FileChooser file = new FileChooser();
					
					file.setTitle("Load file");
					file.setInitialDirectory(new File(System.getProperty("user.dir")));
					file.getExtensionFilters().add(new FileChooser.ExtensionFilter("Saves", "*.sav"));
					
					loadFile = file.showOpenDialog(null);
					
					if (loadFile != null){
						mEngine.loadGame(loadFile);
						
						mRenderer.updateMoves(mEngine.getMoveCount());
						showPlayScreen();
					}
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setResumeGameListener(Button resumeGameBtn) {
		resumeGameBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showPlayScreen();
			}

		});
	}
	
	//TODO Check controller team
	public void startNewGame(int gameSize) {
		try {
			mEngine = new Engine(gameSize);
			mRenderer.updateMoves(0);
			showPlayScreen();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
	}
	
	private void showPlayScreen(){
		try {
			List<Tile> tiles = new ArrayList<>();
			connectTiles(tiles);
			mRenderer.loadGameWindow(tiles);
			startClock();
		} catch (UninitializedGameException | InvalidArgumentException e) {
			e.printStackTrace();
		}
	}
	
	private void startClock(){
		mEngine.resumeTimeCounter();
		timeCounter = new Timeline(new KeyFrame(Duration.millis(100), e -> {
			mRenderer.updateTime(mEngine.getElapsedTime());
		}));
		timeCounter.setCycleCount(Timeline.INDEFINITE);
		timeCounter.play();
	}
	
	private void stopClock(){
		timeCounter.stop();
	}
}
