package com.upe.br.acheie.controllers;

import com.upe.br.acheie.dtos.response.PageResponse;
import com.upe.br.acheie.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/achei-e")
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/items")
  public ResponseEntity<PageResponse> getAllItems(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size
  ) {
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.itemService.getAllItems(pageable));
  }
}
