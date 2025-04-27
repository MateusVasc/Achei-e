package com.upe.br.acheie.mappers;

import com.upe.br.acheie.domain.entities.Comment;
import com.upe.br.acheie.domain.entities.Item;
import com.upe.br.acheie.dtos.CommentDTO;
import com.upe.br.acheie.dtos.ItemDTO;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ToItemDTO {

    public static ItemDTO toItemDTO(Item item) {
        return new ItemDTO(
            item.getId(),
            item.getTitle(),
            item.getDescription(),
            item.getStatus(),
            item.getCategory(),
            item.getImage(),
            item.getCreatedBy(),
            item.getCreatedAt(),
            item.getLostAt(),
            item.getFoundAt(),
            item.getReturnedAt(),
            ToCommentDTO.toCommentDTOs(item.getComments())
        );
    }

    public static List<ItemDTO> toItemDTOs(List<Item> items) {
        List<ItemDTO> dtos = new ArrayList<>();

        for (Item i : items) {
            dtos.add(
                    new ItemDTO(
                            i.getId(),
                            i.getTitle(),
                            i.getDescription(),
                            i.getStatus(),
                            i.getCategory(),
                            i.getImage(),
                            i.getCreatedBy(),
                            i.getCreatedAt(),
                            i.getLostAt(),
                            i.getFoundAt(),
                            i.getReturnedAt(),
                            ToCommentDTO.toCommentDTOs(i.getComments())
                    )
            );
        }

        return dtos;
    }
}
