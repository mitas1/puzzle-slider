package Controller;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import Engine.Engine;
import Engine.Globals.GridPoint;
import ExceptionHandling.InvalidArgumentException;
import ExceptionHandling.UninitializedGameException;
import Renderer.Renderer;
import Renderer.Tile;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PuzzleSlider extends Application {

	Engine mEngine;
	Renderer mRenderer;

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
				if (mEngine.move(row, col)) {
					List<Tile> tiles = new ArrayList<>();
					try {
						connectTiles(tiles);
					} catch (UninitializedGameException | InvalidArgumentException e) {
						e.printStackTrace();
					}
					mRenderer.drawTiles(tiles);
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
		try {
			mEngine.saveGame("tmp.save");
			//			TODO change to dynamic
		} catch (IOException e) {
			e.printStackTrace();
			//			TODO change to popup
		}
	}



	public void setLoadGameListener(Button loadGameBtn) {
		try {
			mEngine.loadGame("tmp.save");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public void setResumeGameListener(Button resumeGameBtn) {
		resumeGameBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					List<Tile> tiles = new ArrayList<>();
					connectTiles(tiles);
					mRenderer.loadGameWindow(tiles);
				} catch (UninitializedGameException | InvalidArgumentException e) {
					e.printStackTrace();
				}
			}

		});
	}
	//TODO Check controller team
	public void startNewGame(int gameSize) {
		try {
			mEngine = new Engine(gameSize);
			List<Tile> tiles = new ArrayList<>();
			connectTiles(tiles);
			mRenderer.loadGameWindow(tiles);
		} catch (UninitializedGameException | InvalidArgumentException e) {
			e.printStackTrace();
		}
	}

}
