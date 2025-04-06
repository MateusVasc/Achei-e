package com.upe.br.acheie.config.database;

import com.upe.br.acheie.repository.PesquisaDeTextoRepositorioImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = PesquisaDeTextoRepositorioImpl.class)
public class AppConfig {

}
