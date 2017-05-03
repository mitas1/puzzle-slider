package Renderer;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

import Controller.PuzzleSlider;

public class Renderer extends Pane{
    //TODO start game with image
	public final int gameWindowWidth=1024;
	public final int gameWindowHeight =768;
	public final int canvasWidth=495;
	public final int canvasHeight =495;

	public static final int minGameSize = 4;
	public static final int maxGameSize = 10;

	private final String imagesPath = "resources/images/";
	private final Image menuBackgroundImage = new Image(imagesPath+"main_background.png");
	private final Image gameBackgroundImage = new Image(imagesPath+"game_background.png");

	private final int gameCanvasOffsetX = 398;
	private final int gameCanvasOffsetY = 131;

	Canvas backgroundCanvas,gameCanvas;

	Label movesLabel, timeLabel;

	Button newGameBtn,loadGameBtn,saveGameBtn,quitGameBtn,menuBtn,resGameBtn;

	PuzzleSlider controller;

	SimpleBooleanProperty gamePaused;
	private boolean gameStarted;

	public NewGameDialog newGameDialog;

	Image picture;
    boolean imageGame;

    public Renderer(Stage primaryStage, PuzzleSlider controller){
		Scene scene = new Scene(this, gameWindowWidth, gameWindowHeight);
		this.controller = controller;
		init();

		primaryStage.setScene( scene );
		primaryStage.show();

        setupGame();

		newGameDialog = new NewGameDialog(primaryStage, controller, this);

	}

	private void init(){
        this.backgroundCanvas= new Canvas(gameWindowWidth,gameWindowHeight);
        this.gameCanvas = new Canvas(canvasWidth,canvasHeight);
        controller.setOnClickListener(this.gameCanvas);

        gamePaused = new SimpleBooleanProperty(false);
        gameStarted = false;
        setupMenu();
        showMenu();
    }


	public void setupMenu(){
    	resGameBtn = new Button("Resume Game");
        newGameBtn = new Button("New Game");
        saveGameBtn = new Button("Save Game");
		loadGameBtn = new Button("Load Game");
		quitGameBtn = new Button("Quit Game");

        resGameBtn.disableProperty().bind(gamePaused.not());
        saveGameBtn.disableProperty().bind(gamePaused.not());

        setObjectsPos(resGameBtn,700,100);
        setObjectsPos(newGameBtn,700,190);
        setObjectsPos(saveGameBtn,700,295);
        setObjectsPos(loadGameBtn,700,390);
        setObjectsPos(quitGameBtn,700,530);

        controller.setResumeGameListener(resGameBtn);
		controller.setNewGameListener(newGameBtn);
		controller.setSaveGameListener(saveGameBtn);
		controller.setLoadGameListener(loadGameBtn);
		controller.setQuitGameListener(quitGameBtn);
		//        other listeneres...

	}

    private void setObjectsPos(Node object, int x, int y) {
        object.setLayoutX(x);
        object.setLayoutY(y);
    }

    public void showMenu(){
	    if (gamePaused.not().get() && gameStarted){
            pauseGame();
        }
		GraphicsContext gc = this.backgroundCanvas.getGraphicsContext2D();
		gc.drawImage(menuBackgroundImage,0,0);

        this.getChildren().clear();
		this.getChildren().addAll(backgroundCanvas,resGameBtn,newGameBtn,saveGameBtn,loadGameBtn,quitGameBtn);

	}

	public void setupGame(){
        timeLabel = new Label("00:00");
        movesLabel = new Label("0");

        setObjectsPos(timeLabel,120,135);
        timeLabel.setStyle("-fx-font-size: 2em;");
        timeLabel.setTextFill(Color.WHITE);

        setObjectsPos(movesLabel,120,180);
        movesLabel.setStyle("-fx-font-size: 2em;");
        movesLabel.setTextFill(Color.WHITE);

        menuBtn = new Button("MENU");
        setObjectsPos(menuBtn,120,340);
        controller.setMenuButtonListener(menuBtn);

        gameCanvas.setLayoutX(gameCanvasOffsetX);
        gameCanvas.setLayoutY(gameCanvasOffsetY);
    }


	public void loadGameWindow(List<Tile> tiles){
	    gameStarted = true;
	    if (!gamePaused.get())
	        resetVars();

        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
        gc.drawImage(gameBackgroundImage,0,0);

		drawTiles(tiles);

		this.getChildren().clear();
		this.getChildren().addAll(backgroundCanvas,gameCanvas, menuBtn, timeLabel, movesLabel);
	}



	public void updateMoves(int moves){
		movesLabel.setText(Integer.toString(moves));
	}

	public void updateTime(int seconds) {
        String min = (seconds/60<10 ? "0" : "" )+seconds/60;
        String sec = (seconds%60<10 ? "0" : "" )+seconds%60;
        StringBuilder time = new StringBuilder();
        time.append(min+":"+sec);
        timeLabel.setText(time.toString());
	}



	public void drawTiles(List<Tile> tiles) {
		GraphicsContext tilesGraphicsContext = gameCanvas.getGraphicsContext2D();
		tilesGraphicsContext.clearRect(0,0,canvasWidth,canvasHeight);
		for (Tile tile: tiles){
			tile.draw(tilesGraphicsContext);
		}
	}


    protected void resetVars(){
	    updateMoves(0);
	    updateTime(0);
	    pauseGame();
    }

    public void pauseGame() {
	    gamePaused.set(gamePaused.not().get());
    }

    public void setPictureGame(Image img){
        imageGame = true;
        picture = img;
    }

}
