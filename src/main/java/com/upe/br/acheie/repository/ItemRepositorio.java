package com.upe.br.acheie.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.br.acheie.domain.models.Item;

public interface ItemRepositorio extends JpaRepository<Item, UUID> {

}
