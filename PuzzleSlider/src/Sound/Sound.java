package Sound;

public abstract class Sound {
	
	protected String mSrc;
	
	public Sound(String src) {
		mSrc = src;
	}
	
	
	public abstract void play();
	

}
