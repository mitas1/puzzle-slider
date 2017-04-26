package Controller;

import Engine.Engine;
import Renderer.Renderer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static Renderer.Renderer.gameWindowHeight;
import static Renderer.Renderer.gameWindowWidth;

public class PuzzleSlider extends Application {
	
	Engine mEngine;
	Renderer mRenderer;



	@Override
	public void start(Stage primaryStage) {
		Pane root = new Pane();
		mRenderer = new Renderer(root);

		Scene scene = new Scene(root, gameWindowWidth, gameWindowHeight);

		mRenderer.showMenu();

		primaryStage.setScene( scene );
		primaryStage.show();
	}


	



	public static void main(String[] args) {
		launch(args);
	}
}
