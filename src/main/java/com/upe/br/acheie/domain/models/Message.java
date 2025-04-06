package com.upe.br.acheie.domain.models;


import java.time.LocalDate;
import java.util.UUID;

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
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_mensagem")
  private UUID id;

  @Column(nullable = false)
  private String content;

  @Column(name = "envio_da_mensagem", nullable = false)
  private LocalDate sentAt;

  @Column(name = "remocao_da_mensagem", nullable = false)
  private LocalDate deletedAt;

  @ManyToOne
  @JoinColumn(name = "id_usuario", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "id_conversa", nullable = false)
  private Chat chat;
}
