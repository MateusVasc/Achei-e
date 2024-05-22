package com.upe.br.acheie.repositorio;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.br.acheie.dominio.modelos.Item;

public interface ItemRepositorio extends JpaRepository<Item, UUID> {

}
