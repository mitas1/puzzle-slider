package Controller;

import Engine.Engine;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PuzzleSlider extends Application {
	
	Engine mEngine;
	// ??? mRenderer;

	private int gameWindowWidth=1024;
	private int gameWindowHeight =768;
	private int canvasWidth=495;
	private int canvasHeight =495;

	private int gameCanvasOffsetX = 398;
	private int gameCanvasOffsetY = 131;


	@Override
	public void start(Stage primaryStage) {
		Pane root = new Pane();
		Scene scene = new Scene(root, gameWindowWidth, gameWindowHeight);
		
		loadGameWindow(root,5);

		primaryStage.setScene( scene );
		primaryStage.show();
	}

	public void menuWindow(){

		Button newGame = new Button("New Game");
		Button saveGame = new Button("Save Game");
		Button loadGame = new Button("Load Game");
		Button quitGame = new Button("Quit Game");

		VBox vBox = new VBox();
		vBox.getChildren().addAll(newGame,saveGame,loadGame,quitGame); //Menu screen

	}

	
	public void loadGameWindow(Pane root,int tileSize){ //tileSize - matrix dimension

		Button menuBtn = new Button("MENU");

		Canvas backgroundCanvas= new Canvas(gameWindowWidth,gameWindowHeight);
		Canvas gameCanvas = new Canvas(canvasWidth,canvasHeight);

		GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
		Image background = new Image("resources/game_background.png");
		gc.drawImage(background,0,0);

		gameCanvas.setLayoutX(gameCanvasOffsetX);
		gameCanvas.setLayoutY(gameCanvasOffsetY);

		menuBtn.setLayoutX(120);
		menuBtn.setLayoutY(340);

		GraphicsContext tilesGraphicsContext = gameCanvas.getGraphicsContext2D();
		tilesGraphicsContext.clearRect(0,0,canvasWidth,canvasHeight);

		Tile t1= new Tile(0,0,canvasWidth/tileSize,9);
		t1.draw(tilesGraphicsContext);

		root.getChildren().addAll(backgroundCanvas,gameCanvas, menuBtn);

	}


	public static void main(String[] args) {
		launch(args);
	}
}
