package ExceptionHandling;

public class UnitionalizedGameException extends Exception {
	private static final long serialVersionUID = -98661371029990809L;
	protected String mMsg;

	public UnitionalizedGameException() {
		super();
	}

	public UnitionalizedGameException(String msg){
		super(msg);
	}

}
