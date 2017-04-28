package Renderer;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.List;

import Controller.PuzzleSlider;

public class Renderer{
	public final int gameWindowWidth=1024;
	public final int gameWindowHeight =768;
	public final int canvasWidth=495;
	public final int canvasHeight =495;

	private final String imagesPath = "resources/images/";
	private final Image menuBackgroundImage = new Image(imagesPath+"main_background.png");
	private final Image gameBackgroundImage = new Image(imagesPath+"game_background.png");

	private final int gameCanvasOffsetX = 398;
	private final int gameCanvasOffsetY = 131;
	private Pane root;

	Canvas backgroundCanvas,gameCanvas;

	Label movesLabel, timeLabel;

	Button newGameBtn,loadGameBtn,saveGameBtn,quitGameBtn,menuBtn,resGameBtn;

	PuzzleSlider controller;

	int seconds;
	int moves;
	boolean pause;


    public Renderer(Stage primaryStage, PuzzleSlider controller){
		this.root = new Pane();
		Scene scene = new Scene(root, gameWindowWidth, gameWindowHeight);
		this.controller = controller;
		setupMenu();


		this.backgroundCanvas= new Canvas(gameWindowWidth,gameWindowHeight);
		this.gameCanvas = new Canvas(canvasWidth,canvasHeight);
		controller.setOnClickListener(this.gameCanvas);

		showMenu();

		primaryStage.setScene( scene );
		primaryStage.show();

        setupGame();
	}


	public void setupMenu(){
        resGameBtn = new Button("Resume Game");
        newGameBtn = new Button("New Game");
        saveGameBtn = new Button("Save Game");
		loadGameBtn = new Button("Load Game");
		quitGameBtn = new Button("Quit Game");

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
		GraphicsContext gc = this.backgroundCanvas.getGraphicsContext2D();
		gc.drawImage(menuBackgroundImage,0,0);

        resGameBtn.disableProperty().bind(new SimpleBooleanProperty(pause).not());
        saveGameBtn.disableProperty().bind(new SimpleBooleanProperty(pause).not());

        this.root.getChildren().clear();
		this.root.getChildren().addAll(backgroundCanvas,resGameBtn,newGameBtn,saveGameBtn,loadGameBtn,quitGameBtn);
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

        pause = true;
    }


	public void loadGameWindow(List<Tile> tiles){//start new Game
	    if (!pause)
	        resetVars();

        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
        gc.drawImage(gameBackgroundImage,0,0);

		drawTiles(tiles);

		this.root.getChildren().clear();
		this.root.getChildren().addAll(backgroundCanvas,gameCanvas, menuBtn, timeLabel, movesLabel);
	}



	public void updateMoves(String moves){
		movesLabel.setText(moves);
	}

	public void updateTime() {
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
		if (!pause) {
            updateMoves("" + (moves++));
        }
        pause=false;
	}


    protected void resetVars(){
	    movesLabel.setText("0");
	    timeLabel.setText("00:00");
	    seconds = 0;
	    pause = false;
	    moves = 0;
    }

    public void pauseGame() {
	    pause = !pause;
    }
}
