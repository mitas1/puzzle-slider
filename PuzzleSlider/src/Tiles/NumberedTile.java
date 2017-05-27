package Tiles;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class NumberedTile extends Tile {

	int mNumber;
	Node mJavaFxObject;
	
	public NumberedTile( int width, int height, int number ) {
		super( width, height );
		mNumber = number;
		buildJavaFxObject();
	}
	
	@Override
	public Node getJavaFxObject() {
		return mJavaFxObject;
	}
	
	@Override
	public void setToEmpty() {
		Rectangle empty = new Rectangle(mWidth, mHeight);
		empty.setFill( Paint.valueOf("0x000000"));
		mJavaFxObject = empty;
		mNumber = 0;
	}
	
	protected void buildJavaFxObject() {
		// TODO: work on this
		
		StackPane base = new StackPane();
		
		Rectangle rectangle = new Rectangle( mWidth, mHeight );
		rectangle.setFill( Paint.valueOf( "0x000000" ));
		
		Label numberLabel = new Label( String.format("%d", mNumber) );
		numberLabel.setFont( new Font("Permanent Marker", 22) );
		numberLabel.setTextFill( Paint.valueOf("0xFFFFFF") );
		numberLabel.setAlignment( Pos.CENTER );
		
		base.getChildren().addAll( rectangle, numberLabel );
		
		mJavaFxObject = base;
	}
}
