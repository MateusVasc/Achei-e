package com.upe.br.acheie;

import com.upe.br.acheie.infra.persistence.Indexer;
import com.upe.br.acheie.infra.search.TextSearchRepositoryImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = TextSearchRepositoryImpl.class)
public class AcheiEApplication {

  public static void main(String[] args) {
    SpringApplication.run(AcheiEApplication.class, args);
  }

  @Bean
  public ApplicationRunner buildIndex(Indexer indexer) {
    return (ApplicationArguments args) -> indexer.indexPersistedData(
        "com.upe.br.acheie.domain.entities.Post");
  }

}
