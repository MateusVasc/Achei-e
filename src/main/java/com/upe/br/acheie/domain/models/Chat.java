package com.upe.br.acheie.domain.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_conversa")
  private UUID id;

  @ManyToMany(mappedBy = "conversas")
  private Set<User> users;

  @OneToMany(mappedBy = "conversa", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Message> messages;
}
