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
import com.upe.br.acheie.domain.entities.Item;
import com.upe.br.acheie.domain.entities.Post;
import com.upe.br.acheie.domain.entities.User;
import com.upe.br.acheie.domain.exceptions.AcheieException;
import com.upe.br.acheie.domain.exceptions.ErrorMessage;
import com.upe.br.acheie.domain.enums.Atualizacao;
import com.upe.br.acheie.domain.enums.Category;
import com.upe.br.acheie.repository.ItemRepository;
import com.upe.br.acheie.repository.PostRepository;
import com.upe.br.acheie.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepo;

  private final UserRepository usuarioRepo;

  private final ItemService itemService;

  private final UserService userService;

  private final ItemRepository itemRepo;

  private final HibernateSearchService hibernateSearchService;

  private static final List<String> SEARCH_FIELDS = Arrays.asList("item.title",
      "item.description");

  @Transactional
  public RegisterPostResponse registerPost(UUID userId, RegisterPostRequest request) {
    User user = usuarioRepo.findById(userId)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_USUARIO_NAO_ENCONTRADO));

    if (request.type() == Type.DEVOLVIDO) {
      throw new AcheieException(ErrorMessage.MSG_ATUALIZAR_TIPO_POST_INVALIDO);
    }

    Post newPost = new Post(request, user);
    postRepo.save(newPost);

    UUID itemId = itemService.registerItem(request.item(), newPost.getId());
    Item item = itemRepo.findById(itemId)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_ITEM_NAO_ENCONTRADO));
    newPost.setItem(item);

    hibernateSearchService.indexEntity(newPost);

    return new RegisterPostResponse(newPost.getId(), newPost.getUser().getId(),
        newPost.getCreatedAt());
  }

  public PostDto searchPostById(UUID postId) {
    Post post = postRepo.findById(postId)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_POST_NAO_ENCONTRADO));
    return new PostDto(post);
  }

  public List<PostDto> searchAllPosts() {
    return postRepo.findAll().stream().map(PostDto::new).toList();
  }

  public List<PostDto> searchPostsByUserId(UUID userId) {
    return postRepo.findByUsuarioId(userId).stream().map(PostDto::new).toList();
  }

  public List<PostDto> searchPostsByText(String text, List<String> fields, int limit) {
    List<String> searchFields = fields.isEmpty() ? SEARCH_FIELDS : fields;

    boolean areFieldsValid = searchFields.stream()
        .anyMatch(f -> !SEARCH_FIELDS.contains(f));

    if (areFieldsValid) {
      throw new IllegalArgumentException();
    }

    return postRepo.searchBy(text, limit, searchFields.toArray(new String[0])).stream()
        .map(PostDto::new).toList();
  }

  public Atualizacao updatePost(UUID postId, RegisterPostRequest request) {
    Post post = postRepo.findById(postId)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_POST_NAO_ENCONTRADO));

    if (request.type() == Type.DEVOLVIDO) {
      throw new AcheieException(ErrorMessage.MSG_ATUALIZAR_TIPO_POST_INVALIDO);
    }

    post.setType(request.type());
    this.itemService.updateItem(post.getItem().getId(), request.item());

    return Atualizacao.ATUALIZACAO_COM_SUCESSO;
  }

  //Filtro: estado, categoria, tipo e lostAt

  public List<PostDto> searchPostsByType(Type type) {
    return postRepo.findByType(type).stream().map(PostDto::new).toList();
  }


  public List<PostDto> searchPostsByStatus(Status status) {
    return postRepo.findByItemEstado(status).stream().map(PostDto::new).toList();
  }

  public List<PostDto> searchPostsByCategory(Category category) {
    return postRepo.findByItemCategoria(category).stream().map(PostDto::new).toList();
  }

  public List<PostDto> searchPostsByLostDate(LocalDate start, LocalDate end) {
    if (end == null) {
      end = LocalDate.now();
    }

    return postRepo.findByItemData(start, end).stream().map(PostDto::new).toList();
  }

  public RemovePostResponse removePostById(UUID postId, UUID userId) {
    Post postToRemove = postRepo.findByUsuarioIdAndPostId(userId, postId)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_POST_DE_USUARIO_NAO_ENCONTRADO));

    postRepo.deleteById(postId);
    postToRemove.setRemovedAt(LocalDate.now());

    return new RemovePostResponse(postToRemove.getCreatedAt(),
        postToRemove.getRemovedAt(), postToRemove.getUser().getId());
  }

  public CloseSearchResponse closeSearch(CloseSearchRequest request) {
    usuarioRepo.findById(request.userId())
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_USUARIO_NAO_ENCONTRADO));
    Post postToClose = postRepo.findById(request.postId())
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_POST_NAO_ENCONTRADO));
    Item itemToClose = itemRepo.findById(postToClose.getItem().getId())
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_ITEM_NAO_ENCONTRADO));

    if (!postToClose.getUser().getId().equals(request.userId())) {
      throw new AcheieException(ErrorMessage.MSG_ENCERRAR_POST_INVALIDO);
    }

    postToClose.setType(Type.DEVOLVIDO);
    postToClose.setReturnedAt(LocalDate.now());
    this.postRepo.save(postToClose);

    itemToClose.setStatus(Status.DEVOLVIDO);
    this.itemRepo.save(itemToClose);

    return new CloseSearchResponse(postToClose.getUser().getId(),
        postToClose.getId(), postToClose.getReturnedAt());
  }
}
