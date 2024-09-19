package com.upe.br.acheie.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

	ELETRONICO("Eletronico"),
	VESTUARIO("Vestuário"),
	CHAVES("Chaves"),
	DOCUMENTOS("Documentos"),
	CARTEIRA("Carteira"),
	OCULOS("Óculos"),
	BOLSA_MOCHILA("Bolsa/Mochila"),
	ACESSORIOS("Acessórios"),
	LIVROS("Livros"),
	BRINQUEDOS("Brinquedos"),
	INSTRUMENTOS_MUSICAIS("Instrumentos Musicais"),
	INSTRUMENTOS_DE_ESCRITA("Instrumentos de Escrita"),
	ITENS_DE_ESPORTE("Itens de Esporte"),
	FERRAMENTAS("Ferramentas"),
	EQUIPAMENTOS_FOTOGRAFICOS("Equipamentos Fotográficos"),
	APARELHOS_DE_COZINHA("Aparelhos de Cozinha"),
	ARTIGOS_DE_PAPELARIA("Artigos de Papelaria"),
	PRODUTOS_DE_BELEZA("Produtos de Beleza"),
	OUTROS("Outros");

  private final String value;
  
  public static boolean eCategoria(String palavra) {
	  Category[] categories = Category.values();
	  for (Category category : categories) {
		  if (category.value.equals(palavra)) {
			  return true;
		  }
	  }
	  return false;
  }
  
}
