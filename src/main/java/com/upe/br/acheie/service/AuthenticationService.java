package com.upe.br.acheie.service;

import com.upe.br.acheie.config.TokenService;
import com.upe.br.acheie.domain.dto.request.RegisterRequest;
import com.upe.br.acheie.domain.dto.response.RegisterResponse;
import com.upe.br.acheie.domain.dto.request.LoginRequest;
import com.upe.br.acheie.domain.dto.response.RemoveAccountResponse;
import com.upe.br.acheie.domain.dto.response.LoginResponse;
import com.upe.br.acheie.domain.model.User;
import com.upe.br.acheie.domain.exceptions.ErrorMessage;
import com.upe.br.acheie.repository.UserRepository;
import com.upe.br.acheie.domain.exceptions.AcheieException;
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
public class AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;
  private final UserRepository userRepository;

  public LoginResponse loginUsuario(LoginRequest request) throws AcheieException {
    var usuarioData = new UsernamePasswordAuthenticationToken(request.email(), request.senha());
    var auth = this.authenticationManager.authenticate(usuarioData);

    var token = tokenService.gerarToken((User) auth.getPrincipal());
    User user = (User) this.userRepository.findByEmail(request.email());
    return new LoginResponse(user.getId(), token);
  }

  public RegisterResponse cadastrarUsuario(RegisterRequest request) throws AcheieException {
    if (!validarInfo(request)) {
      throw new AcheieException(ErrorMessage.MSG_INFO_CADASTRO_INVALIDAS);
    }

    if (this.userRepository.findByEmail(request.email()) != null) {
      throw new AcheieException(ErrorMessage.MSG_EMAIL_CADASTRADO);
    }

    User userNovo = new User(request);
    userNovo.setPassword(new BCryptPasswordEncoder().encode(request.senha()));
    userNovo.setCreatedAt(LocalDate.now());
    this.userRepository.save(userNovo);

    return new RegisterResponse(userNovo.getEmail(), userNovo.getCreatedAt());
  }

  public RemoveAccountResponse excluirUsuarioPorEmail(String email) throws AcheieException {
    if (!validarEmail(email)) {
      throw new AcheieException(ErrorMessage.MSG_EMAIL_INVALIDO);
    }

    if (this.userRepository.findByEmail(email) == null) {
      throw new AcheieException(ErrorMessage.MSG_EMAIL_CADASTRADO);
    }

    User userParaRemocao = (User) this.userRepository.findByEmail(email);
    this.userRepository.deleteById(userParaRemocao.getId());
    userParaRemocao.setRemovedAt(LocalDate.now());

    return new RemoveAccountResponse(userParaRemocao.getEmail(),
        userParaRemocao.getCreatedAt(), userParaRemocao.getRemovedAt());
  }

  private boolean validarInfo(RegisterRequest request) {
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
