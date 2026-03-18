package ch.fhnw.user.data.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.fhnw.user.data.domain.Comment;
import ch.fhnw.user.data.domain.Daydream;
import ch.fhnw.user.data.domain.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByDaydream(Daydream daydream);
    List<Comment> findByUser(User user);
}
