package com.upe.br.acheie.servico;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.upe.br.acheie.dominio.dto.UsuarioDto;
import com.upe.br.acheie.dominio.modelos.Usuario;
import com.upe.br.acheie.dominio.utils.enums.Curso;
import com.upe.br.acheie.dominio.utils.enums.Periodo;
import com.upe.br.acheie.repositorio.UsuarioRepositorio;

@ExtendWith(MockitoExtension.class)
public class UsuarioServicoTeste {

	
	@Mock
	UsuarioRepositorio usuarioRepo;
	
	@Spy
	@InjectMocks
	UsuarioServico usuarioServico;
	
	Usuario usuario;
	UsuarioDto usuarioDto;
	
//	@BeforeEach
//	void init() {
//		usuarioDto = new UsuarioDto("Marcos", "Silva", Curso.ENGENHARIA_DE_SOFTWARE, Periodo.OITAVO,
//				"8199540337", null);
//		usuario = new Usuario(usuarioDto);
//	}
	
	@Test
	@DisplayName("Deve retornar os dados de um usuário pelo seu ID")
	void buscarUsuarioPorIdCase1() {
		when(this.usuarioRepo.findById(this.usuario.getId())).thenReturn(Optional.of(this.usuario));
		
		UsuarioDto localUsuarioDto = this.usuarioServico.buscarUsuarioPorId(this.usuario.getId());
		
		Assertions.assertNotNull(localUsuarioDto);
		
	}
	
	@Test
	@DisplayName("Não deve retornar nenhum usuário. Optional#Empty")
	void buscarUsuarioPorIdCase2() {
		when(this.usuarioRepo.findById(this.usuario.getId())).thenReturn(Optional.empty());
		
		UsuarioDto localUsuarioDto = this.usuarioServico.buscarUsuarioPorId(this.usuario.getId());
		
		Assertions.assertNull(localUsuarioDto);
	}
	
//	@Test
//	@DisplayName("Deve lançar IllegalArgumentException pelo ID ser nulo")
//	void buscarUsuarioPorIdCase3() {
//		when(this.usuarioRepo.findById(this.usuario.getId())).thenThrow(IllegalArgumentException.class);
//		doNothing().when(this.usuarioServico).tratarErros(ArgumentMatchers.<IllegalArgumentException>any());
//
//		UsuarioDto localUsuario = this.usuarioServico.buscarUsuarioPorId(this.usuario.getId());
//		Assertions.assertNull(localUsuario);
//	}
}	
