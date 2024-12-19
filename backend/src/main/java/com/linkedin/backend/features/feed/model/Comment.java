package com.linkedin.backend.features.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linkedin.backend.features.authentication.model.AuthenticationUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="author_id",nullable = false)
    private AuthenticationUser author;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @JsonIgnore
    private Post post;


    @CreationTimestamp
    @Column(columnDefinition = "datetime")
    private LocalDateTime creationDate;

    @Column(columnDefinition = "datetime")
    private LocalDateTime updatedDate;

    public Comment(Post post, AuthenticationUser author, String content) {
        this.post = post;
        this.author = author;
        this.content = content;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
