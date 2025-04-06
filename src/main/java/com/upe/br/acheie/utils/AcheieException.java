package com.upe.br.acheie.utils;

import java.io.Serial;

public class AcheieException extends RuntimeException {
	
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	public AcheieException(String mensagem) {
		super(mensagem);
	}
	
	public AcheieException(String mensagem, Throwable origem) {
		super(mensagem, origem);
	}


}
