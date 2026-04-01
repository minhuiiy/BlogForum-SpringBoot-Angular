package com.blogforum.service;

import com.blogforum.entity.Post;
import com.blogforum.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost(Post post) {
        if (post.getVoteCount() == null) {
            post.setVoteCount(0);
        }
        return postRepository.save(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết"));
    }
}