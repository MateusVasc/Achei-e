package com.upe.br.acheie.mappers;

import com.upe.br.acheie.domain.entities.Comment;
import com.upe.br.acheie.dtos.CommentDTO;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ToCommentDTO {

    public static CommentDTO toCommentDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getSubject(),
                comment.getCreatedBy(),
                comment.getCreatedAt()
        );
    }

    public static List<CommentDTO> toCommentDTOs(List<Comment> comments) {
        List<CommentDTO> dtos = new ArrayList<>();

        for (Comment c : comments) {
            dtos.add(
                    new CommentDTO(
                            c.getId(),
                            c.getSubject(),
                            c.getCreatedBy(),
                            c.getCreatedAt()
                    )
            );
        }

        return dtos;
    }
}
