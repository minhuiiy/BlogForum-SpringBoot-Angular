package com.blogforum.repository;

import com.blogforum.entity.Post;
import com.blogforum.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    // Hàm cực kỳ quan trọng: Tìm xem User cụ thể đã Vote một bài viết cụ thể chưa
    // Spring Data JPA sẽ tự động tạo câu lệnh SQL:
    // SELECT * FROM votes WHERE post_id = ? AND user_id = ?
    Optional<Vote> findByPostAndUserId(Post post, Long userId);
}