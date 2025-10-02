package com.project.shopapp.controllers;

import com.project.shopapp.services.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<String> createComment() {
        return ResponseEntity.ok("Comment created successfully.");
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok("Comment updated successfully. commentId = " + commentId);
    }

    @GetMapping("")
    public ResponseEntity<String> getAllComments(
            @RequestParam("userId") Long userId,
            @RequestParam("productId") Long productId
    ) {
        return ResponseEntity.ok("Comments retrieved successfully.");
    }

    @PostMapping("/generateFakeComments")
    public ResponseEntity<String> generateFakeComments() {
        return ResponseEntity.ok("Fake comments generated successfully.");
    }

}
