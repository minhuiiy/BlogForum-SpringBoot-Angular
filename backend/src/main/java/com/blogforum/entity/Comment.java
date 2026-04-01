package com.blogforum.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore // Tránh vòng lặp khi truy xuất từ Post
    private Post post;

    private Long userId; // ID người dùng (sau này Huy sẽ ráp Auth sau)

    // --- LOGIC ĐỆ QUY Ở ĐÂY ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore // CỰC KỲ QUAN TRỌNG: Tránh lỗi Infinite Recursion (vòng lặp vô tận) khi trả về
                // JSON
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> children; // Hibernate sẽ tự động lôi các con/cháu ra cho bạn

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
