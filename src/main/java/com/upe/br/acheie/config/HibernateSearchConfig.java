package com.upe.br.acheie.config;

import com.upe.br.acheie.servico.HibernateSearchServico;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateSearchConfig {

  @Bean
  public HibernateSearchServico hibernateSearchService(EntityManagerFactory entityManagerFactory) {
    SearchSession searchSession = Search.session(entityManagerFactory.createEntityManager());
    return new HibernateSearchServico(searchSession);
  }
}
