
package com.upe.br.acheie.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.upe.br.acheie.domain.models.User;
import com.upe.br.acheie.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class AutorizacaoServicoTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private AutorizacaoServico autorizacaoServico;

  @Nested
  class loadUserByUsername {

    @Test
    @DisplayName("Deve retornar UserDetails quando o usuário é encontrado")
    void deveRetornarUserDetailsQuandoUsuarioEncontrado() {
      // Arrange
      String email = "emailparaencontrar@gmail.com";
      User user = new User();
      user.setEmail(email);

      // Mockando comportamento
      when(userRepository.findByEmail(email)).thenReturn(user);

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
      when(userRepository.findByEmail(email)).thenReturn(null);

      // Act e Assert
      assertThrows(UsernameNotFoundException.class, () -> {
        autorizacaoServico.loadUserByUsername(email);
      });
    }
  }
}