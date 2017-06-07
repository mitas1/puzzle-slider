package Renderer;

import Global.FileExtension;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class JavaFxUtils {

	public static void setObjectLabel( Labeled object, String label ) {
		object.setText( label );
	}
	
	public static void setObjectsPosition(Node object, int x, int y) {
		object.setLayoutX(x);
        object.setLayoutY(y);
    }
	
	public static ExtensionFilter getImageExtensionFilter( FileExtension extension ) {
		return new FileChooser.ExtensionFilter(extension.description, extension.extensions );
	}
}
