package com.project.shopapp.services.comment;

import com.project.shopapp.dtos.CommentDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Comment;
import com.project.shopapp.responses.comment.CommentResponse;

import java.util.List;

public interface CommentService {
    List<CommentResponse> getCommentsByUserAndProduct(Long userId, Long productId);
    List<CommentResponse> getCommentsByProduct(Long productId);
    Comment createComment(CommentDTO comment);
    void updateComment(Long id, CommentDTO commentDTO) throws DataNotFoundException;
}
