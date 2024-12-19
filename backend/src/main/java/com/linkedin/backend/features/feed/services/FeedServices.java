package com.linkedin.backend.features.feed.services;

import com.linkedin.backend.features.authentication.model.AuthenticationUser;
import com.linkedin.backend.features.authentication.repository.AuthenticationUserRepository;
import com.linkedin.backend.features.feed.dto.PostDto;
import com.linkedin.backend.features.feed.model.Comment;
import com.linkedin.backend.features.feed.model.Post;
import com.linkedin.backend.features.feed.repository.CommentRepository;
import com.linkedin.backend.features.feed.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedServices {

    private static final Logger log = LoggerFactory.getLogger(FeedServices.class);
    private final PostRepository postRepository;
    private final AuthenticationUserRepository authenticationUserRepository;
    private final CommentRepository commentRepository;

    public FeedServices(PostRepository postRepository, AuthenticationUserRepository authenticationUserRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.authenticationUserRepository = authenticationUserRepository;
        this.commentRepository = commentRepository;
    }

    public Post createPost(PostDto postDto, Long authorId) {
        AuthenticationUser author = authenticationUserRepository.findById(authorId).orElseThrow(()->new IllegalArgumentException("User not found"));
        Post post = new Post(postDto.getContent(),author);
        post.setPicture(postDto.getPicture());
        return postRepository.save(post);
    }

    public Post editPost(Long postId, Long authorId, PostDto postDto) {
        Post post = postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("Post not found"));
        AuthenticationUser user = authenticationUserRepository.findById(authorId).orElseThrow(()->new IllegalArgumentException("User not found"));

        if(!post.getAuthor().equals(user)){
            throw new IllegalArgumentException("User is not the author of the post");
        }
        post.setContent(postDto.getContent());
        post.setPicture(postDto.getPicture());

        return postRepository.save(post);
    }

    public List<Post> getFeedPosts(Long authorId) {

        return postRepository.findByAuthorIdNotOrderByCreationDateDesc(authorId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreationDateDesc();
    }

    public void deletePost(Long postId, Long authorId ) {
        Post post = postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("Post not found"));
        AuthenticationUser user = authenticationUserRepository.findById(authorId).orElseThrow(()-> new IllegalArgumentException("User not found"));
        if(!post.getAuthor().equals(user)){
            System.out.println(post.getAuthor());
            System.out.println(user);

            throw new IllegalArgumentException("User is not the author of the post");
        }
        postRepository.delete(post);
    }

    public Post getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("Post not found"));

        return post;
    }

    public List<Post> getPostByAuthorId(Long userId) {
        return postRepository.findByAuthorId(userId);
    }

    public Post likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("Post not found"));
        AuthenticationUser user = authenticationUserRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("User not found"));
        if(post.getLikes().contains(user)){
            post.getLikes().remove(user);
        }else{
            post.getLikes().add(user);
        }
        return postRepository.save(post);
    }

    public Comment addComment(Long postId, Long userId, String content) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        AuthenticationUser user = authenticationUserRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return commentRepository.save(new Comment(post, user, content));
    }

    public List<Comment> getPostComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new IllegalArgumentException("Post not found"));
        return post.getComments();
    }

    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new IllegalArgumentException("comment does not exist"));
        AuthenticationUser user = authenticationUserRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!comment.getAuthor().equals(user)) {
            throw new IllegalArgumentException("User is not the author of the comment");
        }
        commentRepository.delete(comment);
    }

    public Comment editComment(Long commentId, Long userId, String newContent) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new IllegalArgumentException("Comment not found"));
        AuthenticationUser user = authenticationUserRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!comment.getAuthor().equals(user)) {
            throw new IllegalArgumentException("User is not the author of the comment");
        }
        comment.setContent(newContent);

        return commentRepository.save(comment);
    }
}
