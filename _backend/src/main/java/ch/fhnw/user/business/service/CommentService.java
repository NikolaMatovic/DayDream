package ch.fhnw.user.business.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.user.data.domain.Comment;
import ch.fhnw.user.data.domain.Daydream;
import ch.fhnw.user.data.domain.User;
import ch.fhnw.user.data.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsByDaydream(Daydream daydream) {
        return commentRepository.findByDaydream(daydream);
    }

    public List<Comment> getCommentsByUser(User user) {
        return commentRepository.findByUser(user);
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(UUID id) {
        commentRepository.deleteById(id);
    }
}
