package com.upe.br.acheie.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.br.acheie.domain.model.Item;

public interface ItemRepository extends JpaRepository<Item, UUID> {

}
