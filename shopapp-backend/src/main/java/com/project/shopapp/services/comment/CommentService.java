package com.project.shopapp.services.comment;

import com.project.shopapp.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentServiceImpl {
    private final CommentRepository commentRepository;
}
