package ch.fhnw.user.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.fhnw.user.business.service.CommentService;
import ch.fhnw.user.data.domain.Comment;
import ch.fhnw.user.data.domain.Daydream;
import ch.fhnw.user.data.domain.User;

@RestController
@RequestMapping("/commentapi/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/comments/daydream/{daydreamId}")
    public List<Comment> getCommentsByDaydream(@PathVariable UUID daydreamId) {
        Daydream daydream = new Daydream();
        daydream.setId(daydreamId);
        return commentService.getCommentsByDaydream(daydream);
    }

    @GetMapping("/comments/user/{userId}")
    public List<Comment> getCommentsByUser(@PathVariable UUID userId) {
        User user = new User();
        user.setId(userId);
        return commentService.getCommentsByUser(user);
    }

    @PostMapping("/comments")
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
