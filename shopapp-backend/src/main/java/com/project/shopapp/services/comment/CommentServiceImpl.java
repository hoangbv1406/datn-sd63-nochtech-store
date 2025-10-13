package com.project.shopapp.services.comment;

import com.project.shopapp.models.Comment;
import com.project.shopapp.repositories.CommentRepository;
import com.project.shopapp.responses.comment.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public List<CommentResponse> getCommentsByUserAndProduct(Long userId, Long productId) {
        List<Comment> comments = commentRepository.findByUserIdAndProductId(userId, productId);
        return comments.stream().map(comment -> CommentResponse.fromComment(comment)).collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> getCommentsByProduct(Long productId) {
        List<Comment> comments = commentRepository.findByProductId(productId);
        return comments.stream().map(comment -> CommentResponse.fromComment(comment)).collect(Collectors.toList());
    }

}
