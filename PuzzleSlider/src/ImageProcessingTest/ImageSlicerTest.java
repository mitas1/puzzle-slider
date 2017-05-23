package ImageProcessingTest;

import static org.junit.Assert.*;

import org.junit.Test;

import ExceptionHandling.InvalidArgumentException;
import ImageProcessing.ImageSlicer;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class ImageSlicerTest {
	
	private class ImageSlicerInternal extends ImageSlicer {
		
		public int tileSize( Image inputImage, int gameSize ) {
			return getTileSize(inputImage, gameSize);
		};
	}

	@Test
	public void tileSizesEven() {
		ImageSlicerInternal slicer = new ImageSlicerInternal();
		
		WritableImage image = new WritableImage(100, 100);
		WritableImage image2 = new WritableImage(350, 350);
		int gameSize = 5;
		int gameSize2 = 14;
		
		assertEquals( 100/5, slicer.tileSize(image, gameSize));
		assertEquals( 350/14, slicer.tileSize(image2, gameSize2));
		assertEquals( 100/14, slicer.tileSize(image, gameSize2));
		assertEquals( 350/5, slicer.tileSize(image2, gameSize));
	}
	
	@Test
	public void tileSizesNotEven() {
		ImageSlicerInternal slicer = new ImageSlicerInternal();
		
		WritableImage image = new WritableImage(100, 70);
		WritableImage image2 = new WritableImage(230, 500);
		int gameSize = 9;
		int gameSize2 = 4;
		
		assertEquals( 70/9, slicer.tileSize(image, gameSize));
		assertEquals( 230/4, slicer.tileSize(image2, gameSize2));
		assertEquals( 70/4, slicer.tileSize(image, gameSize2));
		assertEquals( 230/9, slicer.tileSize(image2, gameSize));
	}
	
	@Test
	public void tileArrayLengthEvenSize() throws InvalidArgumentException {
		ImageSlicer slicer = new ImageSlicer();
		
		WritableImage image = new WritableImage(200, 200);
		WritableImage image2 = new WritableImage(450, 450);
		int gameSize = 3;
		int gameSize2 = 8;
		
		Image[] arr1 = slicer.GetImageTiles(image, gameSize);
		Image[] arr2 = slicer.GetImageTiles(image, gameSize2);
		Image[] arr3 = slicer.GetImageTiles(image2, gameSize);
		Image[] arr4 = slicer.GetImageTiles(image2, gameSize2);
		
		assertEquals( gameSize*gameSize, arr1.length);
		assertEquals( gameSize2*gameSize2, arr2.length);
		assertEquals(gameSize*gameSize, arr3.length);
		assertEquals(gameSize2*gameSize2, arr4.length);
	}

	@Test
	public void tileArrayLengthUnevenSize() throws InvalidArgumentException {
		ImageSlicer slicer = new ImageSlicer();
		
		WritableImage image = new WritableImage(1220, 400);
		WritableImage image2 = new WritableImage(225, 4310);
		int gameSize = 3;
		int gameSize2 = 8;
		
		Image[] arr1 = slicer.GetImageTiles(image, gameSize);
		Image[] arr2 = slicer.GetImageTiles(image, gameSize2);
		Image[] arr3 = slicer.GetImageTiles(image2, gameSize);
		Image[] arr4 = slicer.GetImageTiles(image2, gameSize2);
		
		assertEquals( gameSize*gameSize, arr1.length);
		assertEquals( gameSize2*gameSize2, arr2.length);
		assertEquals(gameSize*gameSize, arr3.length);
		assertEquals(gameSize2*gameSize2, arr4.length);
	}
	
	@Test( expected = InvalidArgumentException.class )
	public void GetImageTilesThrows() throws InvalidArgumentException {
		ImageSlicer slicer = new ImageSlicer();
		WritableImage image = new WritableImage(10, 10);
		int gameSize = 12;
		
		slicer.GetImageTiles(image, gameSize);
	}
	
	// can't test tile-ing, coz java and it's color encoding....fucking hell...
}
