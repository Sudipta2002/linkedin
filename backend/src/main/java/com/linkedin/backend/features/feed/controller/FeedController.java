package com.linkedin.backend.features.feed.controller;

import com.linkedin.backend.features.authentication.model.AuthenticationUser;
import com.linkedin.backend.features.feed.dto.CommentDto;
import com.linkedin.backend.features.feed.dto.PostDto;
import com.linkedin.backend.features.feed.model.Comment;
import com.linkedin.backend.features.feed.model.Post;
import com.linkedin.backend.features.feed.services.FeedServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feed")
public class FeedController {
    private final FeedServices feedServices;


    public FeedController(FeedServices feedServices) {
        this.feedServices = feedServices;
    }

    @PostMapping("/posts")
    public ResponseEntity<Post>createPost(@RequestBody PostDto postDto, @RequestAttribute("authenticatedUser")AuthenticationUser user){
        Post post = feedServices.createPost(postDto,user.getId());
        return ResponseEntity.ok(post);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<Post> editPost(@PathVariable Long postId, @RequestBody PostDto postDto, @RequestAttribute("authenticatedUser") AuthenticationUser user){
        Post post = feedServices.editPost(postId,user.getId(),postDto);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getFeedPost(@RequestAttribute("authenticatedUser") AuthenticationUser user){
        List<Post>posts = feedServices.getFeedPosts(user.getId());
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>>getAllPosts(){
        List<Post>posts = feedServices.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // deletePostby Post id
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String>deleteFeedPost(@PathVariable Long postId, @RequestAttribute("authenticatedUser")AuthenticationUser user){
        feedServices.deletePost(postId,user.getId());
        return ResponseEntity.ok("Post deleted");
    }
    // get post by user id

    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<List<Post>>getPostByUserId(@PathVariable Long userId){
        List<Post> post = feedServices.getPostByAuthorId(userId);

        return ResponseEntity.ok(post);
    }

    // get post by post id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post>getPostById(@PathVariable Long postId){
        Post post = feedServices.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    // giving likes
    @PutMapping("/posts/{postId}/like")
    public ResponseEntity<Post> likePost(@PathVariable Long postId, @RequestAttribute("authenticatedUser")AuthenticationUser user){
        Post post = feedServices.likePost(postId,user.getId());
        return ResponseEntity.ok(post);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long postId, @RequestBody CommentDto commentDto, @RequestAttribute("authenticatedUser") AuthenticationUser user) {
        Comment comment = feedServices.addComment(postId, user.getId(), commentDto.getContent());
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId) {
        List<Comment> comments = feedServices.getPostComments(postId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @RequestAttribute("authenticatedUser") AuthenticationUser user) {
        feedServices.deleteComment(commentId, user.getId());
        return ResponseEntity.ok("Comment deleted successfully.");

    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Comment> editComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto, @RequestAttribute("authenticatedUser") AuthenticationUser user) {
        Comment comment = feedServices.editComment(commentId, user.getId(), commentDto.getContent());
        return ResponseEntity.ok(comment);
    }






}
