package com.upe.br.acheie.servico;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.upe.br.acheie.dominio.dto.request.LoginRequest;
import com.upe.br.acheie.dominio.dto.response.LoginResponse;
import com.upe.br.acheie.dominio.modelos.Usuario;
import com.upe.br.acheie.repositorio.UsuarioRepositorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class AutorizacaoServicoTest {

  @Mock
  private UsuarioRepositorio usuarioRepositorio;

  @InjectMocks
  private AutorizacaoServico autorizacaoServico;

  @Nested
  class loadUserByUsername {

    @Test
    @DisplayName("Deve retornar UserDetails quando o usuário é encontrado")
    void deveRetornarUserDetailsQuandoUsuarioEncontrado() {
      // Arrange
      String email = "emailparaencontrar@gmail.com";
      Usuario usuario = new Usuario();
      usuario.setEmail(email);

      // Mockando comportamento
      when(usuarioRepositorio.findByEmail(email)).thenReturn(usuario);

      // Act
      UserDetails userDetails = autorizacaoServico.loadUserByUsername(email);

      // Assert
      assertNotNull(userDetails);
      assertEquals(email, userDetails.getUsername());
    }

    @Test
    @DisplayName("Deve lançar UsernameNotFoundException quando o usuário não é encontrado")
    void deveLancarUsernameNotFoundExceptionQuandoUsuarioNaoEncontrado() {
      // Arrange
      String email = "emailnaoencontrado@gmail.com";

      // Mockando comportamento
      when(usuarioRepositorio.findByEmail(email)).thenReturn(null);

      // Act e Assert
      assertThrows(UsernameNotFoundException.class, () -> {
        autorizacaoServico.loadUserByUsername(email);
      });
    }
  }
}