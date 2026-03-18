package ch.fhnw.user.data.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.fhnw.user.data.domain.Tag;
import ch.fhnw.user.data.domain.Daydream;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    List<Tag> findByDaydream(Daydream daydream);
    List<Tag> findByName(String name);
}
