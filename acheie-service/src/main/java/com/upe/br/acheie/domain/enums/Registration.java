package com.upe.br.acheie.domain.enums;

public enum Registration {

	REGISTERED_SUCCESSFULLY(1),
	ENTITY_ALREADY_EXISTS(0),
	FAILED_TO_REGISTER(-1);
	
	private Integer state;
	
	Registration(Integer state) {
		this.state = state;
	}

	public Integer getState() {
		return this.state;
	}
}
