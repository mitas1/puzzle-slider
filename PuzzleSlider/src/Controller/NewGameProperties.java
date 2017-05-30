package Controller;

import java.io.Serializable;

public class NewGameProperties implements Serializable {

	private static final long serialVersionUID = 8301876490632030385L;
	
	public int gameSize;
	public boolean hasImageTiles;
	public String imagePath;
	public long elapsedTime;
	
	public NewGameProperties() {
		gameSize = 0;
		hasImageTiles = false;
		imagePath = null;
		elapsedTime = 0;
	}
	
	public NewGameProperties( int gameSize, String imagePath, long elapsedTime ) {
		this.gameSize = gameSize;
		this.elapsedTime = elapsedTime;
		
		if ( imagePath != null ) {
			hasImageTiles = true;
			this.imagePath = imagePath;
		} else {
			hasImageTiles = false;
		}
	}
	
}
