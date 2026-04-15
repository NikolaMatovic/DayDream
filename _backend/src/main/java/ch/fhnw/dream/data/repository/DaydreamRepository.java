package ch.fhnw.dream.data.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.fhnw.dream.data.domain.Daydream;
import ch.fhnw.dream.data.domain.User;

@Repository
public interface DaydreamRepository extends JpaRepository<Daydream, Long> {
	List<Daydream> findByVisibility(ch.fhnw.dream.data.domain.Visibility visibility);
	List<Daydream> findByUser(User user);
}
