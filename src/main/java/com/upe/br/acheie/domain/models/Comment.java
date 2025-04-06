package com.upe.br.acheie.domain.models;


import java.time.LocalDate;
import java.util.UUID;

import com.upe.br.acheie.dtos.CommentDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_comentario")
  private UUID id;

  @Column(nullable = false)
  private String subject;

  @Column(name = "criacao_do_comentario", nullable = false)
  private LocalDate createdAt;


  @Column(name = "remocao_do_comentario")
  private LocalDate deletedAt;

  @ManyToOne
  @JoinColumn(name = "id_usuario", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "id_post", nullable = false)
  private Post post;
  
  public Comment(CommentDTO comentario, Post post, User user) {
	  this.subject = comentario.subject();
	  this.createdAt = comentario.createdAt() != null ? comentario.createdAt() :
		  LocalDate.now();
	  this.deletedAt = null;
	  this.user = user;
	  this.post = post;
  }

  public Comment(String subject, Post post, User user) {
    this.subject = subject;
    this.createdAt = LocalDate.now();
    this.deletedAt = null;
    this.user = user;
    this.post = post;
  }
}
