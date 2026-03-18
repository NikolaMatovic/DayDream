package ch.fhnw.pizza.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.fhnw.pizza.data.domain.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}