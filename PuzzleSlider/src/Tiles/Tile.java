package Tiles;

import javafx.scene.Node;

public abstract class Tile {
	
	protected int mWidth;
	protected int mHeight;
	
	public Tile( int width, int height ) {
		mWidth = width;
		mHeight = height;
	}
	
	public abstract void setToEmpty();
	
	public abstract Node getJavaFxObject();
	
}