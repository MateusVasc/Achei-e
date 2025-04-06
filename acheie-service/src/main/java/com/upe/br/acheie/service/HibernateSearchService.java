package com.upe.br.acheie.service;

import jakarta.transaction.Transactional;
import org.hibernate.search.mapper.orm.session.SearchSession;

public class HibernateSearchService {

  private final SearchSession searchSession;

  public HibernateSearchService(SearchSession searchSession) {
    this.searchSession = searchSession;
  }

  @Transactional
  public void indexEntity(Object entity) {
    searchSession.indexingPlan().addOrUpdate(entity);
  }
}
