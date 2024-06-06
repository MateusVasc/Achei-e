package com.upe.br.acheie.servico;

import jakarta.transaction.Transactional;
import org.hibernate.search.mapper.orm.session.SearchSession;

public class HibernateSearchServico {

  private final SearchSession searchSession;

  public HibernateSearchServico(SearchSession searchSession) {
    this.searchSession = searchSession;
  }

  @Transactional
  public void indexEntity(Object entity) {
    searchSession.indexingPlan().addOrUpdate(entity);
  }
}
