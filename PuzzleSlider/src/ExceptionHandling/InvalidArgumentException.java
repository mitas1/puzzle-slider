package ExceptionHandling;

public class InvalidArgumentException extends Exception {
	private static final long serialVersionUID = -4770610846189873740L;

	public InvalidArgumentException() {
		super();
	}

	public InvalidArgumentException(String msg){
		super(msg);
	}

}
