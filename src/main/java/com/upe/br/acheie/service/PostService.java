package com.upe.br.acheie.service;

import com.upe.br.acheie.domain.enums.State;
import com.upe.br.acheie.domain.enums.Type;
import com.upe.br.acheie.dtos.request.CreatePostRequest;
import com.upe.br.acheie.dtos.response.CreatePostResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dtos.PostDTO;
import com.upe.br.acheie.dtos.request.EndSearchRequest;
import com.upe.br.acheie.dtos.response.EndSearchResponse;
import com.upe.br.acheie.dtos.response.DeletePostResponse;
import com.upe.br.acheie.domain.models.Item;
import com.upe.br.acheie.domain.models.Post;
import com.upe.br.acheie.domain.models.User;
import com.upe.br.acheie.utils.AcheieException;
import com.upe.br.acheie.utils.ErrorMessages;
import com.upe.br.acheie.domain.enums.Update;
import com.upe.br.acheie.domain.enums.Category;
import com.upe.br.acheie.repository.ItemRepository;
import com.upe.br.acheie.repository.PostRepository;
import com.upe.br.acheie.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final ItemService itemService;
  private final UserService userService;
  private final ItemRepository itemRepository;
  private final HibernateSearchService hibernateSearchService;

  private static final List<String> SEARCH_FIELDS = Arrays.asList("item.title",
      "item.description");

  @Transactional
  public CreatePostResponse createPost(UUID userId, CreatePostRequest request) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new AcheieException(ErrorMessages.MSG_USER_NOT_FOUND));

    if (request.type() == Type.RETURNED) {
      throw new AcheieException(ErrorMessages.MSG_INVALID_POST_TYPE_UPDATE);
    }

    Post newPost = new Post(request, user);
    postRepository.save(newPost);

    UUID itemId = itemService.createItem(request.item(), newPost.getId());
    Item item = itemRepository.findById(itemId)
        .orElseThrow(() -> new AcheieException(ErrorMessages.MSG_ITEM_NOT_FOUND));
    newPost.setItem(item);

    hibernateSearchService.indexEntity(newPost);

    return new CreatePostResponse(newPost.getId(), newPost.getUser().getId(),
        newPost.getCreatedAt());
  }

  public PostDTO searchPostById(UUID postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new AcheieException(ErrorMessages.MSG_POST_NOT_FOUND));
    return new PostDTO(post);
  }

  public List<PostDTO> searchPosts() {
    return postRepository.findAll().stream().map(PostDTO::new).toList();
  }

  public List<PostDTO> searchPostsByUserId(UUID userId) {
    return postRepository.findByUserId(userId).stream().map(PostDTO::new).toList();
  }

  public List<PostDTO> searchPostsByText(String text, List<String> fields, int limit) {
    List<String> fieldsToSearch = fields.isEmpty() ? SEARCH_FIELDS : fields;

    boolean hasInvalidFields = fieldsToSearch.stream()
        .anyMatch(f -> !SEARCH_FIELDS.contains(f));

    if (hasInvalidFields) {
      throw new IllegalArgumentException();
    }

    return postRepository.searchBy(text, limit, fieldsToSearch.toArray(new String[0])).stream()
        .map(PostDTO::new).toList();
  }

  public Update updatePost(UUID postId, CreatePostRequest request) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new AcheieException(ErrorMessages.MSG_POST_NOT_FOUND));

    if (request.type() == Type.RETURNED) {
      throw new AcheieException(ErrorMessages.MSG_INVALID_POST_TYPE_UPDATE);
    }

    post.setType(request.type());
    this.itemService.updateItem(post.getItem().getId(), request.item());

    return Update.UPDATED_SUCCESSFULLY;
  }

  //Filtro: estado, categoria, tipo e date

  public List<PostDTO> getPostsByType(Type type) {
    return postRepository.findByType(type).stream().map(PostDTO::new).toList();
  }

  public List<PostDTO> getPostsByState(State state) {
    return postRepository.findByItemState(state).stream().map(PostDTO::new).toList();
  }

  public List<PostDTO> getPostsByCategory(Category category) {
    return postRepository.findByItemCategory(category).stream().map(PostDTO::new).toList();
  }

  public List<PostDTO> getPostsByDate(LocalDate start, LocalDate end) {
    if (end == null) {
      end = LocalDate.now();
    }

    return postRepository.findByItemDate(start, end).stream().map(PostDTO::new).toList();
  }

  public DeletePostResponse deletePostById(UUID postId, UUID userId) {
    Post postParaRemover = postRepository.findByUserIdAndPostId(userId, postId)
        .orElseThrow(() -> new AcheieException(ErrorMessages.MSG_USER_POST_NOT_FOUND));

    postRepository.deleteById(postId);
    postParaRemover.setDeletedAt(LocalDate.now());

    return new DeletePostResponse(postParaRemover.getCreatedAt(),
        postParaRemover.getDeletedAt(), postParaRemover.getUser().getId());
  }

  public EndSearchResponse endItemSearch(EndSearchRequest request) {
    userRepository.findById(request.userId())
        .orElseThrow(() -> new AcheieException(ErrorMessages.MSG_USER_NOT_FOUND));
    Post postToEnd = postRepository.findById(request.postId())
        .orElseThrow(() -> new AcheieException(ErrorMessages.MSG_POST_NOT_FOUND));
    Item itemToEnd = itemRepository.findById(postToEnd.getItem().getId())
        .orElseThrow(() -> new AcheieException(ErrorMessages.MSG_ITEM_NOT_FOUND));

    if (!postToEnd.getUser().getId().equals(request.userId())) {
      throw new AcheieException(ErrorMessages.MSG_INVALID_POST_CLOSURE);
    }

    postToEnd.setType(Type.RETURNED);
    postToEnd.setReturnedAt(LocalDate.now());
    this.postRepository.save(postToEnd);

    itemToEnd.setState(State.RETURNED);
    this.itemRepository.save(itemToEnd);

    return new EndSearchResponse(postToEnd.getUser().getId(),
        postToEnd.getId(), postToEnd.getReturnedAt());
  }
}
