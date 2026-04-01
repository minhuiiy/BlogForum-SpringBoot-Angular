package com.blogforum.controller;

import com.blogforum.entity.Comment;
import com.blogforum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/{postId}")
    public Comment create(@PathVariable Long postId,
            @RequestParam String text,
            @RequestParam Long userId,
            @RequestParam(required = false) Long parentId) {
        return commentService.addComment(postId, text, userId, parentId);
    }

    @GetMapping("/{postId}")
    public List<Comment> getTree(@PathVariable Long postId) {
        return commentService.getCommentsTree(postId);
    }
}
