package de.raffi.pluginlib.mysql;

public class AlreadyConnectedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyConnectedException() {
		super();
	}

	public AlreadyConnectedException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public AlreadyConnectedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AlreadyConnectedException(String arg0) {
		super(arg0);
	}

	public AlreadyConnectedException(Throwable arg0) {
		super(arg0);
	}
	
}
