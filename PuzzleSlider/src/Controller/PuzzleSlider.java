package Controller;
import java.io.IOException;
import java.util.ArrayList;

import Engine.Engine;
import ExceptionHandling.InvalidArgumentException;
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
		} catch (InvalidArgumentException e) {
			//WTF MATO?
		}
		mRenderer = new Renderer(primaryStage, this);
	}



	public static void main(String[] args) {
		launch(args);
	}



	public void setNewGameListener(Button newGameBtn) {
		newGameBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mRenderer.loadGameWindow(new ArrayList<Tile>());
			}
		});
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
