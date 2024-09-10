package com.upe.br.acheie.domain.dto;

import com.upe.br.acheie.domain.entities.User;
import com.upe.br.acheie.domain.enums.Course;
import com.upe.br.acheie.domain.enums.Semester;
import java.util.UUID;

public record UserDto(UUID userId, String name, String lastname, Course course,
											Semester semester, String phone, byte[] photo) {

	public UserDto(User user) {
		this(user.getId(), user.getName(), user.getLastname(), user.getCourse(),
				user.getSemester(), user.getTelephone(), user.getPhoto());
	}
	
}
