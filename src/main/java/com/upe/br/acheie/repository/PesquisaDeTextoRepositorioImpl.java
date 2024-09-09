package com.upe.br.acheie.repository;

import jakarta.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class PesquisaDeTextoRepositorioImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
    implements PesquisaDeTextoRepositorio<T, ID> {

  private final EntityManager entityManager;

  public PesquisaDeTextoRepositorioImpl(Class<T> domainClass, EntityManager entityManager) {
    super(domainClass, entityManager);
    this.entityManager = entityManager;
  }

  public PesquisaDeTextoRepositorioImpl(
      JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityManager = entityManager;
  }

  @Override
  public List<T> searchBy(String text, int limit, String... fields) {

    SearchResult<T> result = getSearchResult(text, limit, fields);

    return result.hits();
  }

  private SearchResult<T> getSearchResult(String text, int limit, String[] fields) {
    SearchSession searchSession = Search.session(entityManager);

    return searchSession
            .search(getDomainClass())
            .where(f -> f.match().fields(fields).matching(text).fuzzy(2))
            .fetch(limit);
  }
}
