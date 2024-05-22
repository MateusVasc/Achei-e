package com.upe.br.acheie.dominio.modelos;

import java.time.LocalDate;
import java.util.UUID;

import com.upe.br.acheie.dominio.dto.ItemDto;
import com.upe.br.acheie.dominio.utils.enums.Categoria;
import com.upe.br.acheie.dominio.utils.enums.Estado;

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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_item")
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Estado estado; // pra q q serve isso msm?

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Categoria categoria; // pensar quais seriam as categorias (vão ser mtas provavelmente...)

  @Column(nullable = false)
  private String descricao;

  @Column(nullable = false)
  private String titulo;

  @Column(nullable = false)
  private byte[] foto;

  @Column(nullable = false)
  private LocalDate data; // precisa? o post já tem a data

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
