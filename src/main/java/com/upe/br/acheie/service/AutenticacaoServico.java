package com.upe.br.acheie.service;

import com.upe.br.acheie.config.security.TokenService;
import com.upe.br.acheie.domain.models.User;
import com.upe.br.acheie.dtos.request.CadastroRequest;
import com.upe.br.acheie.dtos.response.CadastroResponse;
import com.upe.br.acheie.dtos.request.LoginRequest;
import com.upe.br.acheie.dtos.response.ExcluirContaResponse;
import com.upe.br.acheie.dtos.response.LoginResponse;
import com.upe.br.acheie.utils.MensagensErro;
import com.upe.br.acheie.repository.UsuarioRepositorio;
import com.upe.br.acheie.utils.AcheieException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoServico {

  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;
  private final UsuarioRepositorio usuarioRepositorio;

  public LoginResponse loginUsuario(LoginRequest request) throws AcheieException {
    var usuarioData = new UsernamePasswordAuthenticationToken(request.email(), request.senha());
    var auth = this.authenticationManager.authenticate(usuarioData);

    var token = tokenService.generateToken((User) auth.getPrincipal());
    User user = (User) this.usuarioRepositorio.findByEmail(request.email());
    return new LoginResponse(user.getId(), token);
  }

  public CadastroResponse cadastrarUsuario(CadastroRequest request) throws AcheieException {
    if (!validarInfo(request)) {
      throw new AcheieException(MensagensErro.MSG_INFO_CADASTRO_INVALIDAS);
    }

    if (this.usuarioRepositorio.findByEmail(request.email()) != null) {
      throw new AcheieException(MensagensErro.MSG_EMAIL_CADASTRADO);
    }

    User userNovo = new User(request);
    userNovo.setPassword(new BCryptPasswordEncoder().encode(request.senha()));
    userNovo.setCreatedAt(LocalDate.now());
    this.usuarioRepositorio.save(userNovo);

    return new CadastroResponse(userNovo.getEmail(), userNovo.getCreatedAt());
  }

  public ExcluirContaResponse excluirUsuarioPorEmail(String email) throws AcheieException {
    if (!validarEmail(email)) {
      throw new AcheieException(MensagensErro.MSG_EMAIL_INVALIDO);
    }

    if (this.usuarioRepositorio.findByEmail(email) == null) {
      throw new AcheieException(MensagensErro.MSG_EMAIL_CADASTRADO);
    }

    User userParaRemocao = (User) this.usuarioRepositorio.findByEmail(email);
    this.usuarioRepositorio.deleteById(userParaRemocao.getId());
    userParaRemocao.setDeletedAt(LocalDate.now());

    return new ExcluirContaResponse(userParaRemocao.getEmail(),
        userParaRemocao.getCreatedAt(), userParaRemocao.getDeletedAt());
  }

  private boolean validarInfo(CadastroRequest request) {
    return validarNome(request.nome()) &&
        validarSobrenome(request.sobrenome()) &&
        validarEmail(request.email()) &&
        validarSenha(request.senha()) &&
        validarTelefone(request.telefone());
  }

  private boolean validarNome(String nome) {
    return nome != null && !nome.trim().isEmpty() && nome.length() > 4;
  }

  private boolean validarSobrenome(String sobrenome) {
    return sobrenome != null && !sobrenome.trim().isEmpty() && sobrenome.length() > 4;
  }

  private boolean validarEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
      return false;
    }

    String emailRegex = "^[^@]{5,}@upe\\.br$";
    Pattern pattern = Pattern.compile(emailRegex);
    Matcher matcher = pattern.matcher(email);

    return matcher.matches();
  }

  private boolean validarSenha(String senha) {
    if (senha == null || senha.trim().isEmpty()) {
      return false;
    }

    String senhaRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
    Pattern pattern = Pattern.compile(senhaRegex);
    Matcher matcher = pattern.matcher(senha);

    return matcher.matches();
  }

  private boolean validarTelefone(String telefone) {
    if (telefone == null || telefone.trim().isEmpty()) {
      return false;
    }

    String telefoneRegex = "^\\d{11}$";
    Pattern pattern = Pattern.compile(telefoneRegex);
    Matcher matcher = pattern.matcher(telefone);

    return matcher.matches();
  }
}
