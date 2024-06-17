package com.upe.br.acheie.servico;

import com.upe.br.acheie.dominio.dto.request.CadastrarComentarioRequest;
import com.upe.br.acheie.dominio.dto.response.CadastrarComentarioResponse;
import com.upe.br.acheie.dominio.dto.response.ExcluirComentarioResponse;
import com.upe.br.acheie.repositorio.UsuarioRepositorio;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dominio.dto.ComentarioDto;
import com.upe.br.acheie.dominio.modelos.Comentario;
import com.upe.br.acheie.dominio.modelos.Post;
import com.upe.br.acheie.dominio.modelos.Usuario;
import com.upe.br.acheie.repositorio.ComentarioRepositorio;
import com.upe.br.acheie.repositorio.PostRepositorio;
import com.upe.br.acheie.dominio.utils.AcheieException;
import com.upe.br.acheie.dominio.utils.MensagensErro;
import com.upe.br.acheie.dominio.utils.enums.Cadastro;

@Service
@RequiredArgsConstructor
public class ComentarioServico {

  private final ComentarioRepositorio comentarioRepo;

  private final PostRepositorio postRepo;

  private final UsuarioRepositorio usuarioRepo;

  public CadastrarComentarioResponse cadastrarComentario(UUID postId, UUID usuarioId,
      CadastrarComentarioRequest request) {
    Post post = postRepo.findById(postId)
        .orElseThrow(() -> new AcheieException(MensagensErro.MSG_POST_NAO_ENCONTRADO));
    Usuario usuario = usuarioRepo.findById(usuarioId)
        .orElseThrow(() -> new AcheieException(MensagensErro.MSG_USUARIO_NAO_ENCONTRADO));

    Comentario comentario = new Comentario(request.assunto(), post, usuario);
    comentarioRepo.save(comentario);

    return new CadastrarComentarioResponse(comentario.getId(), comentario.getPost().getId(),
        comentario.getUsuario().getId(), comentario.getCriacaoDoComentario());
  }

  public ExcluirComentarioResponse excluirComentarioPorId(UUID idPost, UUID idUsuario,
      UUID idComentario) {
    Comentario comentarioParaRemover = this.comentarioRepo.findByUsuarioIdAndPostIdAndComentarioId(
        idUsuario, idPost, idComentario).orElseThrow(
        () -> new AcheieException(MensagensErro.MSG_COMENTARIO_DE_USUARIO_NAO_ENCONTRADO));

    this.comentarioRepo.deleteById(idComentario);
    comentarioParaRemover.setRemocaoDoComentario(LocalDate.now());

    return new ExcluirComentarioResponse(comentarioParaRemover.getCriacaoDoComentario(),
        comentarioParaRemover.getRemocaoDoComentario(),
        comentarioParaRemover.getUsuario().getId(),
        comentarioParaRemover.getPost().getId());
  }
}
