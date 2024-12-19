package com.linkedin.backend.features.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linkedin.backend.features.authentication.model.AuthenticationUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name= "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String content;
    private String picture;

    @ManyToOne
    @JoinColumn(nullable = false)
    private AuthenticationUser author;

    @CreationTimestamp
    @Column(columnDefinition = "datetime")
    private LocalDateTime creationDate;

    @Column(columnDefinition = "datetime")
    private LocalDateTime updatedDate;


    @ManyToMany
    @JoinTable(
            name = "posts_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<AuthenticationUser>likes;

    @JsonIgnore
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment>comments;

    @PreUpdate
    public void preUpdate(){
        this.updatedDate=LocalDateTime.now();
    }

    public Post(String content, AuthenticationUser author) {
        this.content=content;
        this.author=author;
    }
}
