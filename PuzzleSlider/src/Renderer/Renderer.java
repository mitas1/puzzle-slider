package Renderer;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

import Controller.PuzzleSlider;

public class Renderer extends Pane{
	public final int gameWindowWidth=1024;
	public final int gameWindowHeight =768;
	
	public static final int canvasWidth=495;
	public static final int canvasHeight =495;

	public static final int minGameSize = 4;
	public static final int maxGameSize = 10;

	private final String imagesPath = "/resources/images/";
	private final Image menuBackgroundImage = new Image(imagesPath+"main_background.png");
	private final Image gameBackgroundImage = new Image(imagesPath+"game_background.png");

	private final int gameCanvasOffsetX = 398;
	private final int gameCanvasOffsetY = 131;
	
	Canvas backgroundCanvas,gameCanvas;

	Label movesLabel, timeLabel, movesTextLabel, timeTextLabel;

	Button newGameBtn,loadGameBtn,saveGameBtn,quitGameBtn,menuBtn,resGameBtn;

	PuzzleSlider controller;
	
	SimpleBooleanProperty gamePaused;
	private boolean gameStarted;

	public NewGameDialog newGameDialog;
    Dialog win;
    ButtonType winNewGameButton;

	Image picture;
    boolean imageGame;

    int xButtonPos = 560;

    public Renderer(Stage primaryStage, PuzzleSlider controller){
        Font.loadFont(Renderer.class.getResource("/resources/fonts/PermanentMarker.ttf").toExternalForm(), 10);

		Scene scene = new Scene(this, gameWindowWidth, gameWindowHeight);
        scene.getStylesheets().add(getClass().getResource("GUI_styles.css").toExternalForm());

        this.controller = controller;
		init();

		primaryStage.setScene( scene );
		primaryStage.show();

        win = new Dialog();
        win.initOwner(primaryStage);

		newGameDialog = new NewGameDialog(primaryStage, controller, this);

        setupGame();
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

		resGameBtn.getStyleClass().add("control-button");
        newGameBtn.getStyleClass().add("control-button");
        saveGameBtn.getStyleClass().add("control-button");
        loadGameBtn.getStyleClass().add("control-button");
        quitGameBtn.getStyleClass().add("control-button");

        resGameBtn.disableProperty().bind(gamePaused.not());
        saveGameBtn.disableProperty().bind(gamePaused.not());

        setObjectsPos(resGameBtn,xButtonPos,80);
        setObjectsPos(newGameBtn,xButtonPos,170);
        setObjectsPos(saveGameBtn,xButtonPos,260);
        setObjectsPos(loadGameBtn,xButtonPos,350);
        setObjectsPos(quitGameBtn,xButtonPos,480);

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
        timeTextLabel = new Label("Time:");
        movesTextLabel = new Label("Moves:");

        setObjectsPos(timeLabel,90,118);
        timeLabel.getStyleClass().add("time-label");

        setObjectsPos(movesLabel,150,210);
        movesLabel.getStyleClass().add("moves-label");

        setObjectsPos(timeTextLabel,95,71);
        timeTextLabel.getStyleClass().add("time-label");
        
        setObjectsPos(movesTextLabel,90,163);
        movesTextLabel.getStyleClass().add("moves-label");
        
        menuBtn = new Button("MENU");
        setObjectsPos(menuBtn,45,300);
        controller.setMenuButtonListener(menuBtn);
        menuBtn.getStyleClass().add("control-button");

        gameCanvas.setLayoutX(gameCanvasOffsetX);
        gameCanvas.setLayoutY(gameCanvasOffsetY);

        setupWinningDialog();
    }


	public void loadGameWindow(List<Tile> tiles){
	    gameStarted = true;
	  
        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
        gc.drawImage(gameBackgroundImage,0,0);

		drawTiles(tiles);

		this.getChildren().clear();
		this.getChildren().addAll(backgroundCanvas,gameCanvas, menuBtn, timeLabel, movesLabel, timeTextLabel, movesTextLabel);
	}



	public void updateMoves(int moves){
		movesLabel.setText(Integer.toString(moves));
	}

	public void updateTime(long seconds) {
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
		//tilesGraphicsContext.drawImage(picture,0,0);

	}


    protected void resetVars(){
	    updateMoves(0);
	    updateTime(0);
	    pauseGame();
    }

    public void pauseGame(){
        gamePaused.set(gamePaused.not().get());
    }

    public void setPictureGame(Image img){
        imageGame = true;
        picture = img;
    }

    private void setupWinningDialog(){
        win.setTitle("Won game!");
        win.setHeaderText("Congratulations! You've Won!");
        win.setGraphic(new ImageView(this.getClass().getResource(imagesPath+"fireworks-icon.png").toString()));
        winNewGameButton = new ButtonType("New Game", ButtonBar.ButtonData.OK_DONE);
        ButtonType winMenuButton = new ButtonType("Menu", ButtonBar.ButtonData.OK_DONE);
        win.getDialogPane().getButtonTypes().addAll(winNewGameButton,winMenuButton);
    }

    public void showWinningDialog(){
        Optional<ButtonType> result = win.showAndWait();
        gameStarted = false;
        pauseGame();
        if (result.get()==winNewGameButton){
            newGameDialog.showAndWait();
        }
        else{
            showMenu();
        }
    }

}
