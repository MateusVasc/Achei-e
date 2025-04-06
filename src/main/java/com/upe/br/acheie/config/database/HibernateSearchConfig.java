package com.upe.br.acheie.config.database;

import com.upe.br.acheie.service.HibernateSearchService;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateSearchConfig {

  @Bean
  public HibernateSearchService hibernateSearchService(EntityManagerFactory entityManagerFactory) {
    SearchSession searchSession = Search.session(entityManagerFactory.createEntityManager());
    return new HibernateSearchService(searchSession);
  }
}
