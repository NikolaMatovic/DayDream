package ch.fhnw.dream.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.dream.data.domain.Comment;
import ch.fhnw.dream.data.domain.Daydream;
import ch.fhnw.dream.data.domain.User;
import ch.fhnw.dream.data.domain.Visibility;
import ch.fhnw.dream.data.domain.Tag;
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

        Comment comment = new Comment();
        comment.setDaydream(daydream);
        comment.setAuthorUsername(username);
        comment.setContent(content);

        return commentRepository.save(comment);
    }

        public List<Daydream> getDaydreamsByVisibility(Visibility visibility) {
        return daydreamRepository.findByVisibility(visibility);
    }

    public List<Daydream> getDaydreamsByUser(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Benutzer nicht gefunden"));
        return daydreamRepository.findByUser(user);
    }

    public Daydream updateDaydream(Long id, Object requestObj, String username) {
        Daydream daydream = daydreamRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Daydream nicht gefunden"));
        if (!daydream.getUser().getUsername().equals(username)) {
            throw new SecurityException("Nicht berechtigt");
        }
        // Cast and update fields
        if (requestObj instanceof ch.fhnw.dream.controller.DaydreamController.CreateDaydreamRequest request) {
            if (request.getTitle() != null) daydream.setTitle(request.getTitle().trim());
            if (request.getDescription() != null) daydream.setDescription(request.getDescription().trim());
            if (request.getMood() != null) daydream.setMood(request.getMood().trim());
            if (request.getVisibility() != null) {
                try {
                    daydream.setVisibility(Visibility.valueOf(request.getVisibility().trim().toUpperCase()));
                } catch (Exception ignored) {}
            }
            // Tags update (optional, simple replace)
            if (request.getTags() != null) {
                daydream.getTags().clear();
                for (String tagName : request.getTags()) {
                    if (tagName != null && !tagName.trim().isEmpty()) {
                        Tag tag = new Tag();
                        tag.setDaydream(daydream);
                        tag.setName(tagName.trim());
                        daydream.getTags().add(tag);
                    }
                }
            }
        }
        return daydreamRepository.save(daydream);
    }

    public void deleteDaydream(Long id, String username) {
        Daydream daydream = daydreamRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Daydream nicht gefunden"));
        if (!daydream.getUser().getUsername().equals(username)) {
            throw new SecurityException("Nicht berechtigt");
        }
        daydreamRepository.delete(daydream);
    }
}
