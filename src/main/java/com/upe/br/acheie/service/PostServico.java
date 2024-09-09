package com.upe.br.acheie.service;

import com.upe.br.acheie.domain.dto.request.CadastrarPostRequest;
import com.upe.br.acheie.domain.dto.response.CadastroPostResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.domain.dto.PostDto;
import com.upe.br.acheie.domain.dto.request.EncerrarProcuraRequest;
import com.upe.br.acheie.domain.dto.response.EncerrarProcuraResponse;
import com.upe.br.acheie.domain.dto.response.ExcluirPostResponse;
import com.upe.br.acheie.domain.model.Item;
import com.upe.br.acheie.domain.model.Post;
import com.upe.br.acheie.domain.model.Usuario;
import com.upe.br.acheie.domain.exceptions.AcheieException;
import com.upe.br.acheie.domain.exceptions.ErrorMessage;
import com.upe.br.acheie.domain.enums.Atualizacao;
import com.upe.br.acheie.domain.enums.Categoria;
import com.upe.br.acheie.domain.enums.Estado;
import com.upe.br.acheie.domain.enums.Tipo;
import com.upe.br.acheie.repository.ItemRepositorio;
import com.upe.br.acheie.repository.PostRepositorio;
import com.upe.br.acheie.repository.UsuarioRepositorio;

@Service
@RequiredArgsConstructor
public class PostServico {

  private final PostRepositorio postRepo;

  private final UsuarioRepositorio usuarioRepo;

  private final ItemServico itemServico;

  private final UsuarioServico usuarioServico;

  private final ItemRepositorio itemRepo;

  private final HibernateSearchServico hibernateSearchServico;

  private static final List<String> CAMPOS_PARA_PESQUISA = Arrays.asList("item.titulo",
      "item.descricao");

  @Transactional
  public CadastroPostResponse cadastrarPost(UUID usuarioID, CadastrarPostRequest request) {
    Usuario usuario = usuarioRepo.findById(usuarioID)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_USUARIO_NAO_ENCONTRADO));

    if (request.tipo() == Tipo.DEVOLVIDO) {
      throw new AcheieException(ErrorMessage.MSG_ATUALIZAR_TIPO_POST_INVALIDO);
    }

    Post novoPost = new Post(request, usuario);
    postRepo.save(novoPost);

    UUID itemId = itemServico.cadastrarItem(request.item(), novoPost.getId());
    Item item = itemRepo.findById(itemId)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_ITEM_NAO_ENCONTRADO));
    novoPost.setItem(item);

    hibernateSearchServico.indexEntity(novoPost);

    return new CadastroPostResponse(novoPost.getId(), novoPost.getUsuario().getId(),
        novoPost.getCriacaoDoPost());
  }

  public PostDto buscarPostEspecifico(UUID idPost) {
    Post post = postRepo.findById(idPost)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_POST_NAO_ENCONTRADO));
    return new PostDto(post);
  }

  public List<PostDto> buscarPosts() {
    return postRepo.findAll().stream().map(PostDto::new).toList();
  }

  public List<PostDto> buscarPostsPorIdUsuario(UUID idUsuario) {
    return postRepo.findByUsuarioId(idUsuario).stream().map(PostDto::new).toList();
  }

  public List<PostDto> buscarPostsPorTexto(String texto, List<String> campos, int limite) {
    List<String> camposDePesquisa = campos.isEmpty() ? CAMPOS_PARA_PESQUISA : campos;

    boolean temCamposInvalidos = camposDePesquisa.stream()
        .anyMatch(f -> !CAMPOS_PARA_PESQUISA.contains(f));

    if (temCamposInvalidos) {
      throw new IllegalArgumentException();
    }

    return postRepo.searchBy(texto, limite, camposDePesquisa.toArray(new String[0])).stream()
        .map(PostDto::new).toList();
  }

  public Atualizacao atualizarPost(UUID postId, CadastrarPostRequest request) {
    Post post = postRepo.findById(postId)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_POST_NAO_ENCONTRADO));

    if (request.tipo() == Tipo.DEVOLVIDO) {
      throw new AcheieException(ErrorMessage.MSG_ATUALIZAR_TIPO_POST_INVALIDO);
    }

    post.setTipo(request.tipo());
    this.itemServico.atualizarItem(post.getItem().getId(), request.item());

    return Atualizacao.ATUALIZACAO_COM_SUCESSO;
  }

  //Filtro: estado, categoria, tipo e data

  public List<PostDto> filtrarPostsPorTipo(Tipo tipo) {
    return postRepo.findByTipo(tipo).stream().map(PostDto::new).toList();
  }


  public List<PostDto> filtrarPostsPorEstado(Estado estado) {
    return postRepo.findByItemEstado(estado).stream().map(PostDto::new).toList();
  }

  public List<PostDto> filtrarPostsPorCategoria(Categoria categoria) {
    return postRepo.findByItemCategoria(categoria).stream().map(PostDto::new).toList();
  }

  public List<PostDto> filtrarPostsPorData(LocalDate inicio, LocalDate fim) {
    if (fim == null) {
      fim = LocalDate.now();
    }

    return postRepo.findByItemData(inicio, fim).stream().map(PostDto::new).toList();
  }

  public ExcluirPostResponse excluirPostPorId(UUID idPost, UUID idUsuario) {
    Post postParaRemover = postRepo.findByUsuarioIdAndPostId(idUsuario, idPost)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_POST_DE_USUARIO_NAO_ENCONTRADO));

    postRepo.deleteById(idPost);
    postParaRemover.setRemocaoDoPost(LocalDate.now());

    return new ExcluirPostResponse(postParaRemover.getCriacaoDoPost(),
        postParaRemover.getRemocaoDoPost(), postParaRemover.getUsuario().getId());
  }

  public EncerrarProcuraResponse encerrarProcuraDeItem(EncerrarProcuraRequest request) {
    usuarioRepo.findById(request.idUsuario())
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_USUARIO_NAO_ENCONTRADO));
    Post postParaEncerrar = postRepo.findById(request.idPost())
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_POST_NAO_ENCONTRADO));
    Item itemParaEncerrar = itemRepo.findById(postParaEncerrar.getItem().getId())
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_ITEM_NAO_ENCONTRADO));

    if (!postParaEncerrar.getUsuario().getId().equals(request.idUsuario())) {
      throw new AcheieException(ErrorMessage.MSG_ENCERRAR_POST_INVALIDO);
    }

    postParaEncerrar.setTipo(Tipo.DEVOLVIDO);
    postParaEncerrar.setDevolucaoItem(LocalDate.now());
    this.postRepo.save(postParaEncerrar);

    itemParaEncerrar.setEstado(Estado.DEVOLVIDO);
    this.itemRepo.save(itemParaEncerrar);

    return new EncerrarProcuraResponse(postParaEncerrar.getUsuario().getId(),
        postParaEncerrar.getId(), postParaEncerrar.getDevolucaoItem());
  }
}
