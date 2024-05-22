package com.upe.br.acheie.dominio.dto.request;

import com.upe.br.acheie.dominio.utils.enums.Curso;
import com.upe.br.acheie.dominio.utils.enums.Periodo;
import java.time.LocalDate;

public record CadastroRequest(String nome, String sobrenome, String email, String senha,
                              Curso curso, Periodo periodo, String telefone,
                              byte[] foto, LocalDate criacaoDaConta) {

}
