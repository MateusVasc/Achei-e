package com.upe.br.acheie.servico;

import com.upe.br.acheie.config.TokenServico;
import com.upe.br.acheie.dominio.dto.request.CadastroRequest;
import com.upe.br.acheie.dominio.dto.response.CadastroResponse;
import com.upe.br.acheie.dominio.dto.request.LoginRequest;
import com.upe.br.acheie.dominio.dto.response.ExcluirContaResponse;
import com.upe.br.acheie.dominio.dto.response.LoginResponse;
import com.upe.br.acheie.dominio.modelos.Usuario;
import com.upe.br.acheie.dominio.utils.MensagensErro;
import com.upe.br.acheie.repositorio.UsuarioRepositorio;
import com.upe.br.acheie.dominio.utils.AcheieException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoServico {

  private final AuthenticationManager authenticationManager;
  private final TokenServico tokenServico;
  private final UsuarioRepositorio usuarioRepositorio;

  public LoginResponse loginUsuario(LoginRequest request) throws AcheieException {
    var usuarioData = new UsernamePasswordAuthenticationToken(request.email(), request.senha());
    var auth = this.authenticationManager.authenticate(usuarioData);

    var token = tokenServico.gerarToken((Usuario) auth.getPrincipal());
    Usuario usuario = (Usuario) this.usuarioRepositorio.findByEmail(request.email());
    return new LoginResponse(usuario.getId(), token);
  }

  public CadastroResponse cadastrarUsuario(CadastroRequest request) throws AcheieException {
    if (!validarInfo(request)) {
      throw new AcheieException(MensagensErro.MSG_INFO_CADASTRO_INVALIDAS);
    }

    if (this.usuarioRepositorio.findByEmail(request.email()) != null) {
      throw new AcheieException(MensagensErro.MSG_EMAIL_CADASTRADO);
    }

    Usuario usuarioNovo = new Usuario(request);
    usuarioNovo.setSenha(new BCryptPasswordEncoder().encode(request.senha()));
    usuarioNovo.setCriacaoDaConta(LocalDate.now());
    this.usuarioRepositorio.save(usuarioNovo);

    return new CadastroResponse(usuarioNovo.getEmail(), usuarioNovo.getCriacaoDaConta());
  }

  public ExcluirContaResponse excluirUsuarioPorEmail(String email) throws AcheieException {
    if (!validarEmail(email)) {
      throw new AcheieException(MensagensErro.MSG_EMAIL_INVALIDO);
    }

    if (this.usuarioRepositorio.findByEmail(email) == null) {
      throw new AcheieException(MensagensErro.MSG_EMAIL_CADASTRADO);
    }

    Usuario usuarioParaRemocao = (Usuario) this.usuarioRepositorio.findByEmail(email);
    this.usuarioRepositorio.deleteById(usuarioParaRemocao.getId());
    usuarioParaRemocao.setRemocaoDaConta(LocalDate.now());

    return new ExcluirContaResponse(usuarioParaRemocao.getEmail(),
        usuarioParaRemocao.getCriacaoDaConta(), usuarioParaRemocao.getRemocaoDaConta());
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
    return email != null && !email.trim().isEmpty() && email.length() > 4;
  }

  private boolean validarSenha(String senha) {
    return senha != null && !senha.trim().isEmpty() && senha.length() > 4;
  }

  private boolean validarTelefone(String telefone) {
    return telefone != null && !telefone.trim().isEmpty() && telefone.length() > 4;
  }
}
