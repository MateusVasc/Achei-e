package com.upe.br.acheie.infra;

import com.upe.br.acheie.repository.TextSearchRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = TextSearchRepositoryImpl.class)
public class AppConfig {

}
