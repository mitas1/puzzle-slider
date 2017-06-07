package Layouts;

import UiObjects.GlobalUiObjects;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public abstract class ScreenLayout {
	
	public abstract void setLayout(GlobalUiObjects uiObjects, Pane parent);

	protected abstract void setVisible(GlobalUiObjects uiObjects, ObservableList<Node> rootChildren);

	protected abstract void setStyles(GlobalUiObjects uiObjects);

	protected abstract void setPositions(GlobalUiObjects uiObjects);

	protected abstract void setLabels(GlobalUiObjects uiObjects);

	protected void showBackground(Pane parent, Image backgroundImage) {
		Canvas bgCanvas = new Canvas( parent.getWidth(), parent.getHeight() );
		bgCanvas.getGraphicsContext2D().drawImage( backgroundImage, 0, 0);
		parent.getChildren().add( bgCanvas );
	}

}