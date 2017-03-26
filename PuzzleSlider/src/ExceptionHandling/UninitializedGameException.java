package ExceptionHandling;

import java.lang.Exception;

public class UninitializedGameException extends Exception {
	private static final long serialVersionUID = -98661371029990809L;

	public UninitializedGameException() {
		super( "Game has not yet been initialized" );
	}

}
