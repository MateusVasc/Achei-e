package com.upe.br.acheie.domain.models;

import java.time.LocalDate;
import java.util.UUID;

import com.upe.br.acheie.dtos.ItemDto;
import com.upe.br.acheie.domain.enums.Category;
import com.upe.br.acheie.domain.enums.State;

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
  private State state;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Category category;

  @Column(nullable = false)
  @FullTextField
  private String description;

  @Column(nullable = false)
  @FullTextField
  private String title;

  @Column(nullable = false)
  private byte[] image;

  @Column(nullable = false)
  private LocalDate date;

  @OneToOne
  @JoinColumn(name = "id_post", nullable = false)
  private Post post;
  
  public Item(ItemDto itemDto, Post post) {
	  this.state = itemDto.state();
	  this.category = itemDto.category();
	  this.description = itemDto.descricao();
	  this.title = itemDto.titulo();
	  this.image = itemDto.foto();
	  this.date = itemDto.data();
	  this.post = post;
  }
}
