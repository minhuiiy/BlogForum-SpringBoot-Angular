package com.blogforum.service;

import com.blogforum.entity.Post;
import com.blogforum.entity.Vote;
import com.blogforum.entity.VoteType;
import com.blogforum.repository.PostRepository;
import com.blogforum.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public void vote(Long postId, VoteType newVoteType, Long userId) {
        // 1. Tìm bài viết, nếu không có thì báo lỗi
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết"));

        // 2. Kiểm tra xem User này đã từng vote bài này chưa
        Optional<Vote> existingVote = voteRepository.findByPostAndUserId(post, userId);

        if (existingVote.isPresent()) {
            Vote currentVote = existingVote.get();

            // THUẬT TOÁN NHẢ VOTE: Nếu bấm lại đúng loại vote cũ (ví dụ: đang Up lại bấm
            // Up)
            if (currentVote.getVoteType().equals(newVoteType)) {
                // Trừ lại điểm đã cộng (hoặc cộng lại điểm đã trừ)
                post.setVoteCount(post.getVoteCount() - newVoteType.getDirection());
                voteRepository.delete(currentVote); // Xóa bản ghi vote trong DB
            }
            // ĐỔI CHIỀU VOTE: Đang Up chuyển sang Down (hoặc ngược lại)
            else {
                // Ví dụ: Đang Up (+1) sang Down (-1) => Điểm phải giảm đi 2
                post.setVoteCount(
                        post.getVoteCount() - currentVote.getVoteType().getDirection() + newVoteType.getDirection());
                currentVote.setVoteType(newVoteType);
                voteRepository.save(currentVote);
            }
        } else {
            // VOTE MỚI HOÀN TOÀN
            post.setVoteCount(post.getVoteCount() + newVoteType.getDirection());
            Vote vote = Vote.builder()
                    .voteType(newVoteType)
                    .post(post)
                    .userId(userId)
                    .build();
            voteRepository.save(vote);
        }

        // Lưu lại tổng điểm mới vào bảng Posts
        postRepository.save(post);
    }
}