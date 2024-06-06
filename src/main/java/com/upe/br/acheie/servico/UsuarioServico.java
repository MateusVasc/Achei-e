package com.upe.br.acheie.servico;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dominio.dto.UsuarioDto;
import com.upe.br.acheie.dominio.dto.request.LoginRequest;
import com.upe.br.acheie.dominio.modelos.Usuario;
import com.upe.br.acheie.dominio.utils.AcheieException;
import com.upe.br.acheie.dominio.utils.MensagensErro;
import com.upe.br.acheie.dominio.utils.enums.Atualizacao;
import com.upe.br.acheie.repositorio.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServico {
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	private static final Logger log = LogManager.getLogger(UsuarioServico.class);
	
	public UsuarioDto buscarUsuarioPorId(UUID idUsuario) { 
		try {
			Optional<Usuario> usuario = usuarioRepo.findById(idUsuario);
			if (usuario.isEmpty()) {
				return null;
			}
			return new UsuarioDto(usuario.get());
		} catch (Exception e) {
			this.tratarErros(e);
		}
		return null;
	}
	
	public Atualizacao atualizarUsuario(UUID idUsuario, UsuarioDto usuarioDto) { 
		try {
			Usuario usuario = usuarioRepo.getReferenceById(idUsuario);
			usuario.setNome(usuarioDto.nome());
			usuario.setSobrenome(usuarioDto.sobrenome());
			usuario.setTelefone(usuarioDto.telefone());
			usuario.setCurso(usuarioDto.curso());
			usuario.setPeriodo(usuarioDto.periodo());
			usuario.setFoto(usuarioDto.foto());
			
			this.usuarioRepo.save(usuario);
			
			return Atualizacao.ATUALIZACAO_COM_SUCESSO;
		} catch (Exception e) {
			this.tratarErros(e);
		}
		return Atualizacao.ATUALIZACAO_COM_FALHA;
	}
	
	public String requisitarMudarSenha(String email) {
		try {
			email = email.replace(" ", "");
			Usuario usuario = usuarioRepo.getByEmail(email);
			if (usuario == null) {
				throw new AcheieException(MensagensErro.MSG_ERRO_NOVA_SENHA);
			}
			return "O link para redefinição de senha foi enviado para o seu e-mail.";
		} catch (Exception e) {
			this.tratarErros(e);
		}
		return MensagensErro.MSG_ERRO_NOVA_SENHA;
	}
	
	public Atualizacao atualizarSenha(LoginRequest login) {
		try {
			Usuario usuario = usuarioRepo.getByEmail(login.email());
			if (usuario == null) {
				throw new AcheieException(MensagensErro.MSG_ERRO_NOVA_SENHA);
			}
			usuario.setSenha(new BCryptPasswordEncoder().encode(login.senha()));
			this.usuarioRepo.save(usuario);
			return Atualizacao.ATUALIZACAO_COM_SUCESSO;
		} catch (Exception e) {
			this.tratarErros(e);
		}
		return Atualizacao.ATUALIZACAO_COM_FALHA;
	}
	
	public void tratarErros(Exception e) {
		if (e instanceof IllegalArgumentException) {
			log.error(MensagensErro.MSG_ID_NULO, e);
			throw new AcheieException(MensagensErro.MSG_ID_NULO, e);
		} else if (e instanceof NoSuchElementException) {
			log.error(MensagensErro.MSG_ERRO_OPTIONAL, e);
			throw new AcheieException(MensagensErro.MSG_ERRO_OPTIONAL, e);
		} else {
			log.error(e.getMessage(), e);
			throw new AcheieException(e.getMessage());
		}
	}
	
}
