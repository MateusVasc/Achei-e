package com.upe.br.acheie.repositorio;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PesquisaDeTextoRepositorio<T, ID extends Serializable> extends
    JpaRepository<T, ID> {

  List<T> searchBy(String text, int limit, String... fields);
}
