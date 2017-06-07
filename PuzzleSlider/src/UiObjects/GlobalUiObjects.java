package UiObjects;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import Tiles.Tile;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GlobalUiObjects {

	//--------------------------------
	// Global
	//--------------------------------
	public Stage root;
	
	
	//--------------------------------
	// Main Menu
	//--------------------------------
	public Button newGameButton;
	public Button resumeGameButton;
	public Button saveGameButton;
	public Button loadGameButton;
	public Button exitButton;
	
	
	//--------------------------------
	// Game Screen
	//--------------------------------
	public Button returnToMenuButton;
	public Label elapsedTimeLabel;
	public Label moveCounterLabel;
	public GridPane gamePane;
	public Tile[] gameTiles;
	
}
