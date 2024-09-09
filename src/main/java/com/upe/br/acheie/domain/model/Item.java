package com.upe.br.acheie.domain.model;

import com.upe.br.acheie.domain.enums.Category;
import java.time.LocalDate;
import java.util.UUID;

import com.upe.br.acheie.domain.dto.ItemDto;
import com.upe.br.acheie.domain.enums.Status;

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
  private Status status;

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
  private byte[] photo;

  @Column(nullable = false)
  private LocalDate lostAt;

  @OneToOne
  @JoinColumn(name = "id_post", nullable = false)
  private Post post;
  
  public Item(ItemDto itemDto, Post post) {
	  this.status = itemDto.status();
	  this.category = itemDto.category();
	  this.description = itemDto.descricao();
	  this.title = itemDto.titulo();
	  this.photo = itemDto.foto();
	  this.lostAt = itemDto.data();
	  this.post = post;
  }
}
