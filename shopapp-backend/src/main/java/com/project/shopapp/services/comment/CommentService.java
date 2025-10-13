package com.project.shopapp.services.comment;

import com.project.shopapp.responses.comment.CommentResponse;

import java.util.List;

public interface CommentService {
    List<CommentResponse> getCommentsByUserAndProduct(Long userId, Long productId);
    List<CommentResponse> getCommentsByProduct(Long productId);
}
