package Renderer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static Renderer.Renderer.gameWindowHeight;
import static Renderer.Renderer.gameWindowWidth;

import java.util.ArrayList;

import ConsoleController.Controller;
import Controller.PuzzleSlider;

public class Renderer {
    public static int gameWindowWidth=1024;
    public static int gameWindowHeight =768;
    private final int canvasWidth=495;
    private final int canvasHeight =495;

    private final String imagesPath = "resources/images/";
    private final Image menuBackgroundImage = new Image(imagesPath+"main_background.png");
    private final Image gameBackgroundImage = new Image(imagesPath+"game_background.png");

    private final int gameCanvasOffsetX = 398;
    private final int gameCanvasOffsetY = 131;
    private Pane root;

    Canvas backgroundCanvas,gameCanvas;

    Label movesLabel, timeLabel;

    Button newGameBtn,loadGameBtn,saveGameBtn,quitGameBtn,menuBtn;
    
    PuzzleSlider controller;



    public Renderer(Stage primaryStage, PuzzleSlider controller){
    	this.root = new Pane();
		Scene scene = new Scene(root, gameWindowWidth, gameWindowHeight);
		this.controller = controller;
        setupMenu();
        
        
        this.backgroundCanvas= new Canvas(gameWindowWidth,gameWindowHeight);
        this.gameCanvas = new Canvas(canvasWidth,canvasHeight);
        
		showMenu();
		primaryStage.setScene( scene );
		primaryStage.show();


    }



    public void setupMenu(){
        newGameBtn = new Button("New Game");
        saveGameBtn = new Button("Save Game");
        loadGameBtn = new Button("Load Game");
        quitGameBtn = new Button("Quit Game");

        newGameBtn.setLayoutX(700);
        newGameBtn.setLayoutY(140);
        saveGameBtn.setLayoutX(700);
        saveGameBtn.setLayoutY(170);
        loadGameBtn.setLayoutX(700);
        loadGameBtn.setLayoutY(200);
        quitGameBtn.setLayoutX(700);
        quitGameBtn.setLayoutY(530);
        
        controller.setNewGameListener(newGameBtn);
        controller.setQuitGameListener(quitGameBtn);
//        other listeneres...

    }

    public void showMenu(){
        GraphicsContext gc = this.backgroundCanvas.getGraphicsContext2D();
        gc.drawImage(menuBackgroundImage,0,0);

        this.root.getChildren().clear();
        this.root.getChildren().addAll(backgroundCanvas,newGameBtn,saveGameBtn,loadGameBtn,quitGameBtn);
    }


    public void loadGameWindow(ArrayList<Tile> tiles){
        timeLabel = new Label("00:00");
        movesLabel = new Label("0");

        timeLabel.setLayoutX(120);
        timeLabel.setLayoutY(135);
        timeLabel.setStyle("-fx-font-size: 2em;");
        timeLabel.setTextFill(Color.WHITE);
        movesLabel.setLayoutX(120);
        movesLabel.setLayoutY(180);
        movesLabel.setStyle("-fx-font-size: 2em;");
        movesLabel.setTextFill(Color.WHITE);

        menuBtn = new Button("MENU");


        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();

        gc.drawImage(gameBackgroundImage,0,0);

        gameCanvas.setLayoutX(gameCanvasOffsetX);
        gameCanvas.setLayoutY(gameCanvasOffsetY);

        menuBtn.setLayoutX(120);
        menuBtn.setLayoutY(340);
        
        
        controller.setMenuButtonListener(menuBtn);

        GraphicsContext tilesGraphicsContext = gameCanvas.getGraphicsContext2D();
        tilesGraphicsContext.clearRect(0,0,canvasWidth,canvasHeight);

        for (Tile tile: tiles){
            tile.draw(tilesGraphicsContext);
        }

        this.root.getChildren().clear();
        this.root.getChildren().addAll(backgroundCanvas,gameCanvas, menuBtn, timeLabel, movesLabel);
    }

    public void updateMoves(String moves){
        this.movesLabel.setText(moves);
    }

    public void updateTime(String time){
        this.timeLabel.setText(time);
    }



}
