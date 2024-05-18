package com.upe.br.acheie.dominio.modelos;

import com.upe.br.acheie.dominio.enums.Tipo;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
  private Date criacaoDoPost;

  @Column(name = "remocao_do_post")
  private Date remocaoDoPost;

  @Column(name = "devolucao_do_item")
  private Date devolucaoItem;

  @ManyToOne
  @JoinColumn(name = "id_usuario", nullable = false)
  private Usuario usuario;

  @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
  private Item item;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comentario> comentarios;
}
