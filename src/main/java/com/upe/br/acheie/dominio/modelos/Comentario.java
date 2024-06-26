package com.upe.br.acheie.dominio.modelos;


import java.time.LocalDate;
import java.util.UUID;

import com.upe.br.acheie.dominio.dto.ComentarioDto;

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
public class Comentario {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_comentario")
  private UUID id;

  @Column(nullable = false)
  private String assunto;

  @Column(name = "criacao_do_comentario", nullable = false)
  private LocalDate criacaoDoComentario;


  @Column(name = "remocao_do_comentario")
  private LocalDate remocaoDoComentario;

  @ManyToOne
  @JoinColumn(name = "id_usuario", nullable = false)
  private Usuario usuario;

  @ManyToOne
  @JoinColumn(name = "id_post", nullable = false)
  private Post post;
  
  public Comentario(ComentarioDto comentario, Post post, Usuario usuario) {
	  this.assunto = comentario.assunto();
	  this.criacaoDoComentario = comentario.dataCriacao() != null ? comentario.dataCriacao() : 
		  LocalDate.now();
	  this.remocaoDoComentario = null;
	  this.usuario = usuario;
	  this.post = post;
  }

  public Comentario(String assunto, Post post, Usuario usuario) {
    this.assunto = assunto;
    this.criacaoDoComentario = LocalDate.now();
    this.remocaoDoComentario = null;
    this.usuario = usuario;
    this.post = post;
  }
}
