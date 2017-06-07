package Animations;

public abstract class PositionChangeAnimation extends Animation {
	
	protected double mDstX;
	protected double mDstY;
	
	
	public PositionChangeAnimation( double dstX, double dstY, long duration ) {
		super(duration);
		mDstX = dstX;
		mDstY = dstY;
	}
	
}
