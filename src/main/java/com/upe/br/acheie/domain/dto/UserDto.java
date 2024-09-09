package com.upe.br.acheie.domain.dto;

import com.upe.br.acheie.domain.model.User;
import com.upe.br.acheie.domain.enums.Course;
import com.upe.br.acheie.domain.enums.Semester;
import java.util.UUID;

public record UserDto(UUID idUsuario, String nome, String sobrenome, Course course,
											Semester semester, String telefone, byte[] foto) {

	public UserDto(User user) {
		this(user.getId(), user.getName(), user.getLastname(), user.getCourse(),
				user.getSemester(), user.getTelephone(), user.getPhoto());
	}
	
}
