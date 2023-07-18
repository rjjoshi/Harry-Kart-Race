package se.atg.harrykart.exception;

public class TieConditionException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public TieConditionException(String message) {
		super(message);
	}

}
