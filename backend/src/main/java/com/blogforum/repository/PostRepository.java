package com.blogforum.repository;

import com.blogforum.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // JpaRepository đã có sẵn save(), findAll(), findById() cho bạn dùng
}