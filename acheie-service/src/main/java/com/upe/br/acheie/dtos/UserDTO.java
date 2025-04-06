package com.upe.br.acheie.dtos;

import com.upe.br.acheie.domain.models.User;
import com.upe.br.acheie.domain.enums.Major;
import com.upe.br.acheie.domain.enums.Semester;
import java.util.UUID;

public record UserDTO(UUID userId, String name, String lastname, Major major,
					  Semester semester, String phone, byte[] image) {

	public UserDTO(User user) {
		this(user.getId(), user.getName(), user.getLastname(), user.getMajor(),
				user.getSemester(), user.getPhone(), user.getImage());
	}
	
}
