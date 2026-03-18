package ch.fhnw.user.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.fhnw.user.data.domain.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}