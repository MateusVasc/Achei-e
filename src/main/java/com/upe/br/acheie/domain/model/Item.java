package com.upe.br.acheie.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import com.upe.br.acheie.domain.dto.ItemDto;
import com.upe.br.acheie.domain.enums.Categoria;
import com.upe.br.acheie.domain.enums.Estado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Indexed
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_item")
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Estado estado;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Categoria categoria;

  @Column(nullable = false)
  @FullTextField
  private String descricao;

  @Column(nullable = false)
  @FullTextField
  private String titulo;

  @Column(nullable = false)
  private byte[] foto;

  @Column(nullable = false)
  private LocalDate data;

  @OneToOne
  @JoinColumn(name = "id_post", nullable = false)
  private Post post;
  
  public Item(ItemDto itemDto, Post post) {
	  this.estado = itemDto.estado();
	  this.categoria = itemDto.categoria();
	  this.descricao = itemDto.descricao();
	  this.titulo = itemDto.titulo();
	  this.foto = itemDto.foto();
	  this.data = itemDto.data();
	  this.post = post;
  }
}
