package com.upe.br.acheie.dominio.modelos;


import com.upe.br.acheie.dominio.dto.UsuarioDto;
import com.upe.br.acheie.dominio.dto.request.CadastroRequest;
import com.upe.br.acheie.dominio.utils.enums.Curso;
import com.upe.br.acheie.dominio.utils.enums.Periodo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_usuario")
  private UUID id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String sobrenome;

  @Email
  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String senha;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Curso curso;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Periodo periodo;

  @Column(nullable = false)
  private String telefone;

  private byte[] foto;

  @Column(name = "criacao_da_conta", nullable = false)
  private LocalDate criacaoDaConta;

  @Column(name = "remocao_da_conta")
  private LocalDate remocaoDaConta;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Post> posts;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comentario> comentarios;

  @ManyToMany
  @JoinTable(
      name = "usuario_conversa",
      joinColumns = @JoinColumn(name = "id_usuario"),
      inverseJoinColumns = @JoinColumn(name = "id_conversa")
  )
  private Set<Conversa> conversas;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Mensagem> mensagens;

  public Usuario(CadastroRequest request) {
    this.nome = request.nome();
    this.sobrenome = request.sobrenome();
    this.email = request.email();
    this.senha = request.senha();
    this.curso = request.curso();
    this.periodo = request.periodo();
    this.telefone = request.telefone();
    this.foto = request.foto();
  }
  
  public Usuario(UsuarioDto usuarioDto) {
	  this.nome = usuarioDto.nome();
	  this.sobrenome = usuarioDto.sobrenome();
	  this.curso = usuarioDto.curso();
	  this.periodo = usuarioDto.periodo();
	  this.telefone = usuarioDto.telefone();
	  this.foto = usuarioDto.foto();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("USUARIO"));
  }

  @Override
  public String getPassword() {
    return this.senha;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
