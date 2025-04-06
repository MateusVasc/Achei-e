package com.upe.br.acheie.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.upe.br.acheie.dtos.UserDTO;
import com.upe.br.acheie.domain.models.User;
import com.upe.br.acheie.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServicoTeste {

	
	@Mock
	UserRepository usuarioRepo;
	
	@Spy
	@InjectMocks
	UserService userService;
	
	User user;
	UserDTO userDTO;
	
//	@BeforeEach
//	void init() {
//		usuarioDto = new UsuarioDto("Marcos", "Silva", Curso.ENGENHARIA_DE_SOFTWARE, Periodo.OITAVO,
//				"8199540337", null);
//		user = new Usuario(usuarioDto);
//	}
	
	@Test
	@DisplayName("Deve retornar os dados de um usuário pelo seu ID")
	void buscarUsuarioPorIdCase1() {
		when(this.usuarioRepo.findById(this.user.getId())).thenReturn(Optional.of(this.user));
		
		UserDTO localUserDTO = this.userService.searchUserById(this.user.getId());
		
		Assertions.assertNotNull(localUserDTO);
		
	}
	
	@Test
	@DisplayName("Não deve retornar nenhum usuário. Optional#Empty")
	void buscarUsuarioPorIdCase2() {
		when(this.usuarioRepo.findById(this.user.getId())).thenReturn(Optional.empty());
		
		UserDTO localUserDTO = this.userService.searchUserById(this.user.getId());
		
		Assertions.assertNull(localUserDTO);
	}
	
//	@Test
//	@DisplayName("Deve lançar IllegalArgumentException pelo ID ser nulo")
//	void buscarUsuarioPorIdCase3() {
//		when(this.usuarioRepo.findById(this.user.getId())).thenThrow(IllegalArgumentException.class);
//		doNothing().when(this.usuarioServico).tratarErros(ArgumentMatchers.<IllegalArgumentException>any());
//
//		UsuarioDto localUsuario = this.usuarioServico.buscarUsuarioPorId(this.user.getId());
//		Assertions.assertNull(localUsuario);
//	}
}	
