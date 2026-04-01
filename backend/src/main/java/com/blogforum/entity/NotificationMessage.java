package com.blogforum.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessage {
    private String type; // Loại thông báo: "COMMENT" hoặc "VOTE"
    private String message; // Nội dung: "Có người vừa bình luận..."
    private Long postId; // ID bài viết để Frontend biết đường tô đậm
}