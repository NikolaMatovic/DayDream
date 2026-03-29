package ch.fhnw.dream.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.fhnw.dream.data.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
