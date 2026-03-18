package ch.fhnw.user.data.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.fhnw.user.data.domain.Daydream;
import ch.fhnw.user.data.domain.User;

@Repository
public interface DaydreamRepository extends JpaRepository<Daydream, UUID> {
    List<Daydream> findByUser(User user);
    List<Daydream> findByTitleContaining(String title);
}
