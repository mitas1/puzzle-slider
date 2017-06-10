package Tiles;

import Global.StringRepository;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class NumberedTile extends Tile {
	
	protected static final double FONT_SCALE_SWEETSPOT = 0.6;
	
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
		empty.getStyleClass().add( StringRepository.CSS_CLASS_EMPTY_RECTANGLE );
		mJavaFxObject = empty;
		mNumber = 0;
	}
	
	protected void buildJavaFxObject() {
		
		StackPane base = new StackPane();
		
		Rectangle rectangle = new Rectangle( mWidth, mHeight );
		rectangle.getStyleClass().add( StringRepository.CSS_CLASS_RECTANGLE );

		Label numberLabel = new Label( String.format( "%d", mNumber) );
		numberLabel.getStyleClass().add( StringRepository.CSS_CLASS_NUMBER_LABEL );
		numberLabel.setFont( new Font( "Permanent Marker", mWidth * FONT_SCALE_SWEETSPOT ) );

		base.getChildren().addAll( numberLabel, rectangle);
		
		mJavaFxObject = base;
	}
}
