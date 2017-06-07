package Layouts;

import Global.ImageRepository;
import Global.NumericalRepository;
import Global.StringRepository;
import Renderer.JavaFxUtils;
import UiObjects.GlobalUiObjects;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class GameScreenLayout extends ScreenLayout {

	public void setLayout( GlobalUiObjects uiObjects, Pane parent ) {
		showBackground( parent, ImageRepository.BACKGROUND_GAME );
		
		setLabels(uiObjects);
		setStyles(uiObjects);
		setPositions(uiObjects);
		
		setGamePane( uiObjects );
		
		ObservableList<Node> rootChildren = parent.getChildren();
		setVisible(uiObjects, rootChildren);
	}

	@Override
	protected void setVisible(GlobalUiObjects uiObjects, ObservableList<Node> rootChildren) {
		rootChildren.addAll( 
				uiObjects.elapsedTimeLabel, uiObjects.moveCounterLabel, uiObjects.returnToMenuButton,
				uiObjects.gamePane
		);
	}

	@Override
	protected void setStyles(GlobalUiObjects uiObjects) {
		uiObjects.elapsedTimeLabel.getStyleClass().add(StringRepository.CSS_CLASS_TIMER);
		uiObjects.returnToMenuButton.getStyleClass().add(StringRepository.CSS_CLASS_CONTROL_BUTTON);
		// TODO: add CSS class to move counter !
	}

	@Override
	protected void setPositions(GlobalUiObjects uiObjects) {
		JavaFxUtils.setObjectsPosition( 
				uiObjects.elapsedTimeLabel, 
				NumericalRepository.LAYOUT_GAME_TIME_ELAPSED_OFFSET_X,
				NumericalRepository.LAYOUT_GAME_TIME_ELAPSED_OFFSET_Y 
		);
		
		JavaFxUtils.setObjectsPosition( 
				uiObjects.moveCounterLabel,
				NumericalRepository.LAYOUT_GAME_MOVE_COUNT_OFFSET_X,
				NumericalRepository.LAYOUT_GAME_MOVE_COUNT_OFFSET_Y
		);
		
		JavaFxUtils.setObjectsPosition( 
				uiObjects.returnToMenuButton,
				NumericalRepository.LAYOUT_GAME_MENU_BTN_OFFSET_X,
				NumericalRepository.LAYOUT_GAME_MENU_BTN_OFFSET_Y
		);
		
		JavaFxUtils.setObjectsPosition( 
				uiObjects.gamePane,
				NumericalRepository.LAYOUT_GAME_CANVAS_OFFSET_X,
				NumericalRepository.LAYOUT_GAME_CANVAS_OFFSET_Y
		);
	}

	@Override
	protected void setLabels(GlobalUiObjects uiObjects) {
		JavaFxUtils.setObjectLabel( uiObjects.elapsedTimeLabel, "00:00");
		JavaFxUtils.setObjectLabel( uiObjects.moveCounterLabel, "0" );
		JavaFxUtils.setObjectLabel( uiObjects.returnToMenuButton, StringRepository.MENU_LABEL );
	}
	
	protected void setGamePane( GlobalUiObjects uiObjects ) {
		uiObjects.gamePane.setPrefSize( NumericalRepository.LAYOUT_GAME_CANVAS_WIDTH, NumericalRepository.LAYOUT_GAME_CANVAS_HEIGHT );
		uiObjects.gamePane.setMaxSize( NumericalRepository.LAYOUT_GAME_CANVAS_WIDTH, NumericalRepository.LAYOUT_GAME_CANVAS_HEIGHT );
		uiObjects.gamePane.setAlignment( Pos.CENTER );
		uiObjects.gamePane.setBackground( new Background( new BackgroundFill( Paint.valueOf("0x000000"), null, null)));
	}
}
