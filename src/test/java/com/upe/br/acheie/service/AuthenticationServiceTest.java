package com.upe.br.acheie.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.upe.br.acheie.config.TokenService;
import com.upe.br.acheie.domain.dto.request.RegisterRequest;
import com.upe.br.acheie.domain.dto.request.LoginRequest;
import com.upe.br.acheie.domain.dto.response.RegisterResponse;
import com.upe.br.acheie.domain.dto.response.RemoveAccountResponse;
import com.upe.br.acheie.domain.dto.response.LoginResponse;
import com.upe.br.acheie.domain.model.Usuario;
import com.upe.br.acheie.domain.exceptions.AcheieException;
import com.upe.br.acheie.domain.enums.Curso;
import com.upe.br.acheie.domain.enums.Periodo;
import com.upe.br.acheie.repository.UsuarioRepositorio;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class AutenticacaoServicoTest {

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private TokenService tokenService;

  @Mock
  private UsuarioRepositorio usuarioRepositorio;

  @InjectMocks
  private AutenticacaoServico autenticacaoServico;

  @Nested
  class loginUsuario {

    @Test
    @DisplayName("Deve fazer login com sucesso e retornar o token")
    void deveFazerLoginComSucesso() {
      // Arrange
      String email = "login@gmail.com";
      String senha = "login123";
      String token = "Token Gerado";

      LoginRequest requisicao = new LoginRequest(email, senha);
      Usuario usuario = new Usuario();
      usuario.setEmail(email);
      usuario.setSenha(senha);

      // Mockando comportamento
      Authentication authentication = mock(Authentication.class);
      when(authentication.getPrincipal()).thenReturn(usuario);
      when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
          .thenReturn(authentication);
      when(tokenService.gerarToken(usuario)).thenReturn(token);

      // Act
      LoginResponse resposta = autenticacaoServico.loginUsuario(requisicao);

      // Assert
      assertNotNull(resposta);
      assertEquals(token, resposta.token());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a autenticação falha")
    void deveLancarExcecaoQuandoAutenticacaoFalha() {
      // Arrange
      LoginRequest requisicao = new LoginRequest("login@gmail.com", "login123");

      // Mockando comportamento
      when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
          .thenThrow(new AcheieException("Autenticação falhou"));

      // Act e Assert
      AcheieException exception = assertThrows(AcheieException.class, () -> {
        autenticacaoServico.loginUsuario(requisicao);
      });

      assertEquals("Autenticação falhou", exception.getMessage());
    }
  }

  @Nested
  class cadastrarUsuario {

    @Test
    @DisplayName("Deve criar um usuário com sucesso")
    void deveCriarUsuarioComSucesso() {

      // Arrange
      RegisterRequest requisicao = new RegisterRequest(
          "Mateus",
          "Teste",
          "testecadastro@gmail.com",
          "teste",
          Curso.ENGENHARIA_DE_SOFTWARE,
          Periodo.QUINTO,
          "123456789",
          null);

      // Mockando comportamento do usuarioRepositorio
      when(usuarioRepositorio.findByEmail(requisicao.email())).thenReturn(null);
      when(usuarioRepositorio.save(any(Usuario.class))).thenAnswer(
          invocation -> invocation.getArgument(0));

      // Act
      RegisterResponse resposta = autenticacaoServico.cadastrarUsuario(requisicao);

      // Assert
      assertNotNull(resposta);
      assertEquals(requisicao.email(), resposta.email());
      assertNotNull(resposta.criacaoDaConta());
    }

    @Test
    @DisplayName("Deve retornar uma exceção informando dados inválidos")
    void deveRetornarExcecaoInfoInvalida() {

      // Arrange
      RegisterRequest requisicaoInvalida = new RegisterRequest(
          null,
          null,
          "emailinvalido",
          "INV12345",
          null,
          null,
          "123456789",
          null);

      // Act
      AcheieException exception = assertThrows(AcheieException.class, () -> {
        autenticacaoServico.cadastrarUsuario(requisicaoInvalida);
      });

      // Assert
      assertEquals("Informe informações válidas", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar uma exceção informando email já cadastrado")
    void deveRetornarExcecaoEmailInvalido() {

      // Arrange
      RegisterRequest requisicaoEmailCadastrado = new RegisterRequest(
          "Mateus",
          "Teste",
          "testecadastrodenovo@gmail.com",
          "teste",
          Curso.ENGENHARIA_DE_SOFTWARE,
          Periodo.QUINTO,
          "123456789",
          null);

      // Mockando comportamento do usuarioRepositorio
      when(usuarioRepositorio.findByEmail(requisicaoEmailCadastrado.email())).thenReturn(
          new Usuario());

      // Act
      AcheieException exception = assertThrows(AcheieException.class, () -> {
        autenticacaoServico.cadastrarUsuario(requisicaoEmailCadastrado);
      });

      // Assert
      assertEquals("Este e-mail já está cadastrado", exception.getMessage());
    }
  }

  @Nested
  class excluirUsuarioPorEmail {

    @Test
    @DisplayName("Deve excluir um usuário com sucesso")
    void deveEcluirUsuario() {
      // Arrange
      String email = "emailcadastrado@gmail.com";
      Usuario usuarioParaExcluir = new Usuario();
      usuarioParaExcluir.setEmail(email);
      usuarioParaExcluir.setCriacaoDaConta(LocalDate.now().minusDays(1));

      // Mockando comportamento do usuarioRepositorio
      when(usuarioRepositorio.findByEmail(email)).thenReturn(usuarioParaExcluir);

      // Act
      RemoveAccountResponse resposta = autenticacaoServico.excluirUsuarioPorEmail(email);

      // Assert
      assertNotNull(resposta);
      assertEquals(email, resposta.email());
      assertNotNull(resposta.dataDeCriacao());
      assertNotNull(resposta.dataDeRemocao());
      verify(usuarioRepositorio, times(1)).deleteById(usuarioParaExcluir.getId());
    }

    @Test
    @DisplayName("Deve retornar uma exceção informando email inválido")
    void deveRetornarExcecaoEmailInvalido() {
      // Arrange
      String emailInvalido = "";

      // Act e Assert
      AcheieException exception = assertThrows(AcheieException.class, () -> {
        autenticacaoServico.excluirUsuarioPorEmail(emailInvalido);
      });

      assertEquals("Informe um email válido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar uma exceção informando email não cadastrado")
    void deveRetornarExcecaoEmailNaoCadastrado() {
      // Arrange
      String emailNaoCadastrado = "emailnaocadastrado@gmail.com";

      // Mockando comportamento do usuarioRepositorio
      when(usuarioRepositorio.findByEmail(emailNaoCadastrado)).thenReturn(null);

      // Act e Assert
      AcheieException exception = assertThrows(AcheieException.class, () -> {
        autenticacaoServico.excluirUsuarioPorEmail(emailNaoCadastrado);
      });

      assertEquals("Informe um email cadastrado", exception.getMessage());
    }
  }
}