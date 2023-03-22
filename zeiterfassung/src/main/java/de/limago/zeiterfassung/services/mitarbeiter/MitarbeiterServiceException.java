package de.limago.zeiterfassung.services.mitarbeiter;

public class MitarbeiterServiceException extends Exception {

	private static final long serialVersionUID = -260939696005475550L;

	public MitarbeiterServiceException() {
		// Ok
	}

	public MitarbeiterServiceException(String message) {
		super(message);

	}

	public MitarbeiterServiceException(Throwable cause) {
		super(cause);
	}

	public MitarbeiterServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public MitarbeiterServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
