package Renderer;

import javafx.stage.Stage;

public class ScreenDrag {

	protected double mOffsetX;
	protected double mOffsetY;
	protected Stage mScreen;
	
	public ScreenDrag( Stage screen, double startX, double startY ) {
		mOffsetX = screen.getX() - startX;
		mOffsetY = screen.getY() - startY;
		mScreen = screen;
	}
	
	public void drag( double mouseEventX, double mouseEventY ) {
		mScreen.setX( mouseEventX + mOffsetX );
		mScreen.setY( mouseEventY + mOffsetY );
	}
	
}
