package com.upe.br.acheie.domain.enums;

public enum Update {
	
	UPDATED_SUCCESSFULLY("Atualização realizada com sucesso"),
	FAILED_TO_UPDATE("Não foi possível realizar a atualização");
	
	private String message;
	
	Update(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	

}
