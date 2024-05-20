package com.upe.br.acheie.dominio.modelos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.upe.br.acheie.dominio.dto.PostDto;
import com.upe.br.acheie.dominio.utils.enums.Tipo;

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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_post")
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Tipo tipo;

  @Column(name = "criacao_do_post", nullable = false)
  private LocalDate criacaoDoPost;

  @Column(name = "remocao_do_post")
  private LocalDate remocaoDoPost;

  @Column(name = "devolucao_do_item")
  private LocalDate devolucaoItem;

  @ManyToOne
  @JoinColumn(name = "id_usuario", nullable = false)
  private Usuario usuario;

  @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
  private Item item;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comentario> comentarios;
  
  public Post(PostDto postDto, Usuario usuario) {
	this.tipo = postDto.tipo();
	this.criacaoDoPost = postDto.dataCriacao() != null ? postDto.dataCriacao() : LocalDate.now();
	this.remocaoDoPost = postDto.dataRemocao();
	this.devolucaoItem = null;
	this.usuario = usuario;
	this.item = null;
	this.comentarios = null;
  }
  
}
