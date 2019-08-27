package db;

public class DBExceptions extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DBExceptions() {
		this("DB error.");
	}
	
	public DBExceptions(String message) {
		super(message);
	}
}
