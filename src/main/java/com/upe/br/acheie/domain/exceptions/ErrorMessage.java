package com.upe.br.acheie.domain.exceptions;

public class ErrorMessage {

	public static final String MSG_ID_NULO = "O ID informado é nulo";
	public static final String MSG_ERRO_INESPERADO =
			"Ocorreu um error no processamento de seus dados. Tente novamente!";
	public static final String MSG_ELEMENTO_AUSENTE = "O elemento informado é nulo";
	public static final String MSG_ERRO_AUTENTICACAO =
			"Houve uma falha na autenticação do usuário. Tente novamente!";
	public static final String MSG_USUARIO_EXISTENTE =
			"Já existe um usuário cadastrado com o e-mail informado";
	public static final String MSG_NAO_ENCONTRADO = "O elemento solicitado não foi encontrado";
	public static final String MSG_ERRO_OPTIONAL = "Não há nenhum valor dentro do Optional<?>";
	public static final String MSG_ERRO_ALGORITMO = "O algoritmo informado é inválido";
	public static final String MSG_ERRO_JWT = "Erro no processamento do JWT";
	public static final String MSG_ERRO_ASSINATURA = "A assinatura informada é inválida";
	public static final String MSG_TOKEN_EXPIRADO = "O token informado já expirou";
	public static final String MSG_CLAIM_AUSENTE = "Claim a ser verificado não foi informado";
	public static final String MSG_ERRO_CLAIM = "O claim informado é inválido";
	public static final String MSG_ENUM_FILTRO = "O valor de filtro informado é inválido";
	public static final String MSG_ERRO_NOVA_SENHA = "Houve um error na solicitação de mudança de password. Tente novamente!";
	public static final String MSG_USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
	public static final String MSG_ITEM_NAO_ENCONTRADO = "Item não encontrado";
	public static final String MSG_POST_NAO_ENCONTRADO = "Post não encontrado";
	public static final String MSG_POST_DE_USUARIO_NAO_ENCONTRADO = "Este usuário não possui um post para este id";
	public static final String MSG_COMENTARIO_DE_USUARIO_NAO_ENCONTRADO = "Este usuário não possui um comentário para este post";
	public static final String MSG_ENCERRAR_POST_INVALIDO = "Um usuário não pode encerrar um post que não seja dele";
	public static final String MSG_INFO_CADASTRO_INVALIDAS = "Informações de cadastro inválidas";
	public static final String MSG_EMAIL_CADASTRADO = "Este email já está cadastrado";
	public static final String MSG_EMAIL_INVALIDO = "Informe um email válido";
	public static final String MSG_ATUALIZAR_TIPO_POST_INVALIDO = "Não é permitido atualizar o tipo do post para DEVOLVIDO neste endpoint";
	public static final String MSG_ATUALIZAR_ESTADO_ITEM_INVALIDO = "Não é permitido atualizar o estado do item para DEVOLVIDO neste endpoint";

	private ErrorMessage() {}
	}
