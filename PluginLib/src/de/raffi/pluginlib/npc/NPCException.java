package de.raffi.pluginlib.npc;

public class NPCException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NPCException() {
		super();
	}

	public NPCException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public NPCException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NPCException(String arg0) {
		super(arg0);
	}

	public NPCException(Throwable arg0) {
		super(arg0);
	}
	
}
