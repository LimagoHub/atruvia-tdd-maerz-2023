package de.limago.zeiterfassung.services.zeitmodelle;

public class ZeitmodellServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 680554137891993835L;

	public ZeitmodellServiceException() {
		// TODO Auto-generated constructor stub
	}

	public ZeitmodellServiceException(String message) {
		super(message);
		System.out.println();
		// TODO Auto-generated constructor stub
	}

	public ZeitmodellServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ZeitmodellServiceException(String message, Throwable cause) {
		super(message, cause);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
			Thread.currentThread().interrupt();

		}
	}

	public ZeitmodellServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
