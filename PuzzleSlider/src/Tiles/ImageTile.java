package Tiles;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class ImageTile extends Tile {
	
	Node mTileView;
	
	public ImageTile( int width, int height, Image image ) {
		super(width, height);
		mTileView = new ImageView( image );
		// TODO: onClick thingies
	}

	@Override
	public Node getJavaFxObject() {
		return mTileView;
	}
	
	@Override
	public void setToEmpty() {
		mTileView = new Rectangle(mWidth, mHeight);
	}

}
