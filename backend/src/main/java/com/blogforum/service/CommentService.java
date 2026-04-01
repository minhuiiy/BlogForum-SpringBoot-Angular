package com.blogforum.service;

import com.blogforum.entity.NotificationMessage;
import com.blogforum.entity.Comment;
import com.blogforum.entity.Post;
import com.blogforum.repository.CommentRepository;
import com.blogforum.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    // "Loa phóng thanh" để gửi thông báo Real-time qua WebSocket
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Transactional
    public Comment addComment(Long postId, String text, Long userId, Long parentId) {
        // 1. Tìm bài viết
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết"));

        // 2. Tạo đối tượng Comment
        Comment comment = Comment.builder()
                .text(text)
                .post(post)
                .userId(userId)
                .build();

        // 3. Xử lý logic Đệ quy (Nếu đây là câu Reply)
        if (parentId != null) {
            Comment parent = commentRepository.findById(parentId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy bình luận gốc"));
            comment.setParent(parent);
        }

        // 4. Lưu vào Database
        Comment savedComment = commentRepository.save(comment);

        // 5. --- REAL-TIME TRIGGER STOMP ---
        // Phát thông báo ra kênh "/topic/notifications" ngay sau khi lưu DB thành công
        NotificationMessage notif = new NotificationMessage(
                "COMMENT",
                "Ai đó vừa bình luận vào bài viết số " + postId,
                postId);
        messagingTemplate.convertAndSend("/topic/notifications", notif);
        // ----------------------------------

        // 6. Trả về kết quả
        return savedComment;
    }

    // Lấy danh sách cây gia phả bình luận
    public List<Comment> getCommentsTree(Long postId) {
        return commentRepository.findByPostIdAndParentIsNull(postId);
    }
}