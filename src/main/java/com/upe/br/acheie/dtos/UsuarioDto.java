package com.upe.br.acheie.dtos;

import com.upe.br.acheie.domain.models.User;
import com.upe.br.acheie.domain.enums.Major;
import com.upe.br.acheie.domain.enums.Semester;
import java.util.UUID;

public record UsuarioDto(UUID idUsuario, String nome, String sobrenome, Major major,
						 Semester semester, String telefone, byte[] foto) {

	public UsuarioDto(User user) {
		this(user.getId(), user.getName(), user.getLastname(), user.getMajor(),
				user.getSemester(), user.getPhone(), user.getImage());
	}
	
}
