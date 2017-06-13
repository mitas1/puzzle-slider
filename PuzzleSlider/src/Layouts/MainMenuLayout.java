package Layouts;

import Global.ImageRepository;
import Global.NumericalRepository;
import Global.StringRepository;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import Renderer.JavaFxUtils;
import UiObjects.GlobalUiObjects;

public class MainMenuLayout extends ScreenLayout {

	@Override
	public void setLayout( GlobalUiObjects uiObjects, Pane parent ) {
		showBackground( parent, ImageRepository.BACKGROUND_MENU );
		
		setLabels( uiObjects );
		setPositions(uiObjects);
		setStyles( uiObjects );
		
		ObservableList<Node> children = parent.getChildren();
		setVisible(uiObjects, children);
	}
	
	@Override
	protected void setLabels( GlobalUiObjects uiObjects ) {
		JavaFxUtils.setObjectLabel( uiObjects.exitButton, StringRepository.EXIT_GAME );
		JavaFxUtils.setObjectLabel( uiObjects.newGameButton, StringRepository.NEW_GAME );
		JavaFxUtils.setObjectLabel( uiObjects.loadGameButton, StringRepository.LOAD_GAME );
		JavaFxUtils.setObjectLabel( uiObjects.resumeGameButton, StringRepository.RESUME_GAME );
		JavaFxUtils.setObjectLabel( uiObjects.saveGameButton, StringRepository.SAVE_GAME );
	}

	@Override
	protected void setPositions( GlobalUiObjects uiObjects ) {
		JavaFxUtils.setObjectsPosition( uiObjects.resumeGameButton, NumericalRepository.LAYOUT_MENU_BUTTON_OFFSET_X - 5, 55 );
		JavaFxUtils.setObjectsPosition( uiObjects.newGameButton, NumericalRepository.LAYOUT_MENU_BUTTON_OFFSET_X, 110 );
		JavaFxUtils.setObjectsPosition( uiObjects.saveGameButton, NumericalRepository.LAYOUT_MENU_BUTTON_OFFSET_X + 20, 375 );
		JavaFxUtils.setObjectsPosition( uiObjects.loadGameButton, NumericalRepository.LAYOUT_MENU_BUTTON_OFFSET_X + 30, 470 );
		JavaFxUtils.setObjectsPosition( uiObjects.exitButton, NumericalRepository.LAYOUT_MENU_BUTTON_OFFSET_X + 110, 550 );
	}
	
	@Override
	protected void setStyles( GlobalUiObjects uiObjects ) {
		uiObjects.resumeGameButton.getStyleClass().add( StringRepository.CSS_CLASS_CONTROL_BUTTON );
        uiObjects.newGameButton.getStyleClass().addAll(
        		StringRepository.CSS_CLASS_CONTROL_BUTTON,
				StringRepository.CSS_CLASS_NEW_GAME_BUTTON
		);
        uiObjects.saveGameButton.getStyleClass().add( StringRepository.CSS_CLASS_CONTROL_BUTTON );
        uiObjects.loadGameButton.getStyleClass().add( StringRepository.CSS_CLASS_CONTROL_BUTTON );
        uiObjects.exitButton.getStyleClass().add( StringRepository.CSS_CLASS_CONTROL_BUTTON );
	}

	@Override
	protected void setVisible( GlobalUiObjects uiObjects, ObservableList<Node> rootChildren ) {
		rootChildren.addAll(
				uiObjects.newGameButton, uiObjects.resumeGameButton, uiObjects.saveGameButton,
				uiObjects.loadGameButton, uiObjects.exitButton 
				);
	}
}
