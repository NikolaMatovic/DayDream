package ch.fhnw.dream.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.dream.data.domain.Comment;
import ch.fhnw.dream.data.domain.Daydream;
import ch.fhnw.dream.data.domain.User;
import ch.fhnw.dream.data.repository.CommentRepository;
import ch.fhnw.dream.data.repository.DaydreamRepository;
import ch.fhnw.dream.data.repository.UserRepository;

@Service
public class DaydreamService {

    @Autowired
    private DaydreamRepository daydreamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<Daydream> getAllDaydreams() {
        return daydreamRepository.findAll();
    }

    public Daydream createDaydream(Daydream daydream) {
        if (daydream.getUpdatedAt() == null) {
            daydream.setUpdatedAt(java.time.LocalDateTime.now());
        }
        return daydreamRepository.save(daydream);
    }

    public Comment addCommentToDaydream(Long daydreamId, String username, String content) {
        Daydream daydream = daydreamRepository.findById(daydreamId)
            .orElseThrow(() -> new IllegalArgumentException("Daydream nicht gefunden"));

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Benutzer nicht gefunden"));

        Comment comment = new Comment();
        comment.setDaydream(daydream);
        comment.setUser(user);
        comment.setContent(content);

        return commentRepository.save(comment);
    }
}
