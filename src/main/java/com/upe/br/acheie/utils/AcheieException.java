package com.upe.br.acheie.utils;

import java.io.Serial;

public class AcheieException extends RuntimeException {
	
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	public AcheieException(String message) {
		super(message);
	}
	
	public AcheieException(String message, Throwable origin) {
		super(message, origin);
	}


}
