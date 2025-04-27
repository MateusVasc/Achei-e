package com.upe.br.acheie.dtos.response;

import com.upe.br.acheie.dtos.ItemDTO;

import java.util.List;

public record PageResponse(
        List<ItemDTO> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}
