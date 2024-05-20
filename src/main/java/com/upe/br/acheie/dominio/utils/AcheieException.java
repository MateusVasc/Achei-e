package com.upe.br.acheie.dominio.utils;

public class AcheieException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AcheieException(String mensagem) {
		super(mensagem);
	}
	
	public AcheieException(String mensagem, Throwable origem) {
		super(mensagem, origem);
	}

}
