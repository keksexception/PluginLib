package de.raffi.pluginlib.converter;

public class ConvertException extends Throwable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConvertException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ConvertException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConvertException(String message) {
		super(message);
	}

	public ConvertException(Throwable cause) {
		super(cause);
	}
	

}
