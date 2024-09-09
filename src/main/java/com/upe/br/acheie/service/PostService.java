package com.upe.br.acheie.service;

import com.upe.br.acheie.domain.dto.request.RegisterPostRequest;
import com.upe.br.acheie.domain.dto.response.RegisterPostResponse;
import com.upe.br.acheie.domain.enums.Status;
import com.upe.br.acheie.domain.enums.Type;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.domain.dto.PostDto;
import com.upe.br.acheie.domain.dto.request.CloseSearchRequest;
import com.upe.br.acheie.domain.dto.response.CloseSearchResponse;
import com.upe.br.acheie.domain.dto.response.RemovePostResponse;
import com.upe.br.acheie.domain.model.Item;
import com.upe.br.acheie.domain.model.Post;
import com.upe.br.acheie.domain.model.User;
import com.upe.br.acheie.domain.exceptions.AcheieException;
import com.upe.br.acheie.domain.exceptions.ErrorMessage;
import com.upe.br.acheie.domain.enums.Atualizacao;
import com.upe.br.acheie.domain.enums.Category;
import com.upe.br.acheie.repository.ItemRepository;
import com.upe.br.acheie.repository.PostRepositorio;
import com.upe.br.acheie.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepositorio postRepo;

  private final UserRepository usuarioRepo;

  private final ItemService itemService;

  private final UserService userService;

  private final ItemRepository itemRepo;

  private final HibernateSearchService hibernateSearchService;

  private static final List<String> CAMPOS_PARA_PESQUISA = Arrays.asList("item.titulo",
      "item.descricao");

  @Transactional
  public RegisterPostResponse cadastrarPost(UUID usuarioID, RegisterPostRequest request) {
    User user = usuarioRepo.findById(usuarioID)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_USUARIO_NAO_ENCONTRADO));

    if (request.type() == Type.DEVOLVIDO) {
      throw new AcheieException(ErrorMessage.MSG_ATUALIZAR_TIPO_POST_INVALIDO);
    }

    Post novoPost = new Post(request, user);
    postRepo.save(novoPost);

    UUID itemId = itemService.cadastrarItem(request.item(), novoPost.getId());
    Item item = itemRepo.findById(itemId)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_ITEM_NAO_ENCONTRADO));
    novoPost.setItem(item);

    hibernateSearchService.indexEntity(novoPost);

    return new RegisterPostResponse(novoPost.getId(), novoPost.getUser().getId(),
        novoPost.getCreatedAt());
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

  public Atualizacao atualizarPost(UUID postId, RegisterPostRequest request) {
    Post post = postRepo.findById(postId)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_POST_NAO_ENCONTRADO));

    if (request.type() == Type.DEVOLVIDO) {
      throw new AcheieException(ErrorMessage.MSG_ATUALIZAR_TIPO_POST_INVALIDO);
    }

    post.setType(request.type());
    this.itemService.atualizarItem(post.getItem().getId(), request.item());

    return Atualizacao.ATUALIZACAO_COM_SUCESSO;
  }

  //Filtro: estado, categoria, tipo e data

  public List<PostDto> filtrarPostsPorTipo(Type type) {
    return postRepo.findByTipo(type).stream().map(PostDto::new).toList();
  }


  public List<PostDto> filtrarPostsPorEstado(Status status) {
    return postRepo.findByItemEstado(status).stream().map(PostDto::new).toList();
  }

  public List<PostDto> filtrarPostsPorCategoria(Category category) {
    return postRepo.findByItemCategoria(category).stream().map(PostDto::new).toList();
  }

  public List<PostDto> filtrarPostsPorData(LocalDate inicio, LocalDate fim) {
    if (fim == null) {
      fim = LocalDate.now();
    }

    return postRepo.findByItemData(inicio, fim).stream().map(PostDto::new).toList();
  }

  public RemovePostResponse excluirPostPorId(UUID idPost, UUID idUsuario) {
    Post postParaRemover = postRepo.findByUsuarioIdAndPostId(idUsuario, idPost)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_POST_DE_USUARIO_NAO_ENCONTRADO));

    postRepo.deleteById(idPost);
    postParaRemover.setRemovedAt(LocalDate.now());

    return new RemovePostResponse(postParaRemover.getCreatedAt(),
        postParaRemover.getRemovedAt(), postParaRemover.getUser().getId());
  }

  public CloseSearchResponse encerrarProcuraDeItem(CloseSearchRequest request) {
    usuarioRepo.findById(request.idUsuario())
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_USUARIO_NAO_ENCONTRADO));
    Post postParaEncerrar = postRepo.findById(request.idPost())
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_POST_NAO_ENCONTRADO));
    Item itemParaEncerrar = itemRepo.findById(postParaEncerrar.getItem().getId())
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_ITEM_NAO_ENCONTRADO));

    if (!postParaEncerrar.getUser().getId().equals(request.idUsuario())) {
      throw new AcheieException(ErrorMessage.MSG_ENCERRAR_POST_INVALIDO);
    }

    postParaEncerrar.setType(Type.DEVOLVIDO);
    postParaEncerrar.setReturnedAt(LocalDate.now());
    this.postRepo.save(postParaEncerrar);

    itemParaEncerrar.setStatus(Status.DEVOLVIDO);
    this.itemRepo.save(itemParaEncerrar);

    return new CloseSearchResponse(postParaEncerrar.getUser().getId(),
        postParaEncerrar.getId(), postParaEncerrar.getReturnedAt());
  }
}
