package com.upe.br.acheie.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.br.acheie.domain.entities.Item;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    Page<Item> findAll(Pageable pageable);
}
