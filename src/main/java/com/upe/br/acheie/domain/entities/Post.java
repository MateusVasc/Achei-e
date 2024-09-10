package com.upe.br.acheie.domain.entities;


import com.upe.br.acheie.domain.dto.request.RegisterPostRequest;
import com.upe.br.acheie.domain.enums.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.upe.br.acheie.domain.dto.PostDto;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Indexed
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_post")
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Type type;

  @Column(name = "criacao_do_post", nullable = false)
  private LocalDate createdAt;

  @Column(name = "remocao_do_post")
  private LocalDate removedAt;

  @Column(name = "devolucao_do_item")
  private LocalDate returnedAt;

  @ManyToOne
  @JoinColumn(name = "id_usuario", nullable = false)
  private User user;

  @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
  @IndexedEmbedded(includePaths = {"title", "description"})
  private Item item;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;
  
  public Post(PostDto postDto, User user) {
    this.id = postDto.postId();
	this.type = postDto.type();
	this.createdAt = postDto.createdAt() != null ? postDto.createdAt() : LocalDate.now();
	this.removedAt = postDto.removedAt();
	this.returnedAt = null;
	this.user = user;
	this.item = null;
	this.comments = null;
  }

  public Post(RegisterPostRequest request, User user) {
    this.type = request.type();
    this.createdAt = LocalDate.now();
    this.removedAt = null;
    this.returnedAt = null;
    this.user = user;
    this.item = null;
    this.comments = null;
  }
  
}
