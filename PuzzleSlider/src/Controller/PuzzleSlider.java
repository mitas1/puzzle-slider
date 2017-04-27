package Controller;
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
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PuzzleSlider extends Application {

	Engine mEngine;
	Renderer mRenderer;


	@Override
	public void start(Stage primaryStage) {
		try {
			mEngine = new Engine();
		} catch (InvalidArgumentException e) {}
		mRenderer = new Renderer(primaryStage, this);
	}



	public static void main(String[] args) {
		launch(args);
	}



	public void setNewGameListener(Button newGameBtn) {
		newGameBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					mEngine = new Engine(4);
					List<Tile> tiles = new ArrayList<>();
					connectTiles(tiles);
					mRenderer.loadGameWindow(tiles);
				} catch (UninitializedGameException | InvalidArgumentException e) {
					e.printStackTrace();
				} 
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
}
