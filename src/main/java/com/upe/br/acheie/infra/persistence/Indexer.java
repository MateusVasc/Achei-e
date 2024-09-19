package com.upe.br.acheie.infra.persistence;

import com.upe.br.acheie.domain.exceptions.AcheieException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@RequiredArgsConstructor
public class Indexer {

  private final EntityManager entityManager;

  private static final int THREAD_NUMBER = 4;

  public void indexPersistedData(String indexClassName) throws AcheieException {

    try {
      SearchSession searchSession = Search.session(entityManager);

      Class<?> classToIndex = Class.forName(indexClassName);
      MassIndexer indexer =
          searchSession
              .massIndexer(classToIndex)
              .threadsToLoadObjects(THREAD_NUMBER);

      indexer.startAndWait();
    } catch (ClassNotFoundException e) {
      throw new AcheieException("Invalid class " + indexClassName, e);
    } catch (InterruptedException e) {
      throw new AcheieException("Index Interrupted", e);
    }
  }
}
