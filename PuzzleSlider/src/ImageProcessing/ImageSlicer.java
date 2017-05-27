package ImageProcessing;

import ExceptionHandling.InvalidArgumentException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class ImageSlicer {

	public Image[] GetImageTiles( Image inputImg, int gameSize ) throws InvalidArgumentException {
		PixelReader bitmapSource = inputImg.getPixelReader();
		
		int tileSize = getTileSize(inputImg, gameSize);
		
		if ( tileSize > 0 ) {
			return CreateTiles(bitmapSource, tileSize, gameSize);
		} else {
			throw new InvalidArgumentException( "inputImg is too small ");
		}
	};
	
	public Image getBiggestSquare( Image sourceImage, int requestedSquareSide ) {
		Image dstImage = getBiggestSquare(sourceImage);
		return resizeTo( dstImage, requestedSquareSide );
	}
	
	protected int getTileSize( Image inputImage, int gameSize ) {
		return (int)Math.min( inputImage.getWidth(), inputImage.getHeight() ) / gameSize;
	};
	
	protected Image[] CreateTiles( PixelReader bitmapSource, int tileSize, int gameSize ) {
		Image[] tiles = new Image[gameSize * gameSize];
		
		for ( int r = 0; r < gameSize; r++) {
			for ( int c = 0; c < gameSize; c++) {
				WritableImage tile = new WritableImage( tileSize, tileSize );
				tile.getPixelWriter().setPixels(0, 0, tileSize, tileSize, bitmapSource, c * tileSize, r * tileSize);
				tiles[r * gameSize + c] = tile;
			}
		}
		
		return tiles;
	};
	
	protected WritableImage getBiggestSquare( Image sourceImg ) {
		int srcSquareSide = (int)Math.min( sourceImg.getWidth(), sourceImg.getHeight() );
		WritableImage dstImage = new WritableImage( srcSquareSide, srcSquareSide );
		dstImage.getPixelWriter().setPixels(0, 0, srcSquareSide, srcSquareSide, sourceImg.getPixelReader(), 0, 0);
		return dstImage;
	}
	
	protected Image resizeTo(Image sourceImage, int side ) {
		ImageView scalingView = new ImageView( sourceImage );
		scalingView.setSmooth(true);
		scalingView.setFitWidth(side);
		scalingView.setFitHeight(side);
		return scalingView.snapshot(null, null);
	}
	
}
