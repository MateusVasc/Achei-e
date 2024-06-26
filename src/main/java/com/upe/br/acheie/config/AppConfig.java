package com.upe.br.acheie.config;

import com.upe.br.acheie.repositorio.PesquisaDeTextoRepositorioImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = PesquisaDeTextoRepositorioImpl.class)
public class AppConfig {

}
