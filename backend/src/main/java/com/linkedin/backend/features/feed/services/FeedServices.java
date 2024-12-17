package com.linkedin.backend.features.feed.services;

import com.linkedin.backend.features.authentication.model.AuthenticationUser;
import com.linkedin.backend.features.authentication.repository.AuthenticationUserRepository;
import com.linkedin.backend.features.feed.dto.PostDto;
import com.linkedin.backend.features.feed.model.Post;
import com.linkedin.backend.features.feed.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedServices {

    private final PostRepository postRepository;
    private final AuthenticationUserRepository authenticationUserRepository;

    public FeedServices(PostRepository postRepository, AuthenticationUserRepository authenticationUserRepository) {
        this.postRepository = postRepository;
        this.authenticationUserRepository = authenticationUserRepository;
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
}
