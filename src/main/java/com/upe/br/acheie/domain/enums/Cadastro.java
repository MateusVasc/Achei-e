package com.upe.br.acheie.domain.enums;

public enum Cadastro {

	SUCESSO_CADASTRO(1), ENTIDADE_EXISTENTE(0), ERRO_CADASTRO(-1);
	
	private Integer estado;
	
	Cadastro(Integer estado) {
		this.estado = estado;
	}

	public Integer getEstado() {
		return this.estado;	
	}
}
