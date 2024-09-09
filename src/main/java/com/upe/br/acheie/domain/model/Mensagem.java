package com.upe.br.acheie.domain.model;


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
public class Mensagem {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_mensagem")
  private UUID id;

  @Column(nullable = false)
  private String conteudo;

  @Column(name = "envio_da_mensagem", nullable = false)
  private LocalDate envio;

  @Column(name = "remocao_da_mensagem", nullable = false)
  private LocalDate remocao;

  @ManyToOne
  @JoinColumn(name = "id_usuario", nullable = false)
  private Usuario usuario;

  @ManyToOne
  @JoinColumn(name = "id_conversa", nullable = false)
  private Conversa conversa;
}
