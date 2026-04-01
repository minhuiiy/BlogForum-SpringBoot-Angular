package com.blogforum.controller;

import com.blogforum.entity.VoteType;
import com.blogforum.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping("/{postId}")
    public String vote(@PathVariable Long postId,
            @RequestParam VoteType type,
            @RequestParam Long userId) {
        voteService.vote(postId, type, userId);
        return "Vote thành công!";
    }
}