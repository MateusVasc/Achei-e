package com.upe.br.acheie.dominio.utils.enums;

public enum Atualizacao {
	
	ATUALIZACAO_COM_SUCESSO("Atualização realizada com sucesso"), ATUALIZACAO_COM_FALHA("Não foi possível realizar a atualização");
	
	private String mensagem;
	
	Atualizacao(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String getMensagem() {
		return this.mensagem;
	}
	
	

}
